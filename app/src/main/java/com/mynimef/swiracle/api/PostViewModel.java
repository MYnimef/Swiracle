package com.mynimef.swiracle.api;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mynimef.swiracle.api.database.Post;
import com.mynimef.swiracle.api.singleton.Singleton;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PostViewModel extends AndroidViewModel {
    private final PostRepository repository;
    private final LiveData<List<Post>> recommendationList;

    @Inject
    public PostViewModel(@NonNull Application application) {
        super(application);
        this.repository = Singleton.getInstance().getRepository();
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

    public LiveData<List<Post>> getRecommendationList() {
        return recommendationList;
    }
}