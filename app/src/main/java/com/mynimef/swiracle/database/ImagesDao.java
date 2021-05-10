package com.mynimef.swiracle.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mynimef.swiracle.models.PostImage;

@Dao
public interface ImagesDao {
    @Insert
    void insertPostImage(PostImage postImage);

    @Update
    void updatePostImage(PostImage postImage);

    @Delete
    void deletePostImage(PostImage postImage);

    @Query("DELETE FROM images_table")
    void deleteAllImages();
}