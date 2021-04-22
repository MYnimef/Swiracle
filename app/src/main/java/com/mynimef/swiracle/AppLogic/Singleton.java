package com.mynimef.swiracle.AppLogic;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

public class Singleton {
    private static Singleton instance;
    private PostList recommendationList;
    private PostList userList;
    private ArrayList<String> gallery;

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    private Singleton() {
        initialiseRecommendationList();
    }

    public void initialiseGallery(Activity activity) {
        new GalleryViewer(activity);
    }

    public void setGallery(ArrayList<String> gallery) {
        this.gallery = gallery;
    }

    public ArrayList<String> getGallery() {
        return this.gallery;
    }

    private void initialiseRecommendationList() {
        Thread thread = new Thread(new Singleton.AnotherRunnable());
        thread.start();
    }

    public PostList getRecommendationList() {
        return recommendationList;
    }

    public void setPostInfo(String title, String description, ArrayList<ClothesElement> clothes, ArrayList<String> uris, Context context) {
        addToList(new Post(title, description, new Images(uris), 0, 0));
    }

    public void addToList(Post post) {
        recommendationList.addPost(post);
    }

    class AnotherRunnable implements Runnable {
        @Override
        public void run() {
            recommendationList = new PostList();
        }
    }
}
