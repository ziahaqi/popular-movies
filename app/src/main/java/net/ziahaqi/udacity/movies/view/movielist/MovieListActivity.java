package net.ziahaqi.udacity.movies.view.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.ziahaqi.udacity.movies.R;
import net.ziahaqi.udacity.movies.model.Movie;
import net.ziahaqi.udacity.movies.network.ApiBuilder;
import net.ziahaqi.udacity.movies.network.MovieService;
import net.ziahaqi.udacity.movies.presenter.MovieListPresenter;
import net.ziahaqi.udacity.movies.repositories.MovieRepository;
import net.ziahaqi.udacity.movies.repositories.NetworkMovieRepository;
import net.ziahaqi.udacity.movies.view.moviedetails.MovieDetailActivity;

import java.util.List;

public class MovieListActivity extends AppCompatActivity implements MovieListView, SwipeRefreshLayout.OnRefreshListener,
        MovieListAdapter.EventListener {

    public static final int SORTED_BY_POPULAR = 1;
    public static final int SORTED_BY_TOP_RATED = 2;
    private static final int COLUMN_NUMBER = 2;

    SwipeRefreshLayout refreshLayout;
    Toolbar mToobar;
    RecyclerView mListMovies;
    TextView mTextErrorMessage;
    ProgressBar mProgressBar;

    private MovieRepository mMovieRepository;
    private MovieService mMovieDbService;
    private MovieListPresenter mPresenter;
    private MovieListAdapter mAdapter;
    private int selectedSort = SORTED_BY_POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        bindViews();
        initProperties();
        fetchMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_item_popular:
                selectedSort = SORTED_BY_POPULAR;
                fetchMovies();
                break;
            case R.id.menu_item_top_rated:

                selectedSort = SORTED_BY_TOP_RATED;
                fetchMovies();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchMovies() {
        mProgressBar.setVisibility(View.VISIBLE);
        mPresenter.getMovies(selectedSort);
    }

    private void initProperties() {
        setSupportActionBar(mToobar);
        mMovieDbService = ApiBuilder.createMovieService();
        mMovieRepository = new NetworkMovieRepository(mMovieDbService);
        mPresenter = new MovieListPresenter(this, mMovieRepository);
        mAdapter = new MovieListAdapter(this);

        mListMovies.setHasFixedSize(true);
        mListMovies.setLayoutManager(new GridLayoutManager(this, COLUMN_NUMBER));
        mListMovies.setAdapter(mAdapter);
    }

    private void bindViews() {
        mToobar = (Toolbar) findViewById(R.id.toolbar);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_refresh);
        mListMovies = (RecyclerView) findViewById(R.id.rv_list);
        mTextErrorMessage = (TextView) findViewById(R.id.tv_error_message);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(int position) {
        Movie movie = mAdapter.getItem(position);
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
        fetchMovies();
    }

    @Override
    public void showMoviews(List<Movie> movies) {
        mProgressBar.setVisibility(View.GONE);
        mAdapter.setData(movies);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        mProgressBar.setVisibility(View.GONE);
        mTextErrorMessage.setText(errorMessage);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
