package com.mynimef.swiracle.fragments.navigation.home.options;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mynimef.swiracle.repository.Repository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class PostOptionsViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> isDeleted;

    @Inject
    public PostOptionsViewModel(@NonNull Application application) {
        super(application);
        this.isDeleted = new MutableLiveData<>(false);
    }

    public void deletePost(String postId) {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int result = msg.arg1;
                if (result == 0) {
                    isDeleted.postValue(true);
                } else if (result == 1) {
                    Toast.makeText(
                            getApplication(),
                            "You have no access",
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

                removeCallbacksAndMessages(null);
            }
        };

        Repository.getInstance().deletePost(postId, handler);
    }

    public MutableLiveData<Boolean> getIsDeleted() {
        return isDeleted;
    }
}
