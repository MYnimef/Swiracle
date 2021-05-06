package com.mynimef.swiracle.database;

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
    void insertPostInfo(PostInfo postInfo);

    @Update
    void updatePostInfo(PostInfo postInfo);

    @Delete
    void deletePostInfo(PostInfo postInfo);

    @Insert
    void insertPostImage(PostImage postImage);

    @Update
    void updatePostImage(PostImage postImage);

    @Delete
    void deletePostImage(PostImage postImage);

    @Query("DELETE FROM post_table")
    void deleteAllPosts();

    @Query("DELETE FROM images_table")
    void deleteAllImages();

    @Transaction
    @Query("SELECT * FROM post_table")
    LiveData<List<Post>> getAllPosts();
}