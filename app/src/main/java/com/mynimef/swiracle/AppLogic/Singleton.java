package com.mynimef.swiracle.AppLogic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mynimef.swiracle.fragments.create.GalleryViewer;
import com.mynimef.swiracle.fragments.create.SerializableGallery;

import java.util.ArrayList;

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

    public void setPostInfo(String title, String description, ArrayList<Uri> uris, Context context) {
        Thread thread = new Thread(new LoadBitmapRunnable(title, description, uris, context));
        thread.start();
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

    class LoadBitmapRunnable implements Runnable {
        private String title;
        private String description;
        private ArrayList<Uri> uris;
        private Context context;
        private ArrayList<Bitmap> bitmaps;

        public LoadBitmapRunnable(String title, String description, ArrayList<Uri> uris, Context context) {
            this.title = title;
            this.description = description;
            this.uris = uris;
            this.context = context;
            this.bitmaps = new ArrayList<Bitmap>();
        }

        @Override
        public void run() {
            ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
            for (int i = 0; i < uris.size(); i++) {
                Glide.with(context)
                        .asBitmap()
                        .load(uris.get(i))
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource,
                                                        @Nullable Transition<? super Bitmap> transition) {
                                bitmaps.add(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });
            }
            while(bitmaps.size() != uris.size()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            addToList(new Post(title, description, bitmaps));
        }
    }
}