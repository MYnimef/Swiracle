package com.mynimef.swiracle.api.singleton;

import android.app.Activity;
import android.app.Application;

import com.mynimef.swiracle.api.PostRepository;

import java.util.ArrayList;

public class Singleton {
    private static Singleton instance;
    private ArrayList<String> gallery;
    private PostRepository repository;

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public void initRepository(Application application) {
        this.repository = new PostRepository(application);
    }

    public PostRepository getRepository() {
        return repository;
    }

    public void initGallery(Activity activity) {
        new GalleryViewer(activity);
    }

    public void setGallery(ArrayList<String> gallery) {
        this.gallery = gallery;
    }

    public ArrayList<String> getGallery() {
        return this.gallery;
    }
}