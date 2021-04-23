package com.mynimef.swiracle.AppLogic;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PostDao {
    @Insert
    void insert(Post post);

    @Update
    void update(Post post);

    @Delete
    void delete(Post post);

    @Query("DELETE FROM post_table")
    void deleteAllPosts();

    @Query("SELECT * FROM post_table")
    LiveData<List<Post>> getAllPosts();
}