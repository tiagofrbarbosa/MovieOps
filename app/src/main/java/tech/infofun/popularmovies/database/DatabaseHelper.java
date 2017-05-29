package tech.infofun.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tfbarbosa on 09/05/17.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String MOVIES_DB = "MoviesDB";
    private static int VERSION = 1;

    public static class MoviesTable{
        public static final String TABLE = "movies";
        public static final String _ID = "_id";
        public static final String MOVIE_ID = "movie_id";
        public static final String TITLE = "title";
        public static final String POSTER = "poster";
        public static final String DESCRIPTION = "description";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String RELEASE_DATE = "release_date";
        public static final String BACKDROP = "backdrop";
        public static final String IS_FAVORITE = "is_favorite";

        public static final String[] COLUMNS = new String[]{
                _ID, MOVIE_ID, TITLE, POSTER, DESCRIPTION, VOTE_AVERAGE, RELEASE_DATE, BACKDROP
        };
    }

    public DatabaseHelper(Context context){
        super(context, MOVIES_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE " + MoviesTable.TABLE +
                "(" + MoviesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        MoviesTable.MOVIE_ID + " INTEGER," +
                        MoviesTable.TITLE + " TEXT," +
                        MoviesTable.POSTER + " TEXT," +
                        MoviesTable.DESCRIPTION + " TEXT," +
                        MoviesTable.VOTE_AVERAGE + " TEXT," +
                        MoviesTable.RELEASE_DATE + " TEXT," +
                        MoviesTable.BACKDROP + " TEXT," +
                        MoviesTable.IS_FAVORITE + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

}
