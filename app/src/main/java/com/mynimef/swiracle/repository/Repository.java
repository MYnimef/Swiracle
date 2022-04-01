package com.mynimef.swiracle.repository;

import android.app.Application;
import android.os.Handler;

import androidx.lifecycle.LiveData;

import com.mynimef.swiracle.models.Post;
import com.mynimef.swiracle.models.PostInfo;
import com.mynimef.swiracle.models.SignInRequest;
import com.mynimef.swiracle.models.SignUpRequest;
import com.mynimef.swiracle.models.User;
import com.mynimef.swiracle.models.PostServer;
import com.mynimef.swiracle.models.UserInit;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class Repository extends RepositoryApp {
    private final LocalDatabase database;
    private final NetworkService network;

    @Inject
    public Repository(Application application) {
        database = LocalDatabase.init(application);
        network = new NetworkService(this);

        new Thread(() -> setAccount(database.initUser()))
                .start();
    }

    public boolean isActualUser(String username) {
        if (getAccount() != null) {
            return getAccount().getUsername().equals(username);
        } else {
            return false;
        }
    }

    public boolean isAdmin() {
        if (getAccount() != null) {
            return (getAccount().getPermission() == 2);
        } else {
            return false;
        }
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

    public void signUp(SignUpRequest request, Handler signUpHandler) {
        network.signUp(request, signUpHandler);
    }

    public void login(SignInRequest request, Handler handler) {
        network.signIn(request, handler);
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