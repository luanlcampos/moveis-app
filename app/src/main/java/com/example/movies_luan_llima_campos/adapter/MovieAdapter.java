package com.example.movies_luan_llima_campos.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies_luan_llima_campos.databinding.MovieRowLayoutBinding;
import com.example.movies_luan_llima_campos.db.MyDatabase;
import com.example.movies_luan_llima_campos.models.Movie;
import com.example.movies_luan_llima_campos.models.UserTicket;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final Context context;
    private final ArrayList<Movie> itemArrayList;
    MovieRowLayoutBinding binding;

    public MovieAdapter(Context context, ArrayList<Movie> items) {
        this.itemArrayList = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(MovieRowLayoutBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie currentItem = itemArrayList.get(position);
        holder.bind(context, currentItem);
    }

    @Override
    public int getItemCount() {
        Log.d("ItemAdapter", "getItemCount: Number of items " + this.itemArrayList.size());
        return this.itemArrayList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        MovieRowLayoutBinding itemBinding;
        private MyDatabase db;

        public MovieViewHolder(MovieRowLayoutBinding binding) {
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        public void bind(Context context, Movie currentItem) {
            // initialize the database
            db = MyDatabase.getDatabase(context);
            // TODO: Write the code to update recycler view's row layout
            // bind the image
            String imageBaseUrl = "https://image.tmdb.org/t/p/w500/";
            if (currentItem.getBackdropPath() == null) {
                Glide.with(context).load(imageBaseUrl + currentItem.getPosterPath()).into(itemBinding.ivMoviePhoto);
            } else {
                Glide.with(context).load(imageBaseUrl + currentItem.getBackdropPath()).into(itemBinding.ivMoviePhoto);
            }

            // bind the title
            itemBinding.tvMovieTitle.setText(currentItem.getTitle());

            // bind the release date
            itemBinding.tvReleaseDate.setText(String.format("Released: %s", currentItem.getReleaseDate()));

            // bind the vote average
            int voteAverage = (int) (currentItem.getVoteAverage() * 10);
            if (voteAverage == 0) {
                itemBinding.tvAverageRating.setText("No votes");
            } else {
                itemBinding.tvAverageRating.setText(String.format("%d%%", voteAverage));
            }

            // bind the overview
            itemBinding.tvSummary.setText(currentItem.getOverview());

            // on button clicked, if the movie is not in database, add it, otherwise, increase the number of tickets
            itemBinding.btnBuyTicket.setOnClickListener(v -> {
                // try to get the movie from the database by id
                UserTicket userTicket = db.userTicketsDAO().getUserTicketsById(currentItem.getId());

                Log.d("MovieAdapter", "onClick: " + userTicket);

                // if the movie is not in database, add it with 1 ticket
                if (userTicket == null) {
                    UserTicket newUserTicket = new UserTicket(currentItem.getId(), currentItem.getTitle(),1);
                    db.userTicketsDAO().insertUserTicket(newUserTicket);
                } else {
                    // if the movie is in database, increase the number of tickets
                    userTicket.setTicketsQty(userTicket.getTicketsQty() + 1);
                    db.userTicketsDAO().updateUserTicket(userTicket);
                }
                Snackbar.make(this.itemBinding.getRoot(), "Ticket purchased", Snackbar.LENGTH_SHORT).show();
            });

        }
    }
}
