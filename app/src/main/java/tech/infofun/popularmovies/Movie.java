package tech.infofun.popularmovies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tfbarbosa on 04/04/2017.
 */
public class Movie {

    static final String TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w500";

    @SerializedName("original_title")
    private String title;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("overview")
    private String description;

    @SerializedName("backdrop_path")
    private String backdrop;

    private String vote_average;

    private String release_date;

    public Movie(){}

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getPoster(){
        return TMDB_IMAGE_PATH + poster;
    }

    public String getDescription(){
        return description;
    }

    public String getBackdrop(){
        return TMDB_IMAGE_PATH + backdrop;
    }

    public String getVote_average(){
        return vote_average;
    }

    public String getRelease_date(){
        return release_date;
    }

    public static class MovieResult{
            private List<Movie> results;

            public List<Movie> getResults(){
                return results;
            }
        }
}
