package com.mynimef.swiracle.fragments.login;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.mynimef.swiracle.models.SignInRequest;
import com.mynimef.swiracle.repository.Repository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class LoginViewModel extends AndroidViewModel {
    private final Repository repository;

    @Inject
    public LoginViewModel(
            @NonNull Application application,
            Repository repository
    ) {
        super(application);
        this.repository = repository;
    }

    public void loginRequest(SignInRequest request, Handler handler) {
        repository.login(request, handler);
    }
}