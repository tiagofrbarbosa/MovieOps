package tech.infofun.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by tfbarbosa on 07/05/17.
 */
public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public Trailer trailerHolder;

    public TextView textView;
    public Button trailer_number;

    private final Context context;


    public TrailerViewHolder(View itemView){
        super(itemView);
        context = itemView.getContext();
        itemView.setOnClickListener(this);
        textView = (TextView) itemView.findViewById(R.id.text_trailer);
        trailer_number = (Button) itemView.findViewById(R.id.trailer_number);
        trailer_number.setOnClickListener(this);
        trailerHolder = new Trailer();
    }

    @Override
    public void onClick(View view){

        //This part was inspired by this answer in StackOverflow
        //http://stackoverflow.com/questions/41696939/open-youtube-video-at-a-specific-time-using-android-intent

        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(Trailer.getYouUrl() + textView.getText()));

        Intent appIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(Trailer.getYouApp() + textView.getText()));

        try {
            context.startActivity(appIntent);
        } catch (Exception ex) {
            context.startActivity(webIntent);
        }
    }


}