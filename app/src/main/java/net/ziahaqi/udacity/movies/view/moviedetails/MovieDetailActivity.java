package net.ziahaqi.udacity.movies.view.moviedetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.ziahaqi.udacity.movies.R;
import net.ziahaqi.udacity.movies.model.Movie;
import net.ziahaqi.udacity.movies.model.Review;
import net.ziahaqi.udacity.movies.model.Trailer;
import net.ziahaqi.udacity.movies.network.ApiBuilder;
import net.ziahaqi.udacity.movies.network.MovieService;
import net.ziahaqi.udacity.movies.repositories.MovieRepository;
import net.ziahaqi.udacity.movies.repositories.MovieRepositoryImpl;

import java.util.List;

/**
 * Created by ziahaqi on 6/29/17.
 */

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailView {
    public static final String EXTRA_MOVIE = "extra.movie";
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w342/";
    private static final String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w780/";
    private static final String YOUTUBE_TRAILER_URL = "http://www.youtube.com/watch?v=";

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private ImageView imagePoster;
    private ImageView imageBackDrop;
    private TextView textTitle;
    private TextView textReleaseDate;
    private TextView textRate;
    private TextView textDescription;
    private RecyclerView rvTrailers;
    private RecyclerView rvReview;
    private FloatingActionButton buttonFavorite;

    private Movie mMovie;
    private MovieDetailPresenter presenter;
    private MovieRepository movieRepository;
    private MovieService movieDbServices;
    private TrailerListAdapter trailersAdapter;
    private ReviewListAdapter reviewAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        initExtras();
        bindViews();
        initProperties();
        initListData();
        initActionButton();
        bindData();
    }

    private void initActionButton() {
        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.changeFavoriteStatus(mMovie);
            }
        });
    }

    private void bindData() {
        if (this.mMovie != null) {
            textTitle.setText(mMovie.title);
            textRate.setText(String.valueOf(mMovie.voteAverage));
            textReleaseDate.setText(getString(R.string.release_date, mMovie.releaseDate));
            textDescription.setText(mMovie.overview);

            if (mMovie.favorite) {
                buttonFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
            } else {
                buttonFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
            }

            final String posterUrl = POSTER_BASE_URL + mMovie.posterPath;

            Glide.with(this)
                    .load(posterUrl)
                    .into(imagePoster);

            final String backDropUrl = BACKDROP_BASE_URL + mMovie.backdropPath;
            Glide.with(this)
                    .load(backDropUrl)
                    .into(imageBackDrop);

            presenter.getVideoAndReview(mMovie.id);

        }
    }

    private void initListData() {
        reviewAdapter = new ReviewListAdapter();
        rvReview = (RecyclerView) findViewById(R.id.rvReview);
        rvReview.setLayoutManager(new LinearLayoutManager(this));
        rvReview.setAdapter(reviewAdapter);

        trailersAdapter = new TrailerListAdapter(new TrailerListAdapter.TrailerListener() {
            @Override
            public void onTrailerClick(Trailer trailer) {
                showVideoTrailer(trailer);
            }
        });
        rvTrailers = (RecyclerView) findViewById(R.id.rvTrailers);
        rvTrailers.setLayoutManager(new LinearLayoutManager(this));
        rvTrailers.setAdapter(trailersAdapter);
    }

    private void showVideoTrailer(Trailer trailer) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_TRAILER_URL + trailer.key)));
    }

    private void initProperties() {
        movieDbServices = ApiBuilder.createMovieService();
        movieRepository = new MovieRepositoryImpl(movieDbServices, this.getContentResolver());
        presenter = new MovieDetailPresenter(movieRepository, this);
    }

    private void initExtras() {
        this.mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);

    }

    private void bindViews() {
        imagePoster = (ImageView) findViewById(R.id.image_poster);
        imageBackDrop = (ImageView) findViewById(R.id.image_backdrop);
        textTitle = (TextView) findViewById(R.id.tv_title);
        textRate = (TextView) findViewById(R.id.tv_rating);
        textReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        textDescription = (TextView) findViewById(R.id.tv_description);
        buttonFavorite = (FloatingActionButton) findViewById(R.id.button_favorite);
    }

    @Override
    public void showTrailers(List<Trailer> trailers) {
        trailersAdapter.setData(trailers);
    }

    @Override
    public void showReviews(List<Review> reviews) {
        reviewAdapter.setData(reviews);
    }

    @Override
    public void showErrorMessage(String message) {
        Log.d(TAG, "showErrorMessage():" + message);
        Toast.makeText(this, "message:" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddNewFavoriteSucces() {
        this.mMovie.favorite = true;
        buttonFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
    }

    @Override
    public void onDeleteFavoriteSuccess() {
        this.mMovie.favorite = false;
        buttonFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
