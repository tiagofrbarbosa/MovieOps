package tech.infofun.popularmovies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tfbarbosa on 09/05/17.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String MOVIES_DB = "MoviesDB";
    private static int VERSION = 1;


    public DatabaseHelper(Context context){
        super(context, MOVIES_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE movies(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                   "movie_id INTEGER," +
                    "title TEXT," +
                    "poster TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

}
