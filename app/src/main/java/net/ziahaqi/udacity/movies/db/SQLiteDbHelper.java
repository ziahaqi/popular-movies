package net.ziahaqi.udacity.movies.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by ziahaqi on 8/4/17.
 */

public class SQLiteDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "db.movies";
    private static final int DB_VERSION = 1;
    private Context context;

    public static final String CONTENT_AUTHORITY = "db.movies.authority";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    private static final String PATH_MOVIES = "movies";

    public SQLiteDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        MovieTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void deleteDatabase() {
        context.deleteDatabase(DB_NAME);
    }
}
