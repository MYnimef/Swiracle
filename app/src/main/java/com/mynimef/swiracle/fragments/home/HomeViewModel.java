package com.mynimef.swiracle.fragments.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mynimef.swiracle.database.Post;
import com.mynimef.swiracle.logic.Repository;

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

    public void delete(Post post) { repository.delete(post); }
    public LiveData<List<Post>> getRecommendationList() { return recommendationList; }
}