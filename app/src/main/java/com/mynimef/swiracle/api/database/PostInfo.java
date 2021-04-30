package com.mynimef.swiracle.api.database;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.mynimef.swiracle.api.Images;
import com.mynimef.swiracle.api.ImagesConverter;
import com.mynimef.swiracle.api.Price;

@Entity(tableName = "post_table")
public class PostInfo {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    public int getUid() { return uid; }
    public void setUid(int uid) { this.uid = uid; }

    private String title;
    private String description;
    @TypeConverters(ImagesConverter.class)
    private final Images images;
    private int likesAmount, commentsAmount;
    @Embedded
    private Price price;

    public PostInfo(String title, String description, Images images,
                    int likesAmount, int commentsAmount,
                    Price price) {
        this.title = title;
        this.description = description;
        this.images = images;
        this.likesAmount = 0;
        this.commentsAmount = 0;
        this.price = price;
    }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setLikesAmount(int likesAmount) { this.likesAmount = likesAmount; }
    public void setCommentsAmount(int commentsAmount) { this.commentsAmount = commentsAmount; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getLikesAmount() { return likesAmount; }
    public void increaseLikesAmount() { likesAmount++; }
    public int getCommentsAmount() { return commentsAmount; }
    public void increaseCommentsAmount() { commentsAmount++; }
    public Images getImages() { return images; }
    public Price getPrice() { return price; }
    public void setPrice(Price price) { this.price = price; }
}