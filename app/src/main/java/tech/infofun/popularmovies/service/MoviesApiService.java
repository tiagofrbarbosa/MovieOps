package tech.infofun.popularmovies.service;

import retrofit.Callback;
import retrofit.http.GET;
import tech.infofun.popularmovies.model.Movie;
import tech.infofun.popularmovies.model.Review;
import tech.infofun.popularmovies.model.Trailer;

/**
 * Created by tfbarbosa on 04/04/17.
 */
public interface MoviesApiService {
    @GET("/movie/popular")
    void getPopularMOvies(Callback<Movie.MovieResult> cb);

    @GET("/movie/top_rated")
    void getTopMovies(Callback<Movie.MovieResult> cb);

    @GET("/videos")
    void getTrailers(Callback<Trailer.TrailerResult> cb);

    @GET("/reviews")
    void getReviews(Callback<Review.ReviewResult> cb);

    }