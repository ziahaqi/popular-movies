package net.ziahaqi.udacity.movies.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ziahaqi on 6/28/17.
 */

public class Movie implements Serializable {

    public int id;
    public String title;
    @SerializedName("original_title")
    public String originalTitle;
    @SerializedName("vote_count")
    public int voteCount;
    @SerializedName("vote_average")
    public float voteAverage;
    public float popularity;
    @SerializedName("poster_path")
    public String posterPath;
    @SerializedName("backdrop_path")
    public String backdropPath;
    public String overview;
    @SerializedName("release_date")
    public Date releaseDate;
    public boolean adult;

}
