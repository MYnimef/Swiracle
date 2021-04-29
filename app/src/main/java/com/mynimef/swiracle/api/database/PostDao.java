package com.mynimef.swiracle.api.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PostDao {
    @Insert
    void insertPost(PostInfo postInfo);

    @Update
    void updatePost(PostInfo postInfo);

    @Delete
    void deletePost(PostInfo postInfo);

    @Insert
    void insertClothesElement(ClothesElement clothesElement);

    @Update
    void updateClothesElement(ClothesElement clothesElement);

    @Delete
    void deleteClothesElement(ClothesElement clothesElement);

    @Query("DELETE FROM post_table")
    void deleteAllPosts();

    @Transaction
    @Query("SELECT * FROM post_table")
    LiveData<List<Post>> getAllPosts();
}