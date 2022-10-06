package com.example.movies_luan_llima_campos.network;

import com.example.movies_luan_llima_campos.models.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    // base url of the api
    String BASE_URL = "https://api.themoviedb.org/3/movie/";

    // get all now playing movies and return a list of movies objects
    // https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed
    @GET("now_playing?api_key=de4785f301c50a95c8a99adf601c1192&language=en-US&page=1&region=CA")
    Call<MoviesResponse> getNowPlayingMovies();
}
