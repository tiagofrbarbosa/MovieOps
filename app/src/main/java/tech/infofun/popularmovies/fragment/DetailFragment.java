package tech.infofun.popularmovies.fragment;

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
import tech.infofun.popularmovies.database.MoviesDAO;
import tech.infofun.popularmovies.model.Movie;
import tech.infofun.popularmovies.service.MoviesRetrofit;

/**
 * Created by tfbarbosa on 14/05/17.
 */
public class DetailFragment extends Fragment{

    private MoviesDAO mMoviesDAO;
    public static TrailersAdapter mAdapter;
    public static ReviewsAdapter reviewsAdapter;
    private RecyclerView mRecyclerView_trailer;
    private RecyclerView getmRecyclerView_review;
    private MoviesRetrofit mMoviesDetails;
    private CheckBox mfavCheck;
    private int mId;
    private String mTitle,mPoster,mDescription,mVote,mRelease,mBack;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.movie_detail_fragment, container, false);

        mRecyclerView_trailer = (RecyclerView) view.findViewById(R.id.trailer_recycler);
        mRecyclerView_trailer.setLayoutManager(new LinearLayoutManager(getActivity()));

        getmRecyclerView_review = (RecyclerView) view.findViewById(R.id.review_recycler);
        getmRecyclerView_review.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMoviesDetails = new MoviesRetrofit();

        mAdapter = new TrailersAdapter(getActivity());
        mRecyclerView_trailer.setAdapter(mAdapter);

        reviewsAdapter = new ReviewsAdapter(getActivity());
        getmRecyclerView_review.setAdapter(reviewsAdapter);


        TextView mMovieTitle = (TextView) view.findViewById(R.id.movie_title);
        TextView mMovieDescription = (TextView) view.findViewById(R.id.movie_description);
        final TextView mVoteAverage = (TextView) view.findViewById(R.id.vote_average);
        TextView mReleaseDate = (TextView) view.findViewById(R.id.release_date);
        ImageView mBackPoster = (ImageView) view.findViewById(R.id.back_detail);
        mfavCheck = (CheckBox) view.findViewById(R.id.fav_check);



        if(getArguments() != null) {

            mId = getArguments().getInt("id");
            mTitle = getArguments().getString("title");
            mDescription = getArguments().getString("description");
            mVote = getArguments().getString("vote_average");
            mRelease = getArguments().getString("release_date");
            mBack = getArguments().getString("backdrop");
            mPoster = getArguments().getString("poster");

            mMoviesDetails.retroTrailers(mId);
            mMoviesDetails.retroReviews(mId);

            mMoviesDAO = new MoviesDAO(getActivity());

            if (mMoviesDAO.select_check(mId) > 0) {
                mfavCheck.setChecked(true);
            }

            mfavCheck.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    if (mfavCheck.isChecked()) {

                        Movie movie = new Movie(mId, mTitle, mPoster, mDescription,
                                mVote, mRelease, mBack);

                        long insert_result = mMoviesDAO.insert(movie);

                        if (insert_result != -1) {
                            Toast.makeText(getActivity(), getString(R.string.success), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        mMoviesDAO.delete(mId);
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

    @Override
    public void onDestroy(){
        mMoviesDAO.close();
        super.onDestroy();
    }
}
