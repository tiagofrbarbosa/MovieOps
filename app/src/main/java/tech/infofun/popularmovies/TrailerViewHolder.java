package tech.infofun.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by tfbarbosa on 07/05/17.
 */
public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public Trailer trailerHolder;

    public TextView textView;


    public TrailerViewHolder(View itemView){
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.text_trailer);
        trailerHolder = new Trailer();
    }

    @Override
    public void onClick(View view){

    }


}