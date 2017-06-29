package net.ziahaqi.udacity.movies.repositories;

import net.ziahaqi.udacity.movies.model.Movie;
import net.ziahaqi.udacity.movies.model.MovieResponse;
import net.ziahaqi.udacity.movies.network.MovieService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by ziahaqi on 6/28/17.
 */

public class NetworkMovieRepository implements MovieRepository {

    private final MovieService mService;

    public NetworkMovieRepository(MovieService service) {
        this.mService = service;
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
}
