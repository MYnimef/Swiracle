package com.mynimef.swiracle.AppLogic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;

public class PostViewModel extends AndroidViewModel {
    private PostRepository repository;
    private LiveData<ArrayList<Post>> recommendationList;

    public PostViewModel(@NonNull Application application) {
        super(application);
        this.repository = new PostRepository(application);
        this.recommendationList = repository.getRecommendationList();
    }

    public void insert(Post post) {
        repository.insert(post);
    }

    public void update(Post post) {
        repository.update(post);
    }

    public void delete(Post post) {
        repository.delete(post);
    }

    public void deleteAllPosts() {
        repository.deleteAllPosts();
    }

    public LiveData<ArrayList<Post>> getRecommendationList() {
        return recommendationList;
    }
}