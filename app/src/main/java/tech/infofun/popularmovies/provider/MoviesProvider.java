package tech.infofun.popularmovies.provider;

import static tech.infofun.popularmovies.provider.MoviesContract.*;
import tech.infofun.popularmovies.provider.MoviesContract.Movie;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import tech.infofun.popularmovies.database.DatabaseHelper;

/**
 * Created by tfbarbosa on 28/05/17.
 */
public class MoviesProvider extends ContentProvider{

    private static final int MOVIES = 1;
    private static final int MOVIE_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private DatabaseHelper helper;

    static{
        uriMatcher.addURI(AUTHORITY, MOVIE_PATH, MOVIES);
        uriMatcher.addURI(AUTHORITY, MOVIE_PATH + "/#", MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        helper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase database = helper.getReadableDatabase();

        switch (uriMatcher.match(uri)){

            case MOVIES:
                return database.query(MOVIE_PATH, projection, selection, selectionArgs, null, null, sortOrder);

            case MOVIE_ID:
                selection = Movie._ID + " = ?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                return database.query(MOVIE_PATH, projection, selection, selectionArgs, null, null, sortOrder);

            default:
                throw new IllegalArgumentException("unknown Uri");
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        switch(uriMatcher.match(uri)){

            case MOVIES:
                return Movie.CONTENT_TYPE;

            case MOVIE_ID:
                return Movie.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("unknown uri");
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase database = helper.getWritableDatabase();
        long id;

        switch (uriMatcher.match(uri)){

            case MOVIES:
                id = database.insert(MOVIE_PATH, null, values);
                return Uri.withAppendedPath(Movie.CONTENT_URI, String.valueOf(id));

            default:
                throw new IllegalArgumentException("unknown uri");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase database = helper.getWritableDatabase();

        switch(uriMatcher.match(uri)){

            case MOVIES:
                return database.delete(MOVIE_PATH, selection, selectionArgs);

            case MOVIE_ID:
                selection = Movie._ID + " = ?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                return database.delete(MOVIE_PATH, selection, selectionArgs);

            default:
                throw new IllegalArgumentException("unknown uri");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase database = helper.getWritableDatabase();

        switch(uriMatcher.match(uri)){

            case MOVIE_ID:
                selection = Movie._ID + " = ?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                return database.update(MOVIE_PATH, values, selection, selectionArgs);

            default:
                throw new IllegalArgumentException("unknown uri");
        }
    }
}
