package com.mynimef.swiracle.api;

import android.app.Activity;
import android.app.Application;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.lifecycle.LiveData;

import com.mynimef.swiracle.api.database.Post;
import com.mynimef.swiracle.api.database.PostDao;
import com.mynimef.swiracle.api.database.PostImage;
import com.mynimef.swiracle.api.database.PostInfo;
import com.mynimef.swiracle.api.database.SingletonDatabase;
import com.mynimef.swiracle.network.NetworkService;
import com.mynimef.swiracle.network.PostImageServer;
import com.mynimef.swiracle.network.PostServer;
import com.mynimef.swiracle.network.PostViewServer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class Repository {
    private static Repository instance;
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

    public void initNetwork() {
        this.networkService = NetworkService.getInstance();
    }

    public void uploadPost(PostServer postServer, List<String> pathList) {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String result = msg.getData().getString("result");
                if (result.equals("positive")){
                    setNewData();
                }
                removeCallbacksAndMessages(null);
            }
        };
        networkService.putPost(postServer, pathList, handler);
    }

    public void setNewData() {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                insertAll((List<PostViewServer>) msg.obj);
                removeCallbacksAndMessages(null);
            }
        };
        networkService.getPosts(handler);
    }

    public void initDatabase(Application application) {
        SingletonDatabase database = SingletonDatabase.getInstance(application);
        postDao = database.postDao();
        recommendationList = postDao.getAllPosts();
    }

    public void insert(Post post) { new Thread(new InsertPostRunnable(postDao, post)).start(); }
    public void insertAll(List<PostViewServer> postList) { new Thread(new InsertAllPostsRunnable(postDao, postList)).start(); }
    public void update(Post post) { new Thread(new UpdatePostRunnable(postDao, post)).start(); }
    public void delete(Post post) { new Thread(new DeletePostRunnable(postDao, post)).start(); }
    public void deleteAllPosts() { new Thread(new DeleteAllPostsRunnable(postDao)).start(); }
    public LiveData<List<Post>> getRecommendationList() { return recommendationList; }

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