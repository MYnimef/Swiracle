package com.mynimef.swiracle.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mynimef.swiracle.models.PostImage;

@Dao
public interface ImagesDao {
    @Insert
    void insertPostImage(PostImage postImage);

    @Query("DELETE FROM images_table")
    void deleteAllImages();
}