package com.mynimef.swiracle.database;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.mynimef.swiracle.logic.Price;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "post_table")
public class PostInfo {
    @PrimaryKey @NonNull
    private String id;
    @NotNull
    public String getId() { return id; }
    public void setId(@NotNull String id) { this.id = id; }

    private String title;
    private int likesAmount, commentsAmount;
    @Embedded
    private Price price;

    public PostInfo(@NotNull String id, String title,
                    int likesAmount, int commentsAmount,
                    Price price) {
        this.id = id;
        this.title = title;
        this.likesAmount = likesAmount;
        this.commentsAmount = commentsAmount;
        this.price = price;
    }

    public void setTitle(String title) { this.title = title; }
    public void setLikesAmount(int likesAmount) { this.likesAmount = likesAmount; }
    public void setCommentsAmount(int commentsAmount) { this.commentsAmount = commentsAmount; }
    public void setPrice(Price price) { this.price = price; }

    public String getTitle() { return title; }
    public int getLikesAmount() { return likesAmount; }
    public int getCommentsAmount() { return commentsAmount; }
    public Price getPrice() { return price; }

    public void increaseLikesAmount() { likesAmount++; }
    public void increaseCommentsAmount() { commentsAmount++; }
}