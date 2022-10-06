package com.example.movies_luan_llima_campos.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies_luan_llima_campos.databinding.MovieRowLayoutBinding;
import com.example.movies_luan_llima_campos.databinding.TicketRowLayoutBinding;
import com.example.movies_luan_llima_campos.db.MyDatabase;
import com.example.movies_luan_llima_campos.listeners.OnItemClickListener;
import com.example.movies_luan_llima_campos.models.Movie;
import com.example.movies_luan_llima_campos.models.UserTicket;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
    private final Context context;
    private final ArrayList<UserTicket> itemArrayList;
    private final OnItemClickListener clickListener;
    TicketRowLayoutBinding binding;

    public TicketAdapter(Context context, ArrayList<UserTicket> items, OnItemClickListener clickListener) {
        this.itemArrayList = items;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public TicketAdapter.TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TicketViewHolder(TicketRowLayoutBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        UserTicket currentItem = itemArrayList.get(position);
        holder.bind(context, currentItem, clickListener);
    }

    @Override
    public int getItemCount() {
        Log.d("TicketAdapter", "getItemCount: Number of items " + this.itemArrayList.size());
        return this.itemArrayList.size();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        TicketRowLayoutBinding itemBinding;

        public TicketViewHolder(TicketRowLayoutBinding binding) {
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        public void bind(Context context, UserTicket currentItem, OnItemClickListener clickListener) {
            // LOG the current item
            Log.d("TicketAdapter", "bind: " + currentItem.toString());
            itemBinding.tvTicketTitle.setText(currentItem.getMovieTitle());
            itemBinding.tvTicketsPurchased.setText(
                    String.format("Tickets purchased: %d", currentItem.getTicketsQty()));

            // onClick detection
            itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TicketAdapter", "onClick: " + currentItem.getMovieTitle());
                    clickListener.onItemClickListener(currentItem);
                }
            });

        }
    }
}
