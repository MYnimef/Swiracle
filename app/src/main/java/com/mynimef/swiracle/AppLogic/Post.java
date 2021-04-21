package com.mynimef.swiracle.AppLogic;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;

@Entity(tableName = "post_table")
public class Post {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
    
    private String title;
    private String description;
    private int likes, comments;
    @TypeConverters(ImagesConverter.class)
    private Images images;

    public Post(String title, String description, Images images, int likes, int comments) {
    //public Post(String title, String description, ArrayList<ClothesElement> clothes, ArrayList<String> images) {
        this.title = title;
        this.description = description;
        this.images = images;
        this.likes = 0;
        this.comments = 0;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getLikes() {
        return likes;
    }

    public void increaseLikes() {
        likes++;
    }

    public int getComments() {
        return comments;
    }

    public void increaseComments() {
        comments++;
    }

    public Images getImages() {
        return images;
    }
}