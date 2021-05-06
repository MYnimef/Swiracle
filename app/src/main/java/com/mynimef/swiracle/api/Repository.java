package com.mynimef.swiracle.api;

import android.app.Activity;
import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.mynimef.swiracle.api.database.ClothesElement;
import com.mynimef.swiracle.api.database.Post;
import com.mynimef.swiracle.api.database.PostDao;
import com.mynimef.swiracle.api.database.SingletonDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class Repository {
    private static Repository instance;
    private PostDao postDao;
    private LiveData<List<Post>> recommendationList;
    private ArrayList<Uri> gallery;

    public static synchronized Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public void initDatabase(Application application) {
        SingletonDatabase database = SingletonDatabase.getInstance(application);
        postDao = database.postDao();
        recommendationList = postDao.getAllPosts();
    }

    public void insert(Post post) {
        new Thread(new InsertPostRunnable(postDao, post)).start();
    }
    public void update(Post post) {
        new Thread(new UpdatePostRunnable(postDao, post)).start();
    }
    public void delete(Post post) {
        new Thread(new DeletePostRunnable(postDao, post)).start();
    }
    public void deleteAllPosts() {
        new Thread(new DeleteAllPostsRunnable(postDao)).start();
    }
    public LiveData<List<Post>> getRecommendationList() {
        return recommendationList;
    }

    public void initGallery(Activity activity) { new GalleryViewer(activity); }
    public void setGallery(ArrayList<Uri> gallery) { this.gallery = gallery; }
    public ArrayList<Uri> getGallery() { return this.gallery; }

    private static class InsertPostRunnable implements Runnable {
        private final PostDao postDao;
        private final Post post;

        private InsertPostRunnable(PostDao postDao, Post post) {
            this.postDao = postDao;
            this.post = post;
        }

        @Override
        public void run() {
            postDao.insertPost(post.postInfo);
            for (ClothesElement element : post.clothes) {
                postDao.insertClothesElement(element);
            }
        }
    }

    private static class UpdatePostRunnable implements Runnable {
        private final PostDao postDao;
        private final Post post;

        private UpdatePostRunnable(PostDao postDao, Post post) {
            this.postDao = postDao;
            this.post = post;
        }

        @Override
        public void run() {
            postDao.updatePost(post.postInfo);
            for (ClothesElement element : post.clothes) {
                postDao.updateClothesElement(element);
            }
        }
    }

    private static class DeletePostRunnable implements Runnable {
        private final PostDao postDao;
        private final Post post;

        private DeletePostRunnable(PostDao postDao, Post post) {
            this.postDao = postDao;
            this.post = post;
        }

        @Override
        public void run() {
            postDao.deletePost(post.postInfo);
            for (ClothesElement element : post.clothes) {
                postDao.deleteClothesElement(element);
            }
        }
    }

    private static class DeleteAllPostsRunnable implements Runnable {
        private final PostDao postDao;

        private DeleteAllPostsRunnable(PostDao postDao) { this.postDao = postDao; }

        @Override
        public void run() { postDao.deleteAllPosts(); }
    }
}