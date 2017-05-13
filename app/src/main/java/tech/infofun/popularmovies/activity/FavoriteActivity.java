package tech.infofun.popularmovies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import tech.infofun.popularmovies.R;
import tech.infofun.popularmovies.adapter.MoviesAdapter;
import tech.infofun.popularmovies.database.MoviesDAO;
import tech.infofun.popularmovies.model.Movie;

/**
 * Created by tfbarbosa on 13/05/17.
 */
public class FavoriteActivity extends AppCompatActivity {

    private MoviesDAO mMoviesDAO;
    private RecyclerView mRecyclerView;
    public static MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mAdapter = new MoviesAdapter(this);

        mMoviesDAO = new MoviesDAO(this);
        List<Movie> fav = mMoviesDAO.select_all();
        mAdapter.setmMovieList(fav);

        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    protected void onDestroy(){

        try {
            mMoviesDAO.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        super.onDestroy();
    }
}
