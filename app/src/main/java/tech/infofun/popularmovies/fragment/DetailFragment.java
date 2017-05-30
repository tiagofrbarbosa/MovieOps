package tech.infofun.popularmovies.fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import tech.infofun.popularmovies.R;
import tech.infofun.popularmovies.adapter.ReviewsAdapter;
import tech.infofun.popularmovies.adapter.TrailersAdapter;
import tech.infofun.popularmovies.model.Movie;
import tech.infofun.popularmovies.provider.MoviesContract;
import tech.infofun.popularmovies.provider.MoviesProvider;
import tech.infofun.popularmovies.service.MoviesRetrofit;

/**
 * Created by tfbarbosa on 14/05/17.
 */
public class DetailFragment extends Fragment{

    public static TrailersAdapter mAdapter;
    public static ReviewsAdapter reviewsAdapter;
    private RecyclerView mRecyclerView_trailer;
    private RecyclerView getmRecyclerView_review;
    private MoviesRetrofit mMoviesDetails;
    private CheckBox mfavCheck;
    private int mId, mIsFavorite;
    private String mTitle,mPoster,mDescription,mVote,mRelease,mBack;
    private TextView mMovieTitle, mMovieDescription,mVoteAverage,mReleaseDate;
    private ImageView mBackPoster;
    private ContentResolver resolver;
    private ContentValues values;
    private Uri uri;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.movie_detail_fragment, container, false);

        mRecyclerView_trailer = (RecyclerView) view.findViewById(R.id.trailer_recycler);
        mRecyclerView_trailer.setLayoutManager(new LinearLayoutManager(getActivity()));

        getmRecyclerView_review = (RecyclerView) view.findViewById(R.id.review_recycler);
        getmRecyclerView_review.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMoviesDetails = new MoviesRetrofit(getActivity());

        mAdapter = new TrailersAdapter(getActivity());
        mRecyclerView_trailer.setAdapter(mAdapter);

        reviewsAdapter = new ReviewsAdapter(getActivity());
        getmRecyclerView_review.setAdapter(reviewsAdapter);


        mMovieTitle = (TextView) view.findViewById(R.id.movie_title);
        mMovieDescription = (TextView) view.findViewById(R.id.movie_description);
        mVoteAverage = (TextView) view.findViewById(R.id.vote_average);
        mReleaseDate = (TextView) view.findViewById(R.id.release_date);
        mBackPoster = (ImageView) view.findViewById(R.id.back_detail);
        mfavCheck = (CheckBox) view.findViewById(R.id.fav_check);
        mfavCheck.setVisibility(View.GONE);


        resolver = getActivity().getContentResolver();
        values = new ContentValues();
        uri = MoviesContract.Movie.CONTENT_URI;


        if(getArguments() != null) {

            mId = getArguments().getInt("id");
            mTitle = getArguments().getString("title");
            mDescription = getArguments().getString("description");
            mVote = getArguments().getString("vote_average");
            mRelease = getArguments().getString("release_date");
            mBack = getArguments().getString("backdrop");
            mPoster = getArguments().getString("poster");

            mfavCheck.setVisibility(View.VISIBLE);

            values.put(MoviesContract.Movie.MOVIE_ID, mId);
            values.put(MoviesContract.Movie.TITLE, mTitle);
            values.put(MoviesContract.Movie.POSTER, mPoster);
            values.put(MoviesContract.Movie.DESCRIPTION, mDescription);
            values.put(MoviesContract.Movie.VOTE_AVERAGE, mVote);
            values.put(MoviesContract.Movie.RELEASE_DATE, mRelease);
            values.put(MoviesContract.Movie.BACKDROP, mBack);

            mMoviesDetails.retroTrailers(mId);
            mMoviesDetails.retroReviews(mId);

            String[] projection = new String[]{MoviesContract.Movie.MOVIE_ID};
            final String selection = MoviesContract.Movie.MOVIE_ID + " = ?";
            final String[] selectionArgs = new String[]{String.valueOf(mId)};
            String sortOrder = null;

            Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);

            cursor.moveToFirst();
            int mCursorCount;

            try {
                mCursorCount =  cursor.getCount();
            }catch (Exception e){
                mCursorCount = 0;
            }

            Log.v("mCursorCount", String.valueOf(mCursorCount));

            if (mCursorCount > 0) {
                mfavCheck.setChecked(true);
            }

            mfavCheck.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    if (mfavCheck.isChecked()) {

                        values.put(MoviesContract.Movie.IS_FAVORITE, 1);

                        Uri insert_result = resolver.insert(uri,values);

                        if (insert_result != null) {
                            Toast.makeText(getActivity(), getString(R.string.success), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        resolver.delete(uri,selection,selectionArgs);
                        Toast.makeText(getActivity(), getString(R.string.removed), Toast.LENGTH_LONG).show();
                    }
                }
            });


            mMovieTitle.setText(mTitle);
            mMovieDescription.setText(mDescription);
            mVoteAverage.setText(mVote);
            mReleaseDate.setText(mRelease);

            Picasso.with(getActivity())
                    .load(Movie.getTmdbBackDropPath() + mBack)
                    .into(mBackPoster);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    }

    public void setFragmentData(Movie movie){

        mId = movie.getId();
        mTitle = movie.getTitle();
        mDescription = movie.getDescription();
        mVote = movie.getVote_average();
        mRelease = movie.getRelease_date();
        mBack = movie.getBackdrop();
        mPoster = movie.getPoster();

        mMovieTitle.setText(mTitle);
        mMovieDescription.setText(mDescription);
        mVoteAverage.setText(mVote);
        mReleaseDate.setText(mRelease);
        mfavCheck.setVisibility(View.VISIBLE);

        Picasso.with(getActivity())
                .load(Movie.getTmdbBackDropPath() + mBack)
                .into(mBackPoster);

        mfavCheck.setChecked(false);

        values.clear();
        values.put(MoviesContract.Movie.MOVIE_ID, mId);
        values.put(MoviesContract.Movie.TITLE, mTitle);
        values.put(MoviesContract.Movie.POSTER, mPoster);
        values.put(MoviesContract.Movie.DESCRIPTION, mDescription);
        values.put(MoviesContract.Movie.VOTE_AVERAGE, mVote);
        values.put(MoviesContract.Movie.RELEASE_DATE, mRelease);
        values.put(MoviesContract.Movie.BACKDROP, mBack);

        mMoviesDetails.retroTrailers(mId);
        mMoviesDetails.retroReviews(mId);

        String[] projection = new String[]{MoviesContract.Movie.MOVIE_ID};
        final String selection = MoviesContract.Movie.MOVIE_ID + " = ?";
        final String[] selectionArgs = new String[]{String.valueOf(mId)};
        String sortOrder = null;

        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);

        cursor.moveToFirst();
        int mCursorCount;

        try {
            mCursorCount =  cursor.getCount();
        }catch (Exception e){
            mCursorCount = 0;
        }


        if (mCursorCount > 0) {
            mfavCheck.setChecked(true);
        }

        mfavCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (mfavCheck.isChecked()) {

                    values.put(MoviesContract.Movie.IS_FAVORITE, 1);

                    Uri insert_result = resolver.insert(uri,values);

                    if (insert_result != null) {
                        Toast.makeText(getActivity(), getString(R.string.success), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
                    }
                } else {
                    resolver.delete(uri,selection,selectionArgs);
                    Toast.makeText(getActivity(), getString(R.string.removed), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
