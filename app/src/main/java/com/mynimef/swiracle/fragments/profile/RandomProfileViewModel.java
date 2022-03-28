package com.mynimef.swiracle.fragments.profile;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mynimef.swiracle.logic.Repository;
import com.mynimef.swiracle.models.ProfileView;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class RandomProfileViewModel extends AndroidViewModel {
    private final Repository repository;
    private final MutableLiveData<ProfileView> profile;

    @Inject
    public RandomProfileViewModel(@NonNull Application application) {
        super(application);
        this.repository = Repository.getInstance();
        this.profile = new MutableLiveData<>();
    }

    public MutableLiveData<ProfileView> getProfile() { return profile; }

    public void loadProfile(String username) {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.arg1 == 0) {
                    profile.postValue((ProfileView) msg.obj);
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
        repository.getProfileView(username, handler);
    }

    public void subscribe(String username) {
        repository.subscribe(username);
    }
}