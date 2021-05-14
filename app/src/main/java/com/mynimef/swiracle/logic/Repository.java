package com.mynimef.swiracle.logic;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;

import com.mynimef.swiracle.database.ImagesDao;
import com.mynimef.swiracle.database.Post;
import com.mynimef.swiracle.database.PostDao;
import com.mynimef.swiracle.database.UserDao;
import com.mynimef.swiracle.models.DateModel;
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
    private SharedPreferences sharedPref;

    private UserDao userDao;
    private PostDao postDao;
    private ImagesDao imagesDao;

    private NetworkService networkService;
    private int signedIn;
    private String token;

    private LiveData<List<UserDetails>> userDetails;
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
        initGallery();
    }

    public void setPreferences(Activity activity) {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        signedIn = sharedPref.getInt("SignedIn", 0);
        //0 - first launch
        //1 - signed in
        //-1 - decided not to sign in
        token = sharedPref.getString("token", "");
    }

    public int getSignedIn() { return signedIn; }
    public void setSignedIn(int signedIn) {
        this.signedIn = signedIn;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("SignedIn", signedIn);
        editor.apply();
    }

    public String getToken() { return token; }
    public void setToken(String token) {
        this.token = token;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public void signUp(String username, String password, String email,
                       String firstName, String lastName,
                       int gender, DateModel birthday,
                       Handler signUpHandler) {
        networkService.signUp(username, password, email,
                firstName, lastName, gender, birthday,
                signUpHandler);
    }

    public void login(String username, String password, Handler handler) {
        networkService.signIn(username, password, handler);
    }

    public void insertUser(UserDetails userDetails) {
        new Thread(new InsertUserRunnable(userDao, userDetails)).start();
    }

    public void uploadPost(PostServer postServer, List<String> pathList) {
        networkService.putPost(postServer, pathList);
    }

    public void getPosts() { networkService.getPosts(); }

    public void getPostDetails(String id, Handler handler) {
        networkService.getPostDetails(id, handler);
    }

    public void insertAllPosts(List<Post> postList) {
        new Thread(new InsertAllPostsRunnable(postDao, imagesDao, postList)).start();
    }

    public LiveData<List<UserDetails>> getUserList() { return userDetails; }
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

    private static class InsertUserRunnable implements Runnable {
        private final UserDao userDao;
        private final UserDetails userDetails;

        private InsertUserRunnable(UserDao userDao, UserDetails userDetails) {
            this.userDao = userDao;
            this.userDetails = userDetails;
        }

        @Override
        public void run() {
            userDao.insertUser(userDetails);
        }
    }

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