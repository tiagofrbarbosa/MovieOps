package tech.infofun.popularmovies.activity;


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
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import tech.infofun.popularmovies.database.MoviesDAO;
import tech.infofun.popularmovies.R;
import tech.infofun.popularmovies.adapter.MoviesAdapter;
import tech.infofun.popularmovies.service.MoviesRetrofit;

public class MainActivity extends DebugActivity {

    private MoviesDAO mMoviesDAO;
    private RecyclerView mRecyclerView;
    public static MoviesAdapter mAdapter;
    private Parcelable mListState;
    private int pageCount = 1;
    private String query;
    private String movieLang;
    private static final String RESTORE_PAGE = "PageRestore";
    private static final String LIST_STATE_KEY = "ListRestore";
    private static final String MOVIE_QUERY = "MovieQuery";
    private static final String LANG_MOVIE = "movieLang";
    private static final String API_KEY = "API_KEY_HERE";
    private MoviesRetrofit mMoviesRetro;
    private  FloatingActionButton b_back, b_next;



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
        mMoviesRetro = new MoviesRetrofit();


        if(savedInstanceState == null) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            setQuery(sharedPrefs.getString(getString(R.string.pref_order_key),getString(R.string.pref_value_popular)));
            movieLang = sharedPrefs.getString(getString(R.string.pref_language_key),getString(R.string.pref_value_lang_en));
            mMoviesRetro.retroMovies(getPageCount(),getQuery(), movieLang);
        }else{
            mMoviesRetro.retroMovies(savedInstanceState.getInt(RESTORE_PAGE),savedInstanceState.getString(MOVIE_QUERY), savedInstanceState.getString(LANG_MOVIE));
        }

            b_back.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if (getPageCount() > 1) {
                        changePageCount(false);
                    }

                    mMoviesRetro.retroMovies(getPageCount(), getQuery(), movieLang);
                }
            });


        b_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                changePageCount(true);

                mMoviesRetro.retroMovies(getPageCount(),getQuery(),movieLang);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        mListState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        savedInstanceState.putParcelable(LIST_STATE_KEY,mListState);
        savedInstanceState.putInt(RESTORE_PAGE,getPageCount());
        savedInstanceState.putString(MOVIE_QUERY,getQuery());
        savedInstanceState.putString(LANG_MOVIE, movieLang);
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

                setQuery(prefQuery);
                movieLang = prefLang;
                setPageCount(1);
                mMoviesRetro.retroMovies(getPageCount(), getQuery(), movieLang);

            } else {

                setQuery(savedQuery);
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

        if(!prefQuery.equals(getQuery()) | !prefLang.equals(movieLang)) {
            setQuery(prefQuery);
            movieLang = prefLang;
            setPageCount(1);
            mMoviesRetro.retroMovies(getPageCount(), getQuery(), movieLang);
            String msgToast = getResources().getString(R.string.query_movies_toast);
            Toast toast = Toast.makeText(this, msgToast + " " + getQuery(), Toast.LENGTH_SHORT);
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
            Intent intent = new Intent(this, FavoriteActivity.class);
            startActivity(intent);
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

    public void changePageCount(boolean operation){

        if(operation){
            pageCount++;
        }else{
            pageCount--;
        }

        if(pageCount > 1){
            b_back.setVisibility(View.VISIBLE);
        }else{
            b_back.setVisibility(View.GONE);
        }

    }

    public void setQuery(String query){
        this.query = query;
    }

    public String getQuery(){
        return query;
    }

    public static String getApiKey(){
        return API_KEY;
    }

    @Override
    protected void onDestroy(){
        try{
            mMoviesDAO.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        super.onDestroy();
    }
}
