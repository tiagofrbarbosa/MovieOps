package tech.infofun.popularmovies;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tfbarbosa on 08/04/17.
 */
public class DetailMovie extends AppCompatActivity {

    private DatabaseHelper helper;
    TrailersAdapter mAdapter;
    RecyclerView mRecyclerView_trailer;

    ReviewsAdapter reviewsAdapter;
    RecyclerView getmRecyclerView_review;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        Bundle extras = getIntent().getExtras();
        final String mTitle = extras.getString("title");
        String mDescription = extras.getString("description");
        String mVote = extras.getString("vote_average");
        String mRelease = extras.getString("release_date");
        String mBack = Movie.getTmdbBackDropPath() + extras.getString("backdrop");
        final String mPoster = extras.getString("poster");
        final int mId = extras.getInt("id");

        mRecyclerView_trailer = (RecyclerView) findViewById(R.id.trailer_recycler);
        mRecyclerView_trailer.setLayoutManager(new LinearLayoutManager(this));

        getmRecyclerView_review = (RecyclerView) findViewById(R.id.review_recycler);
        getmRecyclerView_review.setLayoutManager(new LinearLayoutManager(this));


        mAdapter = new TrailersAdapter(this);
        mRecyclerView_trailer.setAdapter(mAdapter);
        retroTrailers(mId);

        reviewsAdapter = new ReviewsAdapter(this);
        getmRecyclerView_review.setAdapter(reviewsAdapter);
        retroReviews(mId);

        TextView mMovieTitle = (TextView) findViewById(R.id.movie_title);
        TextView mMovieDescription = (TextView) findViewById(R.id.movie_description);
        TextView mVoteAverage = (TextView) findViewById(R.id.vote_average);
        TextView mReleaseDate = (TextView) findViewById(R.id.release_date);
        ImageView mBackPoster = (ImageView) findViewById(R.id.back_detail);
        final CheckBox mfavCheck = (CheckBox) findViewById(R.id.fav_check);

        mfavCheck.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                mfavCheck.isChecked();

                SQLiteDatabase db = helper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("movie_id", mId);
                values.put("Title", mTitle);
                values.put("poster", mPoster);

                long insert_result = db.insert("movies", null, values);

                if(insert_result != -1){
                    Toast.makeText(DetailMovie.this, getString(R.string.success), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(DetailMovie.this, getString(R.string.error), Toast.LENGTH_LONG).show();
                }
            }
        });


        mMovieTitle.setText(mTitle);
        mMovieDescription.setText(mDescription);
        mVoteAverage.setText(mVote);
        mReleaseDate.setText(mRelease);

        Picasso.with(this)
                .load(mBack)
                .into(mBackPoster);

        helper = new DatabaseHelper(this);

    }


    @Override
    protected void onDestroy(){
        helper.close();
        super.onDestroy();
    }

    public void retroTrailers(final int movieId){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Movie.getTmdbEndpoint() + "/movie/" + movieId)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key",String.valueOf(MainActivity.getApiKey()));
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MoviesApiService service = restAdapter.create(MoviesApiService.class);
        service.getTrailers(new Callback<Trailer.TrailerResult>() {
            @Override
            public void success(Trailer.TrailerResult trailerResult, Response response) {
                mAdapter.setmMovieList(trailerResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    public void retroReviews(final int movieId){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Movie.getTmdbEndpoint() + "/movie/" + movieId)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key",String.valueOf(MainActivity.getApiKey()));
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MoviesApiService service = restAdapter.create(MoviesApiService.class);
        service.getReviews(new Callback<Review.ReviewResult>() {
            @Override
            public void success(Review.ReviewResult reviewResult, Response response) {
                reviewsAdapter.setmMovieList(reviewResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}
