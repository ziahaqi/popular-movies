package net.ziahaqi.udacity.movies.view.moviedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.ziahaqi.udacity.movies.R;
import net.ziahaqi.udacity.movies.model.Movie;
import net.ziahaqi.udacity.movies.utils.DateUtils;

/**
 * Created by ziahaqi on 6/29/17.
 */

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra.movie";
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w342/";
    private static final String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w780/";

    private ImageView imagePoster;
    private ImageView imageBackDrop;
    private TextView textTitle;
    private TextView textReleaseDate;
    private TextView textRate;
    private TextView textDescription;

    private Movie mMovie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        initExtras();
        bindViews();
        initProperties();
    }

    private void initProperties() {
        if (this.mMovie != null) {
            textTitle.setText(mMovie.title);
            textRate.setText(String.valueOf(mMovie.voteAverage));
            textReleaseDate.setText(DateUtils.convertMovieReleaseDate(mMovie.releaseDate));
            textDescription.setText(mMovie.overview);

            final String posterUrl = POSTER_BASE_URL + mMovie.posterPath;

            Glide.with(this)
                    .load(posterUrl)
                    .into(imagePoster);

            final String backDropUrl = BACKDROP_BASE_URL + mMovie.backdropPath;
            Glide.with(this)
                    .load(backDropUrl)
                    .into(imageBackDrop);
        }
    }

    private void initExtras() {
        this.mMovie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
    }

    private void bindViews() {
        imagePoster = (ImageView) findViewById(R.id.image_poster);
        imageBackDrop = (ImageView) findViewById(R.id.image_backdrop);
        textTitle = (TextView) findViewById(R.id.tv_title);
        textRate = (TextView) findViewById(R.id.tv_rating);
        textReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        textDescription = (TextView) findViewById(R.id.tv_description);
    }
}
