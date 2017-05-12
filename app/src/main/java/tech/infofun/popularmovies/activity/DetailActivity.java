package tech.infofun.popularmovies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import tech.infofun.popularmovies.database.MoviesDAO;
import tech.infofun.popularmovies.R;
import tech.infofun.popularmovies.adapter.ReviewsAdapter;
import tech.infofun.popularmovies.adapter.TrailersAdapter;
import tech.infofun.popularmovies.model.Movie;
import tech.infofun.popularmovies.service.MoviesRetrofit;

/**
 * Created by tfbarbosa on 08/04/17.
 */
public class DetailActivity extends AppCompatActivity {

    private MoviesDAO mMoviesDAO;

    public static TrailersAdapter mAdapter;
    RecyclerView mRecyclerView_trailer;

    public static ReviewsAdapter reviewsAdapter;
    RecyclerView getmRecyclerView_review;

    private MoviesRetrofit mMoviesDetails;

    CheckBox mfavCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        Bundle extras = getIntent().getExtras();
        final String mTitle = extras.getString("title");
        final String mDescription = extras.getString("description");
        final String mVote = extras.getString("vote_average");
        final String mRelease = extras.getString("release_date");
        final String mBack = extras.getString("backdrop");
        final String mPoster = extras.getString("poster");
        final int mId = extras.getInt("id");

        Movie movie = new Movie(mId, mTitle, mPoster, mDescription,
                mVote, mRelease, mBack);


        mRecyclerView_trailer = (RecyclerView) findViewById(R.id.trailer_recycler);
        mRecyclerView_trailer.setLayoutManager(new LinearLayoutManager(this));

        getmRecyclerView_review = (RecyclerView) findViewById(R.id.review_recycler);
        getmRecyclerView_review.setLayoutManager(new LinearLayoutManager(this));
        mMoviesDetails = new MoviesRetrofit();

        mAdapter = new TrailersAdapter(this);
        mRecyclerView_trailer.setAdapter(mAdapter);
        mMoviesDetails.retroTrailers(mId);

        reviewsAdapter = new ReviewsAdapter(this);
        getmRecyclerView_review.setAdapter(reviewsAdapter);
        mMoviesDetails.retroReviews(mId);

        TextView mMovieTitle = (TextView) findViewById(R.id.movie_title);
        TextView mMovieDescription = (TextView) findViewById(R.id.movie_description);
        final TextView mVoteAverage = (TextView) findViewById(R.id.vote_average);
        TextView mReleaseDate = (TextView) findViewById(R.id.release_date);
        ImageView mBackPoster = (ImageView) findViewById(R.id.back_detail);
        mfavCheck = (CheckBox) findViewById(R.id.fav_check);

        mMoviesDAO = new MoviesDAO(DetailActivity.this);

        int movie_count = mMoviesDAO.select_check(mId);

        if(movie_count > 0){
            mfavCheck.setChecked(true);
        }

        mfavCheck.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){


                if(mfavCheck.isChecked()) {

                    Movie movie = new Movie(mId, mTitle, mPoster, mDescription,
                            mVote, mRelease, mBack);

                    long insert_result = mMoviesDAO.insert(movie);

                    if (insert_result != -1) {
                        Toast.makeText(DetailActivity.this, getString(R.string.success), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DetailActivity.this, getString(R.string.error), Toast.LENGTH_LONG).show();
                    }
                }else{
                    mMoviesDAO.delete(mId);
                    Toast.makeText(DetailActivity.this, getString(R.string.removed), Toast.LENGTH_LONG).show();
                }
            }
        });


        mMovieTitle.setText(mTitle);
        mMovieDescription.setText(mDescription);
        mVoteAverage.setText(mVote);
        mReleaseDate.setText(mRelease);

        Picasso.with(this)
                .load(Movie.getTmdbBackDropPath() + mBack)
                .into(mBackPoster);
    }


    @Override
    protected void onDestroy(){
        mMoviesDAO.close();
        super.onDestroy();
    }
}
