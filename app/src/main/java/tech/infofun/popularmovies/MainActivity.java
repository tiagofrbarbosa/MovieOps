package tech.infofun.popularmovies;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {


    private DatabaseHelper helper;
    private RecyclerView mRecyclerView;
    private MoviesAdapter mAdapter;
    private Parcelable mListState;
    private int pageCount = 1;
    private String query;
    private String movieLang;
    private static final String RESTORE_PAGE = "PageRestore";
    private static final String LIST_STATE_KEY = "ListRestore";
    private static final String MOVIE_QUERY = "MovieQuery";
    private static final String LANG_MOVIE = "movieLang";
    private static final String API_KEY = "API_KEY_HERE";

    FloatingActionButton b_back, b_next;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         b_back = (FloatingActionButton) findViewById(R.id.back);
         b_next = (FloatingActionButton) findViewById(R.id.next);

        if(getPageCount() == 1){
            b_back.setVisibility(View.GONE);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        if(savedInstanceState == null) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            query = sharedPrefs.getString(getString(R.string.pref_order_key),getString(R.string.pref_value_popular));
            movieLang = sharedPrefs.getString(getString(R.string.pref_language_key),getString(R.string.pref_value_lang_en));
            RetroMovies(getPageCount(),query, movieLang);
        }else{
            RetroMovies(savedInstanceState.getInt(RESTORE_PAGE),savedInstanceState.getString(MOVIE_QUERY), savedInstanceState.getString(LANG_MOVIE));
        }

            b_back.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if (getPageCount() > 1) {
                        changePageCount("del");
                    }

                    RetroMovies(getPageCount(), query, movieLang);
                }
            });


        b_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                changePageCount("add");

                RetroMovies(getPageCount(),query,movieLang);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        mListState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        savedInstanceState.putParcelable(LIST_STATE_KEY,mListState);
        savedInstanceState.putInt(RESTORE_PAGE,getPageCount());
        savedInstanceState.putString(MOVIE_QUERY,query);
        savedInstanceState.putString(LANG_MOVIE, movieLang);
        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String prefQuery = sharedPrefs.getString(getString(R.string.pref_order_key),getString(R.string.pref_value_popular));
        String prefLang = sharedPrefs.getString(getString(R.string.pref_language_key),getString(R.string.pref_value_lang_en));
        String savedQuery = savedInstanceState.getString(MOVIE_QUERY);
        String savedLang = savedInstanceState.getString(LANG_MOVIE);


            if (!savedQuery.equals(prefQuery) | !savedLang.equals(prefLang) ) {

                query = prefQuery;
                movieLang = prefLang;
                setPageCount(1);
                RetroMovies(getPageCount(), query, movieLang);

            } else {

                query = savedQuery;
                movieLang = savedLang;
                mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
                setPageCount(savedInstanceState.getInt(RESTORE_PAGE));
                mRecyclerView.getLayoutManager().onRestoreInstanceState(mListState);
            }
    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String prefQuery = sharedPrefs.getString(getString(R.string.pref_order_key), getString(R.string.pref_value_popular));
        String prefLang = sharedPrefs.getString(getString(R.string.pref_language_key),getString(R.string.pref_value_lang_en));

        if(!prefQuery.equals(query) | !prefLang.equals(movieLang)) {
            query = prefQuery;
            movieLang = prefLang;
            setPageCount(1);
            RetroMovies(getPageCount(), query, movieLang);
            String msgToast = getResources().getString(R.string.query_movies_toast);
            Toast toast = Toast.makeText(this, msgToast + " " + query, Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }

        if(id == R.id.about){
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        if(id == R.id.fav_movies_show){

            b_back.setVisibility(View.GONE);
            b_next.setVisibility(View.GONE);
            listFavMovies();

        }

        if(id == R.id.pop_movies_show){

            b_back.setVisibility(View.VISIBLE);
            b_next.setVisibility(View.VISIBLE);
            query = "popular";
            setPageCount(1);
            RetroMovies(getPageCount(), query, movieLang);
        }

        if(id == R.id.top_movies_show){

            b_back.setVisibility(View.VISIBLE);
            b_next.setVisibility(View.VISIBLE);
            query = "top";
            setPageCount(1);
            RetroMovies(getPageCount(), query, movieLang);
        }

        return super.onOptionsItemSelected(item);
    }

    public int getPageCount(){
        return this.pageCount;
    }

    public void setPageCount(int p){
        this.pageCount = p;

        if(pageCount > 1){
            b_back.setVisibility(View.VISIBLE);
        }else{
            b_back.setVisibility(View.GONE);
        }
    }

    public void changePageCount(String op){


        if(op.equals("add")){
            pageCount++;

        }else if(op.equals("del")){
            pageCount--;

        }

        if(pageCount > 1){
            b_back.setVisibility(View.VISIBLE);
        }else{
            b_back.setVisibility(View.GONE);
        }

    }

    public static String getApiKey(){
        return API_KEY;
    }

    public void RetroMovies(final int nPages, String query, final String movieLang){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Movie.getTmdbEndpoint())
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key",String.valueOf(API_KEY));
                        request.addEncodedQueryParam("page",String.valueOf(nPages));
                        request.addEncodedQueryParam("language",String.valueOf(movieLang));
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        MoviesApiService service = restAdapter.create(MoviesApiService.class);

        if(query.equals("popular")) {
            service.getPopularMOvies(new Callback<Movie.MovieResult>() {
                @Override
                public void success(Movie.MovieResult movieResult, Response response) {
                    mAdapter.setmMovieList(movieResult.getResults());
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        }else if(query.equals("top")){
            service.getTopMovies(new Callback<Movie.MovieResult>() {
                @Override
                public void success(Movie.MovieResult movieResult, Response response) {
                    mAdapter.setmMovieList(movieResult.getResults());
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        }

    }


    private void listFavMovies(){

        helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id, movie_id, title, poster, description, vote_average, release_date, backdrop FROM movies", null);
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

            Log.v("BACKDROP", faMovies.getBackdrop());

            fav.add(faMovies);

            Log.v("List",faMovies.getId() + faMovies.getTitle() + faMovies.getPoster());

            cursor.moveToNext();

        }
        cursor.close();

        mAdapter.setmMovieList(fav);
    }

    @Override
    protected void onDestroy(){

        try {
            helper.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        super.onDestroy();
    }
}
