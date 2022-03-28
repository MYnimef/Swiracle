package com.mynimef.swiracle.fragments.navigation.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mynimef.swiracle.logic.Repository;
import com.mynimef.swiracle.models.User;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class MyProfileViewModel extends AndroidViewModel {
    private final Repository repository;

    @Inject
    public MyProfileViewModel(@NonNull Application application) {
        super(application);
        this.repository = Repository.getInstance();
    }

    public LiveData<User> getUser() {
        return repository.getActualUser();
    }
}