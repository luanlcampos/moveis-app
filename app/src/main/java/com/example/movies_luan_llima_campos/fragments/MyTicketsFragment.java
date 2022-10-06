package com.example.movies_luan_llima_campos.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movies_luan_llima_campos.R;
import com.example.movies_luan_llima_campos.adapter.TicketAdapter;
import com.example.movies_luan_llima_campos.databinding.FragmentMyTicketsBinding;
import com.example.movies_luan_llima_campos.databinding.FragmentNowPlayingFragmentBinding;
import com.example.movies_luan_llima_campos.db.MyDatabase;
import com.example.movies_luan_llima_campos.listeners.OnItemClickListener;
import com.example.movies_luan_llima_campos.models.UserTicket;
import com.google.android.material.snackbar.Snackbar;

import java.lang.annotation.Native;
import java.util.ArrayList;
import java.util.List;

public class MyTicketsFragment extends Fragment implements OnItemClickListener {

    private static final String TAG = "MyTicketsFragment";
    // binding the fragment to the layout
    private FragmentMyTicketsBinding binding;

    // adapter
    private TicketAdapter adapter;

    // data source
    private ArrayList<UserTicket> userTickets = new ArrayList<>();

    // room database
    private MyDatabase db;

    public MyTicketsFragment() {
        super(R.layout.fragment_my_tickets);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // configure bindings
        adapter = new TicketAdapter(getContext(), userTickets, this);
        binding.rvMyTickets.setAdapter(adapter);
        binding.rvMyTickets.setLayoutManager(new LinearLayoutManager(getContext()));

        // get all the tickets from the database
        db = MyDatabase.getDatabase(getContext());
        List<UserTicket> userTicketsList = db.userTicketsDAO().getAllUserTickets();

        if (userTicketsList.size() == 0) {
            Log.d(TAG, "onViewCreated: list is empty");
            binding.tvNoTickets.setVisibility(View.VISIBLE);
            binding.rvMyTickets.setVisibility(View.GONE);
        } else {
            Log.d(TAG, "onViewCreated: list is not empty");
            binding.tvNoTickets.setVisibility(View.GONE);
            binding.rvMyTickets.setVisibility(View.VISIBLE);
            userTickets.clear();
            userTickets.addAll(userTicketsList);
            binding.rvMyTickets.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            adapter.notifyDataSetChanged();
        }
    }

    // lifecycle functions - required for configuring view bindings
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyTicketsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onItemClickListener(UserTicket item) {
        Log.d(TAG, "onItemClickListener: " + item.getMovieTitle());
        db.userTicketsDAO().deleteUserTicket(item);
        // update the list of tickets
        userTickets.remove(item);

        // create a snackbar to show the user that the ticket was deleted
        Snackbar.make(binding.getRoot(), "Ticket deleted", Snackbar.LENGTH_LONG).show();
        checkListSize();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // helper function to check if the list is empty and bind tvNoTickets accordingly
    private void checkListSize() {
        if (userTickets.size() == 0) {
            binding.tvNoTickets.setVisibility(View.VISIBLE);
            binding.rvMyTickets.setVisibility(View.GONE);
        } else {
            binding.tvNoTickets.setVisibility(View.GONE);
            binding.rvMyTickets.setVisibility(View.VISIBLE);
        }
    }
}