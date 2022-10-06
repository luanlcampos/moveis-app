package com.example.movies_luan_llima_campos.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movies_luan_llima_campos.models.UserTicket;

import java.util.List;

@Dao
public interface UserTicketsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUserTicket(UserTicket userTicket);

    @Update
    void updateUserTicket(UserTicket userTicket);

    @Delete
    void deleteUserTicket(UserTicket userTicket);

    @Query("SELECT * FROM tickets_table WHERE movieId = :movieId")
    UserTicket getUserTicketsById(int movieId);

    @Query("SELECT * FROM tickets_table")
    List<UserTicket> getAllUserTickets();
}
