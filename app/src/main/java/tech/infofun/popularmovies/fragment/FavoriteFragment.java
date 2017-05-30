package tech.infofun.popularmovies.fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import tech.infofun.popularmovies.R;
import tech.infofun.popularmovies.adapter.MoviesAdapter;
import tech.infofun.popularmovies.model.Movie;
import tech.infofun.popularmovies.provider.MoviesContract;


/**
 * Created by tfbarbosa on 18/05/17.
 */
public class FavoriteFragment extends Fragment{

    private RecyclerView mRecyclerView;
    public static MoviesAdapter mAdapter;
    private ContentResolver resolver;
    private ContentValues values;
    private Uri uri;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_main_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mAdapter = new MoviesAdapter(getActivity());

        resolver = getActivity().getContentResolver();
        values = new ContentValues();
        uri = MoviesContract.Movie.CONTENT_URI;

        String[] projection = new String[]{MoviesContract.Movie._ID, MoviesContract.Movie.MOVIE_ID, MoviesContract.Movie.TITLE, MoviesContract.Movie.POSTER, MoviesContract.Movie.DESCRIPTION,
                                           MoviesContract.Movie.VOTE_AVERAGE, MoviesContract.Movie.RELEASE_DATE, MoviesContract.Movie.BACKDROP};

        String selection = MoviesContract.Movie.IS_FAVORITE + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(1)};
        String sortOrder = null;

        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);

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
            fav.add(faMovies);
            cursor.moveToNext();

        }
        cursor.close();

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

        String[] projection = new String[]{MoviesContract.Movie._ID, MoviesContract.Movie.MOVIE_ID, MoviesContract.Movie.TITLE, MoviesContract.Movie.POSTER, MoviesContract.Movie.DESCRIPTION,
                MoviesContract.Movie.VOTE_AVERAGE, MoviesContract.Movie.RELEASE_DATE, MoviesContract.Movie.BACKDROP};

        String selection = MoviesContract.Movie.IS_FAVORITE + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(1)};
        String sortOrder = null;

        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);

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
            fav.add(faMovies);
            cursor.moveToNext();

        }
        cursor.close();

        mAdapter.setmMovieList(fav);

        mRecyclerView.setAdapter(mAdapter);

        super.onResume();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
