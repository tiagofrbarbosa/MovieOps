package tech.infofun.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by tfbarbosa on 08/04/17.
 */
public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView imageView;
    private String title;
    private String description;
    private String vote_average;
    private String release_date;
    private String poster;
    private String backdrop;
    private final Context context;

    public MovieViewHolder(View itemView){
        super(itemView);
        context = itemView.getContext();
        itemView.setOnClickListener(this);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }

    @Override
    public void onClick(View view){
        Intent intent = new Intent(context,DetailMovie.class);
        intent.putExtra("title", this.title);
        intent.putExtra("description",this.description);
        intent.putExtra("vote_average",this.vote_average);
        intent.putExtra("release_date",this.release_date);
        intent.putExtra("poster",this.poster);
        intent.putExtra("backdrop",this.backdrop);
        context.startActivity(intent);
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setVote_average(String vote_average){
        this.vote_average = vote_average;
    }

    public void setRelease_date(String release_date){
        this.release_date = release_date;
    }

    public void setPoster(String poster){
        this.poster = poster;
    }

    public void setBackdrop(String backdrop){
        this.backdrop = backdrop;
    }
}
