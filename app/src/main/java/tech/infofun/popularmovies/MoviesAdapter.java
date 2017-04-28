package tech.infofun.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tfbarbosa on 04/04/2017.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder>{
        private List<Movie> mMovieList;
        private LayoutInflater mInflater;
        private Context mContext;

        public MoviesAdapter(Context context){
            mContext = context;
            mInflater = LayoutInflater.from(context);
            mMovieList = new ArrayList<>();
        }

        @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = mInflater.inflate(R.layout.row_movie,parent,false);
            MovieViewHolder viewHolder = new MovieViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MovieViewHolder holder,int position){
            Movie movie = mMovieList.get(position);

            holder.setTitle(movie.getTitle());
            holder.setDescription(movie.getDescription());
            holder.setVote_average(movie.getVote_average());
            holder.setRelease_date(movie.getRelease_date());
            holder.setPoster(movie.getPoster());
            holder.setBackdrop(movie.getBackdrop());

            Picasso.with(mContext)
                    .load(movie.getPoster())
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount(){
            return (mMovieList == null) ? 0 : mMovieList.size();
        }

        public void setmMovieList(List<Movie> movieList){
            mMovieList.clear();
            mMovieList.addAll(movieList);
            notifyDataSetChanged();
        }
}
