package net.ziahaqi.udacity.movies.db.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.ziahaqi.udacity.movies.db.MovieTable;
import net.ziahaqi.udacity.movies.db.SQLiteDbHelper;

/**
 * Created by ziahaqi on 8/6/17.
 */

public class MovieProvider extends ContentProvider {
    private static final int MOVIES = 100;
    private static final int MOVIES_ID = 101;
    private static final String MOVIES_PATH = "movies";
    private static final String MOVIES_ID_PATH = "movies/*";
    private static final UriMatcher URI_MATCHER = buildUriMatcher();

    private SQLiteDbHelper dbHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SQLiteDbHelper.CONTENT_AUTHORITY;

        matcher.addURI(authority, MOVIES_PATH, MOVIES);
        matcher.addURI(authority, MOVIES_ID_PATH, MOVIES_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new SQLiteDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);

        Cursor query;
        switch (match) {
            case MOVIES: {
                query = db.query(MovieTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
            }
        }
        query.setNotificationUri(getContext().getContentResolver(), uri);
        return query;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);
        Uri insertUri;
        switch (match) {
            case MOVIES: {
                long id = db.insertOrThrow(MovieTable.TABLE_NAME, null, values);
                insertUri = MovieTable.buildMovieUri(String.valueOf(id));
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
            }
        }
        notifyResolver(uri);
        return insertUri;
    }

    private void notifyResolver(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (uri.equals(SQLiteDbHelper.BASE_CONTENT_URI)) {
            dbHelper.close();
            dbHelper.deleteDatabase();
            dbHelper = new SQLiteDbHelper(getContext());
            return 1;
        }
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);
        int retrieveValue;
        switch (match) {
            case MOVIES: {
                retrieveValue = db.delete(MovieTable.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
            }
        }
        notifyResolver(uri);
        return retrieveValue;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);
        int updateValue;
        switch (match) {
            case MOVIES: {
                updateValue = db.update(MovieTable.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Update data failed: " + uri);
            }
        }
        notifyResolver(uri);
        return updateValue;
    }
}
