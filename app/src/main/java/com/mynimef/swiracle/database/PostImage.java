package com.mynimef.swiracle.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "images_table")
public class PostImage {
    @PrimaryKey @NotNull
    private String url;
    public void setUrl(@NotNull String url) { this.url = url; }
    @NotNull
    public String getUrl() { return url; }

    @NonNull
    private String postId;
    @NotNull
    public String getPostId() { return postId; }
    public void setPostId(@NotNull String postId) { this.postId = postId; }

    public PostImage(@NotNull String url, @NotNull String postId) {
        this.url = url;
        this.postId = postId;
    }
}