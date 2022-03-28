package com.mynimef.swiracle.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "images_table")
public final class PostImage {
    @PrimaryKey @NotNull
    private String imageUrl;
    public void setImageUrl(@NotNull String imageUrl) { this.imageUrl = imageUrl; }
    @NotNull
    public String getImageUrl() { return imageUrl; }

    @NonNull
    private String postId;
    @NotNull
    public String getPostId() { return postId; }
    public void setPostId(@NotNull String postId) { this.postId = postId; }

    public PostImage(@NotNull String imageUrl, @NotNull String postId) {
        this.imageUrl = imageUrl;
        this.postId = postId;
    }
}