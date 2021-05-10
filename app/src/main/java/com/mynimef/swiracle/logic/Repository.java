package com.mynimef.swiracle.logic;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;

import com.mynimef.swiracle.database.ImagesDao;
import com.mynimef.swiracle.database.Post;
import com.mynimef.swiracle.database.PostDao;
import com.mynimef.swiracle.database.UserDao;
import com.mynimef.swiracle.models.PostImage;
import com.mynimef.swiracle.database.SingletonDatabase;
import com.mynimef.swiracle.models.UserDetails;
import com.mynimef.swiracle.network.NetworkService;
import com.mynimef.swiracle.models.PostServer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class Repository {
    private static Repository instance;
    private Application application;

    private UserDao userDao;
    private PostDao postDao;
    private ImagesDao imagesDao;

    private NetworkService networkService;

    private LiveData<UserDetails> userDetails;
    private LiveData<List<Post>> recommendationList;
    private ArrayList<Uri> gallery;

    public static synchronized Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public void init(Application application) {
        this.application = application;

        SingletonDatabase database = SingletonDatabase.getInstance(application);
        userDao = database.userDao();
        postDao = database.postDao();
        imagesDao = database.imagesDao();

        userDetails = userDao.getAllUsers();
        recommendationList = postDao.getAllPosts();

        networkService = NetworkService.getInstance();
        networkService.getPosts();

        initGallery();
    }

    public boolean isLoggedIn() {
        return userDetails.getValue() != null;
    }

    public void login(String username, String password, Handler handler) {
        networkService.signIn(username, password, handler);
    }

    public void uploadPost(PostServer postServer, List<String> pathList) {
        networkService.putPost(postServer, pathList);
    }

    public void getPosts() { networkService.getPosts(); }

    public void getPostDetails(String id, Handler handler) {
        networkService.getPostDetails(id, handler);
    }

    public void insertAll(List<Post> postList) {
        new Thread(new InsertAllPostsRunnable(postDao, imagesDao, postList)).start();
    }
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

    private static class InsertAllPostsRunnable implements Runnable {
        private final PostDao postDao;
        private final ImagesDao imagesDao;
        private final List<Post> postList;

        private InsertAllPostsRunnable(PostDao postDao, ImagesDao imagesDao, List<Post> postList) {
            this.postDao = postDao;
            this.imagesDao = imagesDao;
            this.postList = postList;
        }

        @Override
        public void run() {
            postDao.deleteAllPosts();
            imagesDao.deleteAllImages();
            for (Post post : postList) {
                postDao.insertPostInfo(post.getPostInfo());
                for (PostImage image : post.getImages()) {
                    imagesDao.insertPostImage(image);
                }
            }
        }
    }
}