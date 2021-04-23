package com.mynimef.swiracle.api;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.mynimef.swiracle.api.database.Post;
import com.mynimef.swiracle.api.database.PostDao;
import com.mynimef.swiracle.api.database.SingletonDatabase;

import java.util.List;

public class PostRepository {
    private final PostDao postDao;
    private final LiveData<List<Post>> recommendationList;

    public PostRepository(Application application) {
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

    private static class InsertPostRunnable implements Runnable {
        private final PostDao postDao;
        private final Post post;

        private InsertPostRunnable(PostDao postDao, Post post) {
            this.postDao = postDao;
            this.post = post;
        }

        @Override
        public void run() {
            postDao.insert(post);
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
            postDao.update(post);
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
            postDao.delete(post);
        }
    }

    private static class DeleteAllPostsRunnable implements Runnable {
        private final PostDao postDao;

        private DeleteAllPostsRunnable(PostDao postDao) {
            this.postDao = postDao;
        }

        @Override
        public void run() {
            postDao.deleteAllPosts();
        }
    }
}