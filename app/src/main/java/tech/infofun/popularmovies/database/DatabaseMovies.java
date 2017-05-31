package tech.infofun.popularmovies.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import tech.infofun.popularmovies.model.Movie;
import tech.infofun.popularmovies.provider.MoviesContract;

/**
 * Created by tfbarbosa on 30/05/17.
 */
public class DatabaseMovies {

        private ContentResolver mResolver;
        private Uri mUri;


        public DatabaseMovies(ContentResolver resolver){
            mResolver = resolver;
            mUri = MoviesContract.Movie.CONTENT_URI;
        }

    public List<Movie> getFavoriteMovies(){

        String[] projection = new String[]{MoviesContract.Movie._ID, MoviesContract.Movie.MOVIE_ID, MoviesContract.Movie.TITLE, MoviesContract.Movie.POSTER, MoviesContract.Movie.DESCRIPTION,
                MoviesContract.Movie.VOTE_AVERAGE, MoviesContract.Movie.RELEASE_DATE, MoviesContract.Movie.BACKDROP};

        String selection = MoviesContract.Movie.IS_FAVORITE + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(1)};
        String sortOrder = null;

        Cursor cursor = mResolver.query(mUri, projection, selection, selectionArgs, sortOrder);

        cursor.moveToFirst();

        List<Movie> fav = new ArrayList<Movie>();

        for (int i = 0; i < cursor.getCount(); i++) {

            Movie faMovies = new Movie();

            faMovies.setId(cursor.getInt(1));
            faMovies.setTitle(cursor.getString(2));
            faMovies.setPoster(cursor.getString(3));
            faMovies.setDescription(cursor.getString(4));
            faMovies.setVote_average(cursor.getString(5));
            faMovies.setRelease_date(cursor.getString(6));
            faMovies.setBackdrop(cursor.getString(7));
            fav.add(faMovies);
            cursor.moveToNext();

        }
        cursor.close();
        return fav;
    }

    public int getFavoriteMovieForCheck(int id){

        String[] projection = new String[]{MoviesContract.Movie.MOVIE_ID};
        final String selection = MoviesContract.Movie.MOVIE_ID + " = ?";
        final String[] selectionArgs = new String[]{String.valueOf(id)};
        String sortOrder = null;

        Cursor cursor = mResolver.query(mUri, projection, selection, selectionArgs, sortOrder);

        int mCursorCount;

        try {
            cursor.moveToFirst();
            mCursorCount =  cursor.getCount();
        }catch (Exception e){
            mCursorCount = 0;
        }

        return mCursorCount;
    }

    public Uri setFavoriteMovies(ContentValues values){
        return mResolver.insert(mUri,values);
    }

    public void deleteMovies(int id){

        final String selection = MoviesContract.Movie.MOVIE_ID + " = ?";
        final String[] selectionArgs = new String[]{String.valueOf(id)};
        mResolver.delete(mUri,selection,selectionArgs);
    }
}
