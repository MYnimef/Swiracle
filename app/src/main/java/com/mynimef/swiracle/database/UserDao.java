package com.mynimef.swiracle.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mynimef.swiracle.models.User;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("DELETE FROM user_table")
    void deleteAllUsers();

    @Query("SELECT token FROM user_table WHERE username = :username")
    String getToken(String username);

    @Query("SELECT * FROM user_table WHERE username = :username")
    LiveData<User> getUser(String username);
}