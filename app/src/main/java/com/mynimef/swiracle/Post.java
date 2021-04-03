package com.mynimef.swiracle;

public class Post {
    private String title, description;
    private int likes, comments;
    private int[] imageResource;

    public Post(String title, String description, int likes, int comments, int[] imageResource) {
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.comments = comments;
        this.imageResource = imageResource;
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

    public int[] getImageResource() {
        return imageResource;
    }
}