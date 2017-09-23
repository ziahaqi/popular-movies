package net.ziahaqi.udacity.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ziahaqi on 8/9/17.
 */

public class Trailer implements Parcelable {

    @SerializedName("id")
    public String id;
    @SerializedName("type")
    public String type;
    @SerializedName("name")
    public String name;
    @SerializedName("key")
    public String key;
    @SerializedName("iso_639_1")
    public String iso6391;
    @SerializedName("site")
    public String site;
    @SerializedName("size")
    public int size;
    @SerializedName("iso_3166_1")
    public String iso31661;


    protected Trailer(Parcel in) {
        this.id = in.readString();
        this.type = in.readString();
        this.name = in.readString();
        this.key = in.readString();
        this.site = in.readString();
        this.size = in.readInt();
        this.iso6391 = in.readString();
        this.iso31661 = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.site);
        dest.writeInt(this.size);
        dest.writeString(this.iso31661);
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeString(this.iso6391);
        dest.writeString(this.key);
    }
}
