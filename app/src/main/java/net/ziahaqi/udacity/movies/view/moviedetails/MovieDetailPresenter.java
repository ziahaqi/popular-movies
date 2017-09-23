package net.ziahaqi.udacity.movies.view.moviedetails;

import android.util.Log;
import android.util.Pair;

import net.ziahaqi.udacity.movies.model.Movie;
import net.ziahaqi.udacity.movies.model.Review;
import net.ziahaqi.udacity.movies.model.Trailer;
import net.ziahaqi.udacity.movies.repositories.MovieRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ziahaqi on 9/23/17.
 */

public class MovieDetailPresenter {

    private static final String TAG = MovieDetailPresenter.class.getSimpleName();
    private MovieRepository movieRepository;
    private MovieDetailView view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MovieDetailPresenter(MovieRepository movieRepository, MovieDetailView view) {
        this.movieRepository = movieRepository;
        this.view = view;
    }

    public void getVideoAndReview(int movieId) {
        compositeDisposable.add(Observable.zip(movieRepository.getTrailers(movieId), movieRepository.getReviews(movieId, 1), new BiFunction<List<Trailer>, List<Review>, Pair<List<Trailer>, List<Review>>>() {
            @Override
            public Pair<List<Trailer>, List<Review>> apply(@NonNull List<Trailer> trailers, @NonNull List<Review> reviews) throws Exception {

                return new Pair<>(trailers, reviews);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Pair<List<Trailer>, List<Review>>>() {
                    @Override
                    public void accept(@NonNull Pair<List<Trailer>, List<Review>> listListPair) throws Exception {
                        view.showTrailers(listListPair.first);
                        view.showReviews(listListPair.second);
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        view.showErrorMessage(throwable.getMessage());
                        Log.d(TAG, "onFetchTrailersAndReviews:" + throwable.getMessage());
                    }
                }));

    }

    public void changeFavoriteStatus(Movie movie) {
        if (movie.favorite) {
            compositeDisposable.add(movieRepository.deleteMovie(movie)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action() {
                        @Override
                        public void run() throws Exception {
                            view.onDeleteFavoriteSuccess();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            Log.d(TAG, "ondeleteFavoriteFailed");
                        }
                    }));

        } else {
            compositeDisposable.add(movieRepository.saveMovie(movie).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action() {
                        @Override
                        public void run() throws Exception {
                            view.onAddNewFavoriteSucces();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            Log.d(TAG, "onAddFavoriteFailed");
                        }
                    }));
        }
    }

    public void detachView() {
        compositeDisposable.dispose();
    }

}
