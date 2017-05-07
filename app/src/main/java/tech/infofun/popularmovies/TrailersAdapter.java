package tech.infofun.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tfbarbosa on 07/05/2017.
 */
public class TrailersAdapter extends RecyclerView.Adapter<TrailerViewHolder>{
    private List<Trailer> mMovieList;
    private LayoutInflater mInflater;
    private Context mContext;

    public TrailersAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mMovieList = new ArrayList<>();
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.trailer_movie,parent,false);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder,int position){
        Trailer trailer = mMovieList.get(position);
        holder.textView.setText(trailer.getKey());

    }

    @Override
    public int getItemCount(){
        return (mMovieList == null) ? 0 : mMovieList.size();
    }

    public void setmMovieList(List<Trailer> trailerList){
        mMovieList.clear();
        mMovieList.addAll(trailerList);
        notifyDataSetChanged();
    }
}
