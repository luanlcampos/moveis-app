package com.example.movies_luan_llima_campos.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse {
    // get the list of movies from the results field
    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "MoviesResponse{" +
                "movies=" + movies +
                '}';
    }
}
