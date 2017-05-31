package tech.infofun.popularmovies.fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import tech.infofun.popularmovies.R;
import tech.infofun.popularmovies.adapter.MoviesAdapter;
import tech.infofun.popularmovies.database.DatabaseMovies;
import tech.infofun.popularmovies.model.Movie;


/**
 * Created by tfbarbosa on 18/05/17.
 */
public class FavoriteFragment extends Fragment{

    private RecyclerView mRecyclerView;
    public static MoviesAdapter mAdapter;
    private ContentResolver resolver;
    private ContentValues values;
    private Uri uri;
    private DatabaseMovies dbMovies;
    private List<Movie> fav;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_main_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mAdapter = new MoviesAdapter(getActivity());

        resolver = getActivity().getContentResolver();
        dbMovies = new DatabaseMovies(resolver);
        fav = dbMovies.getFavoriteMovies();

        mAdapter.setmMovieList(fav);

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume(){

        fav.clear();
        fav = dbMovies.getFavoriteMovies();
        mAdapter.setmMovieList(fav);
        mRecyclerView.setAdapter(mAdapter);
        super.onResume();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
