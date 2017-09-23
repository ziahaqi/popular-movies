package net.ziahaqi.udacity.movies.repositories;

import net.ziahaqi.udacity.movies.model.Movie;
import net.ziahaqi.udacity.movies.model.Review;
import net.ziahaqi.udacity.movies.model.Trailer;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**$
 * Created by ziahaqi on 6/28/17.
 */

public interface MovieRepository {

    Observable<List<Movie>> getPopularMovie();

    Observable<List<Movie>> getTopRatedMovie();

    Observable<List<Trailer>> getTrailers(int movieId);

    Observable<List<Review>> getReviews(int movieId, int page);

    Completable saveMovie(Movie movie);

    Completable deleteMovie(Movie movie);

    Single<Movie> getMovie(Movie movie);

    Observable<List<Movie>> getFavoriteMovies();
}
