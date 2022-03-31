package com.mynimef.swiracle.fragments.signup;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.mynimef.swiracle.repository.Repository;
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

    public void signUp(
            String username,
            String password,
            String email,
            String name,
            int gender,
            DateModel birthday,
            Handler signUpHandler
    ) {
        repository.signUp(
                username,
                password,
                email,
                name,
                gender,
                birthday,
                signUpHandler
        );
    }
}