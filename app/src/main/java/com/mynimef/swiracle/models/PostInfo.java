package com.mynimef.swiracle.models;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "post_table")
public class PostInfo {
    @PrimaryKey @NonNull
    private String id;
    @NotNull
    public String getId() { return id; }
    public void setId(@NotNull String id) { this.id = id; }

    private String username;
    private String title;
    private int likesAmount;
    private int commentsAmount;

    @Embedded
    private Price price;

    private boolean isLiked;

    public PostInfo(@NotNull String id, String username, String title,
                    int likesAmount, int commentsAmount,
                    Price price, boolean isLiked) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.likesAmount = likesAmount;
        this.commentsAmount = commentsAmount;
        this.price = price;
        this.isLiked = isLiked;
    }

    public void setTitle(String title) { this.title = title; }
    public void setUsername(String username) { this.username = username; }
    public void setLikesAmount(int likesAmount) { this.likesAmount = likesAmount; }
    public void setCommentsAmount(int commentsAmount) { this.commentsAmount = commentsAmount; }
    public void setPrice(Price price) { this.price = price; }
    public void setIsLiked(boolean liked) { isLiked = liked; }

    public String getTitle() { return title; }
    public String getUsername() { return username; }
    public int getLikesAmount() { return likesAmount; }
    public int getCommentsAmount() { return commentsAmount; }
    public Price getPrice() { return price; }
    public boolean getIsLiked() { return isLiked; }
}