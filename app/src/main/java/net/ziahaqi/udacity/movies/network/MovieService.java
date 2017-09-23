package net.ziahaqi.udacity.movies.network;

import net.ziahaqi.udacity.movies.model.Trailer;
import net.ziahaqi.udacity.movies.model.payload.MovieResponse;
import net.ziahaqi.udacity.movies.model.payload.ReviewResponse;
import net.ziahaqi.udacity.movies.model.payload.TrailerResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ziahaqi on 6/28/17.
 */

public interface MovieService {

    @GET("movie/popular")
    Observable<MovieResponse> getPopularMovie();

    @GET("movie/top_rated")
    Observable<MovieResponse> getTopRatedMovie();

    @GET("movie/{id}/videos")
    Observable<TrailerResponse> getTrailers(@Path("id") int movieId);

    @GET("movie/{id}/reviews")
    Observable<ReviewResponse> getReviews(@Path("id") int movieId, @Query("page") int page);

}
