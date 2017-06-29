package net.ziahaqi.udacity.movies.view.movielist;

import net.ziahaqi.udacity.movies.model.Movie;

import java.util.List;

/**
 * Created by ziahaqi on 6/28/17.
 */

public interface MovieListView {

    void showMoviews(List<Movie> movies);

    void showErrorMessage(String errorMessage);
}
