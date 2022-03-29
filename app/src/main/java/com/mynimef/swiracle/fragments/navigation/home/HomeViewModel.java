package com.mynimef.swiracle.fragments.navigation.home;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mynimef.swiracle.models.Post;
import com.mynimef.swiracle.repository.Repository;
import com.mynimef.swiracle.models.PostInfo;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class HomeViewModel extends AndroidViewModel {
    private final Repository repository;
    private final LiveData<List<Post>> recommendationList;
    private final MutableLiveData<Boolean> updated;

    @Inject
    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.repository = Repository.getInstance();
        this.recommendationList = repository.getRecommendationList();
        this.updated = new MutableLiveData<>(false);
    }

    public void update() {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int result = msg.arg1;

                if (result == 0) {
                } else if (result == 1) {
                    Toast.makeText(
                            getApplication(),
                            "Error loading data",
                            Toast.LENGTH_SHORT
                    )
                            .show();
                } else if (result == -1) {
                    Toast.makeText(
                            getApplication(),
                            "No connection",
                            Toast.LENGTH_SHORT
                    )
                            .show();
                }

                updated.setValue(true);
                removeCallbacksAndMessages(null);
            }
        };
        repository.getPosts(handler);
    }

    public LiveData<List<Post>> getRecommendationList() { return recommendationList; }
    public MutableLiveData<Boolean> getUpdated() { return updated; }

    public int getSignedIn() { return repository.getSignedIn(); }
    public void likePost(String id) { repository.likePost(id); }
    public void updatePostInfo(PostInfo postInfo) { repository.updatePostInfo(postInfo); }
}