package tech.infofun.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by tfbarbosa on 08/04/17.
 */
public class DetailMovie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        TextView mMovieTitle = (TextView) findViewById(R.id.movie_title);
        TextView mMovieDescription = (TextView) findViewById(R.id.movie_description);
        TextView mVoteAverage = (TextView) findViewById(R.id.vote_average);
        TextView mReleaseDate = (TextView) findViewById(R.id.release_date);
        ImageView mImagePoster = (ImageView) findViewById(R.id.detail_poster);
        ImageView mBackPoster = (ImageView) findViewById(R.id.back_detail);

        Bundle extras = getIntent().getExtras();
        String mTitle = extras.getString("title");
        String mDescription = extras.getString("description");
        String mVote = extras.getString("vote_average");
        String mRelease = extras.getString("release_date");
        String mPoster = extras.getString("poster");
        String mBack = extras.getString("backdrop");

        mMovieTitle.setText(mTitle);
        mMovieDescription.setText(mDescription);
        mVoteAverage.setText(mVote);
        mReleaseDate.setText(mRelease);

        Picasso.with(this)
                .load(mPoster)
                .into(mImagePoster);

        Picasso.with(this)
                .load(mBack)
                .into(mBackPoster);
    }
}
