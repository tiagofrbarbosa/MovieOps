package tech.infofun.popularmovies.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tech.infofun.popularmovies.R;
import tech.infofun.popularmovies.model.Review;

/**
 * Created by tfbarbosa on 07/05/17.
 */
public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public Review reviewHolder;

    public TextView textReview;
    public TextView textAuthor;

    private final Context context;


    public ReviewViewHolder(View itemView){
        super(itemView);
        context = itemView.getContext();
        itemView.setOnClickListener(this);
        textReview = (TextView) itemView.findViewById(R.id.text_review);
        textAuthor = (TextView) itemView.findViewById(R.id.text_author);
        reviewHolder = new Review();
    }

    @Override
    public void onClick(View view){

    }


}