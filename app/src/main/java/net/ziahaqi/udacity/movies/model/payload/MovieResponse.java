package net.ziahaqi.udacity.movies.model.payload;

import net.ziahaqi.udacity.movies.model.Movie;

import java.util.List;

/**
 * Created by ziahaqi on 6/28/17.
 */

public class MovieResponse {
    public int page;
    public int totalResults;
    public int totalPages;
    public List<Movie> results;
}
