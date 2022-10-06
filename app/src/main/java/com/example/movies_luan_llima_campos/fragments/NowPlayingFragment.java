package com.example.movies_luan_llima_campos.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movies_luan_llima_campos.R;
import com.example.movies_luan_llima_campos.adapter.MovieAdapter;
import com.example.movies_luan_llima_campos.databinding.FragmentNowPlayingFragmentBinding;
import com.example.movies_luan_llima_campos.models.Movie;
import com.example.movies_luan_llima_campos.models.MoviesResponse;
import com.example.movies_luan_llima_campos.network.API;
import com.example.movies_luan_llima_campos.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowPlayingFragment extends Fragment {
    // binding the fragment to the layout
    private FragmentNowPlayingFragmentBinding binding;

    private final String TAG = "NowPlayingFragment";

    private ArrayList<Movie> moviesList = new ArrayList<>();

    // adapter for the recycler view
    private MovieAdapter adapter;

    // api
    private API api;

    public NowPlayingFragment() {
        super(R.layout.fragment_now_playing_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // configure the recyclerview
        adapter = new MovieAdapter(getContext(), moviesList);
        binding.rvNowPlaying.setAdapter(adapter);
        binding.rvNowPlaying.setLayoutManager(new LinearLayoutManager(getContext()));

        // load the data from the API and populate the recycler view
        api = RetrofitClient.getInstance().getAPI();

        // get the list of movies
        // - create a network request
        Call<MoviesResponse> call = api.getNowPlayingMovies();

        // - execute the network request
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (!response.isSuccessful()) {
                    // print the error message
                    Log.d(TAG, "onError: " + response.errorBody());
                    Log.d(TAG, "onError: " + response.code());
                    return;
                }
                // - get the list of movies
                MoviesResponse moviesResponse = response.body();

                // clear the list of movies
                moviesList.clear();

                // add the list of movies to the list of movies
                moviesList.addAll(moviesResponse.getMovies());

                // - populate the recycler view
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        // get the data from the API
        // populate the recycler view
    }

    // the listener for the recycler view
    public void onItemClickListener(Movie item) {
        Log.d(TAG, "onItemClickListener: " + item.getTitle());
    }

    // lifecycle functions - required for configuring view bindings
    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentNowPlayingFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}