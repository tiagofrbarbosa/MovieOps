package tech.infofun.popularmovies.fragment;

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
import tech.infofun.popularmovies.database.MoviesDAO;
import tech.infofun.popularmovies.model.Movie;

/**
 * Created by tfbarbosa on 18/05/17.
 */
public class FavoriteFragment extends Fragment{

    private MoviesDAO mMoviesDAO;
    private RecyclerView mRecyclerView;
    public static MoviesAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_main_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mAdapter = new MoviesAdapter(getActivity());

        mMoviesDAO = new MoviesDAO(getActivity());
        List<Movie> fav = mMoviesDAO.select_all();
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
    public void onDestroy(){

        try {
            mMoviesDAO.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        super.onDestroy();
    }
}
