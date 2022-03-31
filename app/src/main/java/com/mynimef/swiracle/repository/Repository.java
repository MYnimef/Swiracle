package com.mynimef.swiracle.repository;

import android.app.Application;
import android.os.Handler;

import androidx.lifecycle.LiveData;

import com.mynimef.swiracle.models.DateModel;
import com.mynimef.swiracle.models.Post;
import com.mynimef.swiracle.models.PostInfo;
import com.mynimef.swiracle.models.User;
import com.mynimef.swiracle.models.PostServer;
import com.mynimef.swiracle.models.UserInit;

import java.util.List;

public final class Repository extends RepositoryApp {
    private static Repository instance;

    public static synchronized Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    private LocalDatabase database;
    private NetworkService network;

    public void init(Application application) {
        database = LocalDatabase.init(application);
        network = new NetworkService(this);

        new Thread(() -> setAccount(database.initUser()))
                .start();
    }

    public boolean isActualUser(String username) {
        return getAccount().getUsername().equals(username);
    }

    public boolean isAdmin() {
        System.out.println(getAccount().getPermission());
        return (getAccount().getPermission() == 2);
    }

    public LiveData<User> getActualUser() {
        return database.getUser(getAccount().getUsername());
    }

    public int getSignedIn() {
        if (getAccount() != null) {
            return 1;
        } else {
            return -1;
        }
    }

    public void signUp(
            String username,
            String password,
            String email,
            String name,
            int gender,
            DateModel birthday,
            Handler signUpHandler
    ) {
        network.signUp(
                username,
                password,
                email,
                name,
                gender,
                birthday,
                signUpHandler
        );
    }

    public void login(String username, String password, Handler handler) {
        network.signIn(username, password, handler);
    }

    public void insertUser(User user) {
        setAccount(
                new UserInit(
                        user.getUsername(),
                        user.getToken(),
                        user.getPermission()
                )
        );
        database.insertUser(user);
    }

    public void getClothesParsing(String url, Handler handler) {
        network.getClothesParsing(url, handler, getAccount().getToken());
    }

    public void uploadPost(PostServer postServer, List<String> pathList) {
        network.putPost(postServer, pathList, getAccount().getToken());
    }

    public void deletePost(String postId, Handler handler) {
        network.deletePost(postId, handler, getAccount().getToken());
    }

    public void deletePostLocal(String postId) {
        database.deletePost(postId);
    }

    public void likePost(String id) {
        network.likePost(id, getAccount().getToken());
    }

    public void getPosts(Handler handler) {
        if (getAccount() != null) {
            network.getPostsAuth(getAccount().getToken(), handler);
        } else {
            network.getPosts(handler);
        }
    }

    public void getPostDetails(String id, Handler handler) {
        network.getPostDetails(id, handler);
    }

    public void getProfileView(String id, Handler handler) {
        if (getAccount() != null) {
            network.getProfileViewAuth(id, handler, getAccount().getToken());
        } else {
            network.getProfileView(id, handler);
        }
    }

    public void subscribe(String id) {
        network.followUser(getAccount().getToken(), id);
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