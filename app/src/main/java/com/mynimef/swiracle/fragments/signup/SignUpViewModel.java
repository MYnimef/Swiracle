package com.mynimef.swiracle.fragments.signup;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.mynimef.swiracle.logic.Repository;
import com.mynimef.swiracle.models.DateModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class SignUpViewModel extends AndroidViewModel {
    private final Repository repository;

    @Inject
    public SignUpViewModel(@NonNull Application application) {
        super(application);
        this.repository = Repository.getInstance();
    }

    public void signUp(String username, String password, String email,
                       String firstName, String lastName,
                       int gender, DateModel birthday,
                       Handler signUpHandler) {
        repository.signUp(username, password, email,
                firstName, lastName, gender, birthday,
                signUpHandler);
    }
}