package com.mynimef.swiracle.fragments.navigation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.mynimef.swiracle.repository.Repository;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class NavigationViewModel extends AndroidViewModel {
    private final Repository repository;

    @Inject
    public NavigationViewModel(
            @NonNull Application application,
            Repository repository
    ) {
        super(application);
        this.repository = repository;
    }

    public int getSignedIn() {
        return repository.getSignedIn();
    }
}