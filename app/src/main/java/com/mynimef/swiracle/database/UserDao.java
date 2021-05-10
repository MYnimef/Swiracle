package com.mynimef.swiracle.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.mynimef.swiracle.models.UserDetails;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(UserDetails userDetails);

    @Update
    void updateUser(UserDetails userDetails);

    @Delete
    void deleteUser(UserDetails userDetails);

    @Query("DELETE FROM user_table")
    void deleteAllUsers();

    @Transaction
    @Query("SELECT * FROM user_table")
    LiveData<List<UserDetails>> getAllUsers();
}