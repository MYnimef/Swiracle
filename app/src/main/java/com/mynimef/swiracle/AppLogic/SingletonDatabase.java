package com.mynimef.swiracle.AppLogic;

import android.app.Activity;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.ArrayList;

@Database(entities = {Post.class}, version = 1)
public abstract class SingletonDatabase extends RoomDatabase {
    private static SingletonDatabase instance;
    private PostList recommendationList;
    private PostList userList;
    private ArrayList<String> gallery;

    public static synchronized SingletonDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SingletonDatabase.class, "post_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    protected SingletonDatabase() {
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
        Thread thread = new Thread(new AnotherRunnable());
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