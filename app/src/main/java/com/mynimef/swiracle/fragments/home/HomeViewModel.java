package com.mynimef.swiracle.fragments.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mynimef.swiracle.database.Post;
import com.mynimef.swiracle.logic.Repository;
import com.mynimef.swiracle.models.PostInfo;

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

    public void update() { repository.getPosts(); }
    public LiveData<List<Post>> getRecommendationList() { return recommendationList; }

    public int getSignedIn() { return repository.getSignedIn(); }
    public void likePost(String id) { repository.likePost(id); }
    public void updatePostInfo(PostInfo postInfo) { repository.updatePostInfo(postInfo); }
}