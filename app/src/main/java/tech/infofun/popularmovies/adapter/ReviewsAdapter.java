package tech.infofun.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tech.infofun.popularmovies.R;
import tech.infofun.popularmovies.model.Review;
import tech.infofun.popularmovies.holder.ReviewViewHolder;

/**
 * Created by tfbarbosa on 07/05/2017.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewViewHolder>{
    private List<Review> mMovieList;
    private LayoutInflater mInflater;
    private Context mContext;

    public ReviewsAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mMovieList = new ArrayList<>();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.review_movie,parent,false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder,int position){
        Review review = mMovieList.get(position);
        holder.textReview.setText(review.getReview());
        holder.textAuthor.setText(review.getAuthor());
    }

    @Override
    public int getItemCount(){
        return (mMovieList == null) ? 0 : mMovieList.size();
    }

    public void setmMovieList(List<Review> reviewList){
        mMovieList.clear();
        mMovieList.addAll(reviewList);
        notifyDataSetChanged();
    }
}
