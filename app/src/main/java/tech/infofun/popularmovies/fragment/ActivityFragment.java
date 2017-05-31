package tech.infofun.popularmovies.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import tech.infofun.popularmovies.R;
import tech.infofun.popularmovies.adapter.MoviesAdapter;
import tech.infofun.popularmovies.service.MoviesRetrofit;

/**
 * Created by tfbarbosa on 14/05/17.
 */
public class ActivityFragment extends Fragment{

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
    private MoviesRetrofit mMoviesRetro;
    private FloatingActionButton b_back, b_next;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_main_fragment, container, false);

        b_back = (FloatingActionButton) view.findViewById(R.id.back);
        b_next = (FloatingActionButton) view.findViewById(R.id.next);

        if(getPageCount() == 1){
            b_back.setVisibility(View.GONE);
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mAdapter = new MoviesAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mMoviesRetro = new MoviesRetrofit(getActivity());

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


        return view;
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (savedInstanceState == null) {

            setQuery(getResources().getString(R.string.pref_value_popular));
            movieLang = sharedPrefs.getString(getString(R.string.pref_language_key), getString(R.string.pref_value_lang_en));
            mMoviesRetro.retroMovies(getPageCount(), getQuery(), movieLang);

        } else {

            String savedQuery = savedInstanceState.getString(MOVIE_QUERY);
            String savedLang = savedInstanceState.getString(LANG_MOVIE);
            String prefLang = sharedPrefs.getString(getString(R.string.pref_language_key), getString(R.string.pref_value_lang_en));

            if (!savedLang.equals(prefLang)) {

                movieLang = prefLang;
                setPageCount(1);
                mMoviesRetro.retroMovies(getPageCount(), getQuery(), movieLang);

            } else {

                setQuery(savedQuery);
                movieLang = savedLang;
                mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
                setPageCount(savedInstanceState.getInt(RESTORE_PAGE));
                mRecyclerView.getLayoutManager().onRestoreInstanceState(mListState);
                mMoviesRetro.retroMovies(getPageCount(), getQuery(), movieLang);
            }
        }
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

    public void refreshMenuMovies(String q){
        setPageCount(1);
        mMoviesRetro.retroMovies(getPageCount(), q, movieLang);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
