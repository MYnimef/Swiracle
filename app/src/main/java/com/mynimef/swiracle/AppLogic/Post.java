package com.mynimef.swiracle.AppLogic;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Post {
    private final String title;
    private final String description;
    private int likes, comments;
    //private final ArrayList<Bitmap> images;
    private final ArrayList<String> images;

    //public Post(String title, String description, ArrayList<Bitmap> images) {
    public Post(String title, String description, ArrayList<ClothesElement> clothes, ArrayList<String> images) {
        this.title = title;
        this.description = description;
        this.images = images;
        this.likes = 0;
        this.comments = 0;
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

    /*
    public ArrayList<Bitmap> getImages() {
        return images;
    }
    */

    public ArrayList<String> getImages() {
        return images;
    }
}