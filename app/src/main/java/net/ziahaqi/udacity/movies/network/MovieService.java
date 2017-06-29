package net.ziahaqi.udacity.movies.network;

import net.ziahaqi.udacity.movies.model.MovieResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by ziahaqi on 6/28/17.
 */

public interface MovieService {

    @GET("movie/popular")
    Observable<MovieResponse> getPopularMovie();

    @GET("movie/top_rated")
    Observable<MovieResponse> getTopRatedMovie();
}
