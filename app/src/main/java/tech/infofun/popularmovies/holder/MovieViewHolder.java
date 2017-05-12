package tech.infofun.popularmovies.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import tech.infofun.popularmovies.activity.DetailActivity;
import tech.infofun.popularmovies.model.Movie;
import tech.infofun.popularmovies.R;

/**
 * Created by tfbarbosa on 08/04/17.
 */
public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public Movie movieHolder;

    public ImageView imageView;

    private final Context context;

    public MovieViewHolder(View itemView){
        super(itemView);
        context = itemView.getContext();
        itemView.setOnClickListener(this);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        movieHolder = new Movie();
    }

    @Override
    public void onClick(View view){
        Intent intent = new Intent(context,DetailActivity.class);
        intent.putExtra("title", movieHolder.getTitle());
        intent.putExtra("description",movieHolder.getDescription());
        intent.putExtra("vote_average",movieHolder.getVote_average());
        intent.putExtra("release_date",movieHolder.getRelease_date());
        intent.putExtra("backdrop",movieHolder.getBackdrop());
        intent.putExtra("id",movieHolder.getId());
        intent.putExtra("poster",movieHolder.getPoster());
        context.startActivity(intent);
    }


}
