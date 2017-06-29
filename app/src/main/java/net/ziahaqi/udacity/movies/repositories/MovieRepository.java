package net.ziahaqi.udacity.movies.repositories;

import net.ziahaqi.udacity.movies.model.Movie;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ziahaqi on 6/28/17.
 */

public interface MovieRepository {

    Observable<List<Movie>> getPopularMovie();

    Observable<List<Movie>> getTopRatedMovie();


}
