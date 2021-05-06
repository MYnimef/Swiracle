package com.mynimef.swiracle.logic;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;

import com.mynimef.swiracle.database.Post;
import com.mynimef.swiracle.database.PostDao;
import com.mynimef.swiracle.database.PostImage;
import com.mynimef.swiracle.database.PostInfo;
import com.mynimef.swiracle.database.SingletonDatabase;
import com.mynimef.swiracle.network.NetworkService;
import com.mynimef.swiracle.network.models.PostImageServer;
import com.mynimef.swiracle.network.models.PostServer;
import com.mynimef.swiracle.network.models.PostViewServer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class Repository {
    private static Repository instance;
    private Application application;
    private PostDao postDao;
    private NetworkService networkService;
    private LiveData<List<Post>> recommendationList;
    private ArrayList<Uri> gallery;

    public static synchronized Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public void uploadPost(PostServer postServer, List<String> pathList) {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String result = msg.getData().getString("result");
                if (result.equals("positive")){
                    networkService.getPosts();
                }
                removeCallbacksAndMessages(null);
            }
        };
        networkService.putPost(postServer, pathList, handler);
    }

    public void init(Application application) {
        this.application = application;
        SingletonDatabase database = SingletonDatabase.getInstance(application);
        postDao = database.postDao();
        recommendationList = postDao.getAllPosts();

        networkService = NetworkService.getInstance();
        networkService.getPosts();

        initGallery();
    }

    public void insert(Post post) { new Thread(new InsertPostRunnable(postDao, post)).start(); }
    public void insertAll(List<PostViewServer> postList) {
        new Thread(new InsertAllPostsRunnable(postDao, postList)).start();
    }
    public void update(Post post) { new Thread(new UpdatePostRunnable(postDao, post)).start(); }
    public void delete(Post post) { new Thread(new DeletePostRunnable(postDao, post)).start(); }
    public void deleteAllPosts() { new Thread(new DeleteAllPostsRunnable(postDao)).start(); }
    public LiveData<List<Post>> getRecommendationList() { return recommendationList; }

    public void initGallery() {
        if (ContextCompat.checkSelfPermission(application,
            Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED) {
            new GalleryViewer(application);
        }
    }
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
            postDao.insertPostInfo(post.getPostInfo());
            for (PostImage postImage : post.getImages()) {
                postDao.insertPostImage(postImage);
            }
        }
    }

    private static class InsertAllPostsRunnable implements Runnable {
        private final PostDao postDao;
        private final List<PostViewServer> postViewList;

        private InsertAllPostsRunnable(PostDao postDao, List<PostViewServer> postViewList) {
            this.postDao = postDao;
            this.postViewList = postViewList;
        }

        @Override
        public void run() {
            postDao.deleteAllPosts();
            postDao.deleteAllImages();
            for (PostViewServer view : postViewList) {
                PostInfo postInfo = new PostInfo(view.getId(),
                        view.getTitle(),
                        view.getLikesAmount(),
                        view.getCommentsAmount(),
                        view.getPrice());
                postDao.insertPostInfo(postInfo);
                for (PostImageServer image : view.getImages()) {
                    postDao.insertPostImage(new PostImage(image.getImageUrl(), image.getPostId()));
                }
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
            postDao.updatePostInfo(post.getPostInfo());
            for (PostImage postImage : post.getImages()) {
                postDao.updatePostImage(postImage);
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
            postDao.deletePostInfo(post.getPostInfo());
            for (PostImage postImage : post.getImages()) {
                postDao.deletePostImage(postImage);
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