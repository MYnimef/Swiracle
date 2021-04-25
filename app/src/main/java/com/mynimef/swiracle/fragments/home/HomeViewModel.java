package com.mynimef.swiracle.fragments.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mynimef.swiracle.api.Repository;
import com.mynimef.swiracle.api.database.Post;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends AndroidViewModel {
    private final Repository repository;
    private final LiveData<List<Post>> recommendationList;

    @Inject
    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.repository = Repository.getInstance();
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