package com.mynimef.swiracle.repository.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.mynimef.swiracle.models.PostInfo;
import com.mynimef.swiracle.models.Post;

import java.util.List;

@Dao
public interface PostDao {
    @Insert
    void insertPostInfo(PostInfo postInfo);

    @Update
    void updatePostInfo(PostInfo post);

    @Query("DELETE FROM post_table WHERE id=:postId")
    void deletePost(String postId);

    @Query("DELETE FROM post_table")
    void deleteAllPosts();

    @Transaction
    @Query("SELECT * FROM post_table WHERE id=:id ")
    LiveData<Post> getPost(String id);

    @Transaction
    @Query("SELECT * FROM post_table")
    LiveData<List<Post>> getAllPosts();
}