package com.mynimef.swiracle.fragments.login;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.mynimef.swiracle.repository.Repository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class LoginViewModel extends AndroidViewModel {
    private final Repository repository;

    @Inject
    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
    }

    public void loginRequest(String username, String password, Handler handler) {
        repository.login(username, password, handler);
    }

    public void setSignedIn(int signedIn) {
        repository.setSignedIn(signedIn);
    }
}