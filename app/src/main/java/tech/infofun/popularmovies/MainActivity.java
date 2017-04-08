package tech.infofun.popularmovies;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

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
    static final String RESTORE_PAGE = "PageRestore";
    static final String LIST_STATE_KEY = "ListRestore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            RetroMovies(pageCount);
        }else{
            RetroMovies(savedInstanceState.getInt(RESTORE_PAGE));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mAdapter);



        FloatingActionButton b_back = (FloatingActionButton) findViewById(R.id.back);
        b_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(pageCount < 1){
                    pageCount = 1;
                }else {
                    pageCount--;
                }
                Log.v("PageCount: ",String.valueOf(pageCount));
                RetroMovies(pageCount);
            }
        });

        FloatingActionButton b_next = (FloatingActionButton) findViewById(R.id.next);
        b_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                pageCount++;
                RetroMovies(pageCount);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        mListState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        savedInstanceState.putParcelable(LIST_STATE_KEY,mListState);
        savedInstanceState.putInt(RESTORE_PAGE,pageCount);
        super.onSaveInstanceState(savedInstanceState);
        ;

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            pageCount = savedInstanceState.getInt(RESTORE_PAGE);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(mListState != null) {
            mRecyclerView.getLayoutManager().onRestoreInstanceState(mListState);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public MovieViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }


    public void RetroMovies(final int nPages){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key","API_KEY");
                        request.addEncodedQueryParam("page",String.valueOf(nPages));
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        MoviesApiService service = restAdapter.create(MoviesApiService.class);
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

    }
}
