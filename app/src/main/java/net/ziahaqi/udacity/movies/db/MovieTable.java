package net.ziahaqi.udacity.movies.db;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ziahaqi on 8/6/17.
 */

public class MovieTable implements BaseColumns{

    private static final String PATH_MOVIES = "movies";

    public static final Uri CONTENT_URI = SQLiteDbHelper.BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();
    public static final String TABLE_NAME = "movies";
    public static final String COL_ID = "movie_id";
    public static final String COL_TITLE = "movie_title";
    public static final String COL_OVERVIEW = "movie_overview";
    public static final String COL_VOTE_COUNT = "movie_vote_count";
    public static final String COL_VOTE_AVERAGE = "movie_vote_average";
    public static final String COL_RELEASE_DATE = "movie_release_date";
    public static final String COL_FAVORED = "movie_favored";
    public static final String COL_POSTER_PATH = "movie_poster_path";
    public static final String COL_BACKDROP_PATH = "movie_backdrop_path";

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.popularmovies.movie";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.popularmovies.movie";

    public static final String DEFAULT_SORT = BaseColumns._ID + " DESC";

    public static Uri buildMovieUri(String movieId) {
        return CONTENT_URI.buildUpon().appendPath(movieId).build();
    }

    public static String getMovieId(Uri uri) {
        return uri.getPathSegments().get(1);
    }

    public static void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY,"
                + COL_ID + " TEXT NOT NULL,"
                + COL_TITLE + " TEXT NOT NULL,"
                + COL_OVERVIEW + " TEXT,"
                + COL_VOTE_AVERAGE + " REAL,"
                + COL_VOTE_COUNT + " INTEGER,"
                + COL_BACKDROP_PATH + " TEXT,"
                + COL_POSTER_PATH + " TEXT,"
                + COL_RELEASE_DATE + " TEXT,"
                + COL_FAVORED + " INTEGER NOT NULL DEFAULT 0,"
                + "UNIQUE (" + COL_ID + ") ON CONFLICT REPLACE)");
    }
}
