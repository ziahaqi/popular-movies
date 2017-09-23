package net.ziahaqi.udacity.movies.view.moviedetails;

import net.ziahaqi.udacity.movies.model.Review;
import net.ziahaqi.udacity.movies.model.Trailer;

import java.util.List;

/**
 * Created by ziahaqi on 9/23/17.
 */

public interface MovieDetailView {
    void showTrailers(List<Trailer> first);

    void showReviews(List<Review> second);

    void showErrorMessage(String message);

    void onAddNewFavoriteSucces();

    void onDeleteFavoriteSuccess();
}
