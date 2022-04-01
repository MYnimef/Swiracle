package com.mynimef.swiracle.fragments.navigation.create;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mynimef.swiracle.repository.Repository;
import com.mynimef.swiracle.models.PostServer;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class CreateViewModel extends AndroidViewModel {
    private final MutableLiveData<String> buttonText;
    private final Repository repository;

    @Inject
    CreateViewModel(
            @NonNull Application application,
            Repository repository
    ) {
        super(application);
        this.buttonText = new MutableLiveData<>();
        this.repository = repository;
    }

    public LiveData<String> getText() { return this.buttonText; }
    public void setText(String text) { this.buttonText.setValue(text); }

    public void uploadPost(PostServer postServer, List<String> pathList) {
        repository.uploadPost(postServer, pathList);
    }
}