package tech.infofun.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tech.infofun.popularmovies.model.Movie;

/**
 * Created by tfbarbosa on 11/05/17.
 */
public class MoviesDAO {

    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public MoviesDAO(Context context){
        helper = new DatabaseHelper(context);
    }

    public SQLiteDatabase getDb(){
        if(db == null){
            db = helper.getWritableDatabase();
        }
        return db;
    }

    public long insert(Movie movie){
   ;
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MoviesTable._ID, movie.getId());
        values.put(DatabaseHelper.MoviesTable.TITLE, movie.getTitle());
        values.put(DatabaseHelper.MoviesTable.POSTER, movie.getPoster());
        values.put(DatabaseHelper.MoviesTable.DESCRIPTION, movie.getDescription());
        values.put(DatabaseHelper.MoviesTable.VOTE_AVERAGE, movie.getVote_average());
        values.put(DatabaseHelper.MoviesTable.RELEASE_DATE, movie.getRelease_date());
        values.put(DatabaseHelper.MoviesTable.BACKDROP, movie.getBackdrop());

        return getDb().insert(DatabaseHelper.MoviesTable.TABLE,null,values);
    }

    public boolean delete(int id){
        String p_args = String.valueOf(id);
        String where = DatabaseHelper.MoviesTable.MOVIE_ID + " = ?";
        String[] args = new String[]{p_args};
        int result = getDb().delete(DatabaseHelper.MoviesTable.TABLE, where, args);
        return result > 0;
    }

    public int select_check(int id){
        String p_args = String.valueOf(id);
        String where = DatabaseHelper.MoviesTable.MOVIE_ID + " = ";
        String query = "SELECT " + DatabaseHelper.MoviesTable.MOVIE_ID + " FROM " + DatabaseHelper.MoviesTable.TABLE + " WHERE " + where + p_args;
        Log.v("sql_result", query);
        Cursor cursor  = getDb().rawQuery(query,null);
        Log.v("sql_result",String.valueOf(cursor.getCount()));
        return cursor.getCount();
    }

    public List<Movie> select_all(){
        String query = "SELECT " +  DatabaseHelper.MoviesTable._ID + "," +
                                    DatabaseHelper.MoviesTable.MOVIE_ID + "," +
                                    DatabaseHelper.MoviesTable.TITLE + "," +
                                    DatabaseHelper.MoviesTable.POSTER + "," +
                                    DatabaseHelper.MoviesTable.DESCRIPTION + "," +
                                    DatabaseHelper.MoviesTable.VOTE_AVERAGE + "," +
                                    DatabaseHelper.MoviesTable.RELEASE_DATE + "," +
                                    DatabaseHelper.MoviesTable.BACKDROP +
                                    " FROM " + DatabaseHelper.MoviesTable.TABLE;

        Cursor cursor = getDb().rawQuery(query,null);
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


    public void close(){
        try {
            helper.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
