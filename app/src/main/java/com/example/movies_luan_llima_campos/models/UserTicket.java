package com.example.movies_luan_llima_campos.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="tickets_table")
public class UserTicket {
    // Id from the TMDB API item
    @PrimaryKey
    private int movieId;
    private String movieTitle;
    private int ticketsQty;

    public UserTicket(int movieId, String movieTitle, int ticketsQty) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.ticketsQty = ticketsQty;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getTicketsQty() {
        return ticketsQty;
    }

    public void setTicketsQty(int ticketsQty) {
        this.ticketsQty = ticketsQty;
    }

    @Override
    public String toString() {
        return "UserTicket{" +
                "movieId=" + movieId +
                ", movieTitle='" + movieTitle + '\'' +
                ", ticketsQty=" + ticketsQty +
                '}';
    }
}
