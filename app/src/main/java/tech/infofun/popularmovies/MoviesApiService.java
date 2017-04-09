package tech.infofun.popularmovies;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by tfbarbosa on 04/04/17.
 */
public interface MoviesApiService {
    @GET("/movie/popular")
    void getPopularMOvies(Callback<Movie.MovieResult> cb);

    @GET("/movie/top_rated")
    void getTopMovies(Callback<Movie.MovieResult> cb);

    }