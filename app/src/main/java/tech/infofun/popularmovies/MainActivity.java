package tech.infofun.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {


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



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton b_back = (FloatingActionButton) findViewById(R.id.back);
        FloatingActionButton b_next = (FloatingActionButton) findViewById(R.id.next);

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

        return super.onOptionsItemSelected(item);
    }

    public int getPageCount(){
        return this.pageCount;
    }

    public void setPageCount(int p){
        this.pageCount = p;
        Log.v("PageCount: ",String.valueOf(getPageCount()));
    }

    public void changePageCount(String op){
        FloatingActionButton b_back = (FloatingActionButton) findViewById(R.id.back);

        if(op.equals("add")){
            pageCount++;
            Log.v("PageCount: ",String.valueOf(getPageCount()));

        }else if(op.equals("del")){
            pageCount--;
            Log.v("PageCount: ",String.valueOf(getPageCount()));

        }

    }

    public void RetroMovies(final int nPages, String query, final String movieLang){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
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
}
