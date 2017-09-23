package net.ziahaqi.udacity.movies.repositories;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import net.ziahaqi.udacity.movies.db.MovieTable;
import net.ziahaqi.udacity.movies.model.Movie;
import net.ziahaqi.udacity.movies.model.Review;
import net.ziahaqi.udacity.movies.model.Trailer;
import net.ziahaqi.udacity.movies.model.payload.MovieResponse;
import net.ziahaqi.udacity.movies.model.payload.ReviewResponse;
import net.ziahaqi.udacity.movies.model.payload.TrailerResponse;
import net.ziahaqi.udacity.movies.network.MovieService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by ziahaqi on 6/28/17.
 */

public class MovieRepositoryImpl implements MovieRepository {
    private static String TAG = MovieRepositoryImpl.class.getSimpleName();
    private final MovieService mService;
    private final ContentResolver contentResolver;

    private final String[] movieProjection = new String[]{
            MovieTable.COL_ID,
            MovieTable.COL_TITLE,
            MovieTable.COL_OVERVIEW,
            MovieTable.COL_VOTE_COUNT,
            MovieTable.COL_VOTE_AVERAGE,
            MovieTable.COL_RELEASE_DATE,
            MovieTable.COL_FAVORED,
            MovieTable.COL_POSTER_PATH,
            MovieTable.COL_BACKDROP_PATH,
    };

    public MovieRepositoryImpl(MovieService mService, ContentResolver contentResolver) {
        this.mService = mService;
        this.contentResolver = contentResolver;
    }

    @Override
    public Observable<List<Movie>> getPopularMovie() {
        return mService.getPopularMovie().flatMap(new Function<MovieResponse, Observable<List<Movie>>>() {
            @Override
            public Observable<List<Movie>> apply(@NonNull MovieResponse movieResponse) throws Exception {
                return Observable.just(movieResponse.results);
            }
        });
    }

    @Override
    public Observable<List<Movie>> getTopRatedMovie() {
        return mService.getTopRatedMovie().flatMap(new Function<MovieResponse, ObservableSource<List<Movie>>>() {
            @Override
            public ObservableSource<List<Movie>> apply(@NonNull MovieResponse movieResponse) throws Exception {
                return Observable.just(movieResponse.results);
            }
        });
    }

    @Override
    public Observable<List<Trailer>> getTrailers(int movieId) {
        return mService.getTrailers(movieId).flatMap(new Function<TrailerResponse, ObservableSource<List<Trailer>>>() {
            @Override
            public ObservableSource<List<Trailer>> apply(@NonNull TrailerResponse trailerResponse) throws Exception {
                return Observable.just(trailerResponse.results);
            }
        });
    }

    @Override
    public Observable<List<Review>> getReviews(int movieId, int page) {
        Log.d(TAG, "getReviews");

        return mService.getReviews(movieId, page).flatMap(new Function<ReviewResponse, ObservableSource<List<Review>>>() {
            @Override
            public ObservableSource<List<Review>> apply(@NonNull ReviewResponse reviewResponse) throws Exception {
                return Observable.just(reviewResponse.results);
            }
        });

    }

    @Override
    public Completable saveMovie(final Movie movie) {
        Log.d(TAG, "saveMovie: " + movie.title);
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(@NonNull CompletableEmitter e) throws Exception {
                final ContentValues movieValues = getMovieValues(movie);
                contentResolver.insert(MovieTable.CONTENT_URI, movieValues);
                e.onComplete();
            }
        });
    }

    private ContentValues getMovieValues(Movie movie) {
        final ContentValues values = new ContentValues();
        values.put(MovieTable.COL_ID, movie.id);
        values.put(MovieTable.COL_TITLE, movie.title);
        values.put(MovieTable.COL_OVERVIEW, movie.overview);
        values.put(MovieTable.COL_VOTE_COUNT, movie.voteCount);
        values.put(MovieTable.COL_VOTE_AVERAGE, movie.voteAverage);
        values.put(MovieTable.COL_RELEASE_DATE, movie.releaseDate);
        values.put(MovieTable.COL_FAVORED, movie.favorite);
        values.put(MovieTable.COL_POSTER_PATH, movie.posterPath);
        values.put(MovieTable.COL_BACKDROP_PATH, movie.backdropPath);
        return values;
    }

    @Override
    public Completable deleteMovie(final Movie movie) {

        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(@NonNull CompletableEmitter e) throws Exception {
                final String where = String.format("%s=?", MovieTable.COL_ID);
                final String[] args = new String[]{String.valueOf(movie.id)};
                contentResolver.delete(MovieTable.CONTENT_URI, where, args);
                e.onComplete();
            }
        });
    }

    @Override
    public Single<Movie> getMovie(final Movie movie) {
        return Single.create(new SingleOnSubscribe<Movie>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Movie> e) throws Exception {
                final String where = String.format("%s=?", MovieTable.COL_ID);
                final String[] args = new String[]{String.valueOf(movie.id)};
                final Cursor cursor = contentResolver.query(MovieTable.CONTENT_URI, movieProjection, where, args, null);
                Log.d(TAG, "getMovie: " + movie.title);
                Log.d(TAG, "getMovie: cursor count " + cursor.getCount());
                if (cursor.getCount() >= 1) {
                    cursor.moveToFirst();
                    final Movie resultMovie = fetchMovie(cursor);
                    e.onSuccess(resultMovie);
                } else {
                    e.onError(new Throwable("No movies"));
                }
            }
        });
    }

    @Override
    public Observable<List<Movie>> getFavoriteMovies() {
        return Observable.create(new ObservableOnSubscribe<List<Movie>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Movie>> e) throws Exception {
                List<Movie> movies = new ArrayList<>();
                final Cursor query = contentResolver.query(MovieTable.CONTENT_URI, movieProjection, null, null, null);
                if (query.moveToFirst()) {
                    do {
                        movies.add(fetchMovie(query));
                    } while (query.moveToNext());
                }
                Log.d(TAG, "getFavoriteMovies: " + movies.size());
                e.onNext(movies);
            }
        });
    }

    private Movie fetchMovie(Cursor query) {
        Movie movie = new Movie();

        movie.id = query.getInt(query.getColumnIndex(MovieTable.COL_ID));
        movie.title = query.getString(query.getColumnIndex(MovieTable.COL_TITLE));
        movie.overview = query.getString(query.getColumnIndex(MovieTable.COL_OVERVIEW));
        movie.posterPath = query.getString(query.getColumnIndex(MovieTable.COL_POSTER_PATH));
        movie.voteAverage = (float) query.getDouble(query.getColumnIndex(MovieTable.COL_VOTE_AVERAGE));
        movie.voteCount = query.getInt(query.getColumnIndex(MovieTable.COL_VOTE_COUNT));
        movie.releaseDate = query.getString(query.getColumnIndex(MovieTable.COL_RELEASE_DATE));
        movie.backdropPath = query.getString(query.getColumnIndex(MovieTable.COL_BACKDROP_PATH));
        movie.favorite = query.getInt(query.getColumnIndex(MovieTable.COL_FAVORED)) > 0;
        return movie;
    }

}
