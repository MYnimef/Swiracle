package com.mynimef.swiracle.repository;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import androidx.lifecycle.LiveData;

import com.mynimef.swiracle.models.DateModel;
import com.mynimef.swiracle.models.Post;
import com.mynimef.swiracle.models.PostInfo;
import com.mynimef.swiracle.models.User;
import com.mynimef.swiracle.models.PostServer;

import java.util.List;

public final class Repository {
    private static Repository instance;

    public static synchronized Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    private SharedPreferences sharedPref;

    private LocalDatabase database;
    private NetworkService network;

    private int signedIn;
    private String actualUsername;
    private String token;

    public void init(Application application) {
        database = LocalDatabase.init(application);
        network = new NetworkService(this);
    }

    public void setSharedPref(Activity activity) {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        signedIn = sharedPref.getInt("SignedIn", 0);
        //0 - first launch
        //1 - signed in
        //-1 - decided not to sign in

        actualUsername = sharedPref.getString("username", "");
        if (!actualUsername.isEmpty()) {
            new Thread(() -> token = database.getToken(actualUsername))
                    .start();
        }
    }

    public LiveData<User> getActualUser() {
        return database.getUser(actualUsername);
    }

    public int getSignedIn() {
        return signedIn;
    }

    public void setSignedIn(int signedIn) {
        this.signedIn = signedIn;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("SignedIn", signedIn);
        editor.apply();
    }

    public void signUp(
            String username,
            String password,
            String email,
            String firstName,
            String lastName,
            int gender,
            DateModel birthday,
            Handler signUpHandler
    ) {
        network.signUp(
                username,
                password,
                email,
                firstName,
                lastName,
                gender,
                birthday,
                signUpHandler
        );
    }

    public void login(String username, String password, Handler handler) {
        network.signIn(username, password, handler);
    }

    public void insertUser(User user) {
        database.insertUser(user);

        actualUsername = user.getUsername();
        token = user.getToken();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", actualUsername);
        editor.apply();
    }

    public void getClothesParsing(String url, Handler handler) {
        network.getClothesParsing(url, handler, token);
    }

    public void uploadPost(PostServer postServer, List<String> pathList) {
        network.putPost(postServer, pathList, token);
    }

    public void likePost(String id) {
        network.likePost(id, token);
    }

    public void getPosts(Handler handler) {
        if (signedIn == 1) {
            while (token == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            network.getPostsAuth(token, handler);
        } else {
            network.getPosts(handler);
        }
    }

    public void getPostDetails(String id, Handler handler) {
        network.getPostDetails(id, handler);
    }

    public void getProfileView(String id, Handler handler) {
        if (signedIn == 1) {
            network.getProfileViewAuth(id, handler, token);
        } else {
            network.getProfileView(id, handler);
        }
    }

    public void subscribe(String id) {
        network.followUser(token, id);
    }

    public LiveData<Post> getPost(String username) {
        return database.getPost(username);
    }

    public void updatePostInfo(PostInfo postInfo) {
        database.updatePostInfo(postInfo);
    }

    public void insertAllPosts(List<Post> postList) {
        database.insertAllPosts(postList);
    }

    public LiveData<List<Post>> getRecommendationList() {
        return database.getRecommendationList();
    }
}