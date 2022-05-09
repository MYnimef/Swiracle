package com.mynimef.swiracle.fragments.navigation.home;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mynimef.swiracle.models.Post;
import com.mynimef.swiracle.repository.Repository;
import com.mynimef.swiracle.models.PostInfo;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class HomeViewModel extends ViewModel {
    private final Repository repository;
    private final LiveData<List<Post>> list;

    private final MutableLiveData<Boolean> animRefresh;
    private final MutableLiveData<Boolean> animLayout;
    private final MutableLiveData<String> errorMessage;

    @Inject
    public HomeViewModel(Repository repository) {
        this.repository = repository;
        list = repository.getRecommendationList();

        animRefresh = new MutableLiveData<>(false);
        animLayout = new MutableLiveData<>(true);
        errorMessage = new MutableLiveData<>();

        update();
    }

    public void update() {
        repository.getPosts(new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int result = msg.arg1;

                if (result == 0) {
                    animLayout.setValue(true);
                } else if (result == 1) {
                    errorMessage.setValue("Error loading data");
                } else if (result == -1) {
                    errorMessage.setValue("No connection");
                }

                animRefresh.setValue(true);
                animLayout.setValue(false);
                removeCallbacksAndMessages(null);
            }
        });
    }

    public LiveData<List<Post>> getRecommendationList() {
        return list;
    }

    public LiveData<Boolean> getAnimRefresh() {
        return animRefresh;
    }

    public LiveData<Boolean> getAnimLayout() {
        return animLayout;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public int getSignedIn() { return repository.getSignedIn(); }
    public boolean isActualUser(String username) { return repository.isActualUser(username); }
    public boolean isAdmin() { return repository.isAdmin(); }
    public void likePost(String id) { repository.likePost(id); }

    public void updatePostInfo(PostInfo postInfo) {
        repository.updatePostInfo(postInfo);
    }
}