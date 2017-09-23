package net.ziahaqi.udacity.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ziahaqi on 6/28/17.
 */

public class Movie implements Parcelable {

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
    public String releaseDate;
    public boolean adult;
    public boolean favorite = false;

    public Movie() {
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        originalTitle = in.readString();
        voteCount = in.readInt();
        voteAverage = in.readFloat();
        popularity = in.readFloat();
        posterPath = in.readString();
        backdropPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        adult = in.readInt() == 1;
        favorite = in.readInt() == 1;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.originalTitle);
        dest.writeInt(this.voteCount);
        dest.writeFloat(this.voteAverage);
        dest.writeFloat(this.popularity);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeInt(this.adult ? 1 : 0);
        dest.writeInt(this.favorite ? 1 : 0);
    }
}
