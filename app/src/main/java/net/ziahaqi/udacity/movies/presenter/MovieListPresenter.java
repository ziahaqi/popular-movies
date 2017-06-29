package net.ziahaqi.udacity.movies.presenter;

import net.ziahaqi.udacity.movies.model.Movie;
import net.ziahaqi.udacity.movies.repositories.MovieRepository;
import net.ziahaqi.udacity.movies.view.movielist.MovieListActivity;
import net.ziahaqi.udacity.movies.view.movielist.MovieListView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ziahaqi on 6/28/17.
 */

public class MovieListPresenter {
    private final MovieListView mView;
    private final MovieRepository mRepository;
    private CompositeDisposable mCompositeDisposable;


    public MovieListPresenter(MovieListView view, MovieRepository repository) {
        this.mRepository = repository;
        this.mView = view;
        this.mCompositeDisposable = new CompositeDisposable();
    }


    public void getMovies(int sortedBy) {
        Observable<List<Movie>> observableMovies;

        if (sortedBy == MovieListActivity.SORTED_BY_POPULAR) {
            observableMovies = mRepository.getPopularMovie();
        } else {
            observableMovies = mRepository.getTopRatedMovie();
        }

        mCompositeDisposable.add(observableMovies.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(@NonNull List<Movie> movies) throws Exception {
                        mView.showMoviews(movies);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showErrorMessage(throwable.getLocalizedMessage());
                    }
                }));
    }

    public void detachView() {
        mCompositeDisposable.dispose();
    }

    public void attachView() {
        mCompositeDisposable = new CompositeDisposable();
    }

}
