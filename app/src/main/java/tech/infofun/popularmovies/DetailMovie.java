package tech.infofun.popularmovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private SQLiteDatabase db;
    TrailersAdapter mAdapter;
    RecyclerView mRecyclerView_trailer;

    ReviewsAdapter reviewsAdapter;
    RecyclerView getmRecyclerView_review;

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
        final TextView mVoteAverage = (TextView) findViewById(R.id.vote_average);
        TextView mReleaseDate = (TextView) findViewById(R.id.release_date);
        ImageView mBackPoster = (ImageView) findViewById(R.id.back_detail);
        mfavCheck = (CheckBox) findViewById(R.id.fav_check);

        helper = new DatabaseHelper(this);
        db = helper.getReadableDatabase();

        String query = "SELECT movie_id FROM movies WHERE movie_id = " + mId;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();

        int movie_count = cursor.getCount();

        if(movie_count > 0){
            mfavCheck.setChecked(true);
        }

        mfavCheck.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                db = helper.getWritableDatabase();

                if(mfavCheck.isChecked()) {
                    ContentValues values = new ContentValues();
                    values.put("movie_id", mId);
                    values.put("Title", mTitle);
                    values.put("poster", mPoster);
                    values.put("description", mDescription);
                    values.put("vote_average", mVote);
                    values.put("release_date", mRelease);
                    values.put("backdrop", mBack);

                    long insert_result = db.insert("movies", null, values);

                    if (insert_result != -1) {
                        Toast.makeText(DetailMovie.this, getString(R.string.success), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DetailMovie.this, getString(R.string.error), Toast.LENGTH_LONG).show();
                    }
                }else{
                    String where[] = new String[]{String.valueOf(mId)};
                    db.delete("movies","movie_id = ?",where);
                    Toast.makeText(DetailMovie.this, getString(R.string.removed), Toast.LENGTH_LONG).show();
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
