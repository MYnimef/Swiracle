package com.mynimef.swiracle.fragments.post;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mynimef.swiracle.database.Post;
import com.mynimef.swiracle.logic.Repository;
import com.mynimef.swiracle.models.PostDetails;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PostViewModel extends AndroidViewModel {
    private final Repository repository;
    private final LiveData<List<Post>> recommendationList;
    private final MutableLiveData<PostDetails> postDetails;

    @Inject
    public PostViewModel(@NonNull Application application) {
        super(application);
        this.repository = Repository.getInstance();
        this.recommendationList = repository.getRecommendationList();
        this.postDetails = new MutableLiveData<>();
    }

    public LiveData<List<Post>> getRecommendationList() { return recommendationList; }
    public MutableLiveData<PostDetails> getDetails() { return postDetails; }

    public void loadDetails(String id) {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.arg1 == 0) {
                    postDetails.postValue((PostDetails) msg.obj);
                } else if (msg.arg1 == 1) {
                    Toast.makeText(getApplication(), "Error getting details",
                            Toast.LENGTH_SHORT).show();
                } else if (msg.arg1 == -1) {
                    Toast.makeText(getApplication(), "No connection",
                            Toast.LENGTH_SHORT).show();
                }
                removeCallbacksAndMessages(null);
            }
        };
        repository.getPostDetails(id, handler);
    }
}