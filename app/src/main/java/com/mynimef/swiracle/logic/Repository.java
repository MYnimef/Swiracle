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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.mynimef.swiracle.database.ImagesDao;
import com.mynimef.swiracle.database.Post;
import com.mynimef.swiracle.database.PostDao;
import com.mynimef.swiracle.database.UserDao;
import com.mynimef.swiracle.models.DateModel;
import com.mynimef.swiracle.models.PostImage;
import com.mynimef.swiracle.database.SingletonDatabase;
import com.mynimef.swiracle.models.PostInfo;
import com.mynimef.swiracle.models.User;
import com.mynimef.swiracle.network.NetworkService;
import com.mynimef.swiracle.models.PostServer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public final class Repository {
    private static Repository instance;
    private Application application;
    private SharedPreferences sharedPref;

    private UserDao userDao;
    private PostDao postDao;
    private ImagesDao imagesDao;

    private NetworkService networkService;
    private int signedIn;
    private String actualUsername;
    private String token;

    private LiveData<User> actualUser;
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

        networkService = NetworkService.getInstance();
        initGallery();
    }

    public void setPreferences(Activity activity) {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        signedIn = sharedPref.getInt("SignedIn", 0);
        //0 - first launch
        //1 - signed in
        //-1 - decided not to sign in

        actualUsername = sharedPref.getString("username", "");
        if (!actualUsername.equals("")) {
            setUser();
        }
    }

    public void changeUser(String username) {
        actualUsername = username;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", username);
        editor.apply();
        setUser();
    }

    private void setUser() {
        actualUser = userDao.getUser(actualUsername);
        new Thread(() -> setToken(userDao.getToken(actualUsername))).start();
    }
    public LiveData<User> getActualUser() { return actualUser; }

    public int getSignedIn() { return signedIn; }
    public void setSignedIn(int signedIn) {
        this.signedIn = signedIn;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("SignedIn", signedIn);
        editor.apply();
    }

    public void setToken(String token) { this.token = token; }

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

    public void insertUser(User user) {
        new Thread(() -> {
            userDao.insertUser(user);
            changeUser(user.getUsername());
        }).start();
    }

    public void getClothesParsing(String url, Handler handler) {
        networkService.getClothesParsing(url, handler, token);
    }

    public void uploadPost(PostServer postServer, List<String> pathList) {
        networkService.putPost(postServer, pathList, token);
    }

    public void likePost(String id) {
        networkService.likePost(id, token);
    }

    public void getPosts(Handler handler) {
        if (signedIn == 1) {
            networkService.getPostsAuth(token, handler);
        } else {
            networkService.getPosts(handler);
        }
    }

    public void getPostDetails(String id, Handler handler) {
        networkService.getPostDetails(id, handler);
    }

    public LiveData<Post> getPost(String username) {
        return postDao.getPost(username);
    }

    public void getProfileView(String id, Handler handler) {
        if (signedIn == 1) {
            networkService.getProfileViewAuth(id, handler, token);
        } else {
            networkService.getProfileView(id, handler);
        }
    }

    public void subscribe(String id) {
        networkService.followUser(token, id);
    }

    public void updatePostInfo(PostInfo postInfo) {
        new Thread(() -> postDao.updatePostInfo(postInfo)).start();
    }

    public void insertAllPosts(List<Post> postList) {
        new Thread(() -> {
            postDao.deleteAllPosts();
            imagesDao.deleteAllImages();
            for (Post post : postList) {
                postDao.insertPostInfo(post.getPostInfo());
                for (PostImage image : post.getImages()) {
                    imagesDao.insertPostImage(image);
                }
            }
        }).start();
    }

    public LiveData<User> getUserList() { return actualUser; }
    public LiveData<List<Post>> getRecommendationList() { return postDao.getAllPosts(); }

    public void initGallery() {
        if (ContextCompat.checkSelfPermission(application,
            Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED) {
            new GalleryViewer(application);
        }
    }
    public void setGallery(ArrayList<Uri> gallery) { this.gallery = gallery; }
    public ArrayList<Uri> getGallery() { return this.gallery; }
}