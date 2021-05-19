package com.mynimef.swiracle.fragments.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.mynimef.swiracle.logic.Repository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProfileViewModel extends AndroidViewModel {
    private final Repository repository;

    @Inject
    public ProfileViewModel(@NonNull Application application) {
        super(application);
        this.repository = Repository.getInstance();
    }

}