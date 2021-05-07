package com.mynimef.swiracle.fragments.create;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mynimef.swiracle.logic.Repository;
import com.mynimef.swiracle.models.PostServer;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CreateViewModel extends AndroidViewModel {
    private final MutableLiveData<String> buttonText;
    private final Repository repository;

    @Inject
    CreateViewModel(@NonNull Application application) {
        super(application);
        this.buttonText = new MutableLiveData<>();
        this.repository = Repository.getInstance();
    }

    public LiveData<String> getText() { return this.buttonText; }
    public void setText(String text) { this.buttonText.setValue(text); }

    public void uploadPost(PostServer postServer, List<String> pathList) {
        repository.uploadPost(postServer, pathList);
    }
}