package com.mynimef.swiracle.AppLogic;

import android.app.Activity;

import com.mynimef.swiracle.fragments.create.GalleryViewer;
import com.mynimef.swiracle.fragments.create.SerializableGallery;

public class Singleton {
    private static Singleton instance;
    private PostList recommendationList;
    private PostList userList;
    private SerializableGallery gallery;

    private Singleton() {
        initialiseRecommendationList();
    }

    public void initialiseGallery(Activity activity) {
        GalleryViewer galleryViewer = new GalleryViewer(activity);
    }

    public void setGallery(SerializableGallery gallery) {
        this.gallery = gallery;
    }

    public SerializableGallery getGallery() {
        return gallery;
    }

    public static Singleton getInstance() {
        if (Singleton.instance == null) {
            Singleton.instance = new Singleton();
        }
        return Singleton.instance;
    }

    private void initialiseRecommendationList() {
        Thread thread = new Thread(new AnotherRunnable());
        thread.start();
    }

    public PostList getRecommendationList() {
        return recommendationList;
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