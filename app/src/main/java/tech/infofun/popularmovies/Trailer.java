package tech.infofun.popularmovies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tfbarbosa on 07/05/17.
 */
public class Trailer {

    @SerializedName("key")
    private String key;

    public Trailer(){}

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }

    public static class TrailerResult{
        private List<Trailer> results;

        public List<Trailer> getResults(){
            return results;
        }
    }
}
