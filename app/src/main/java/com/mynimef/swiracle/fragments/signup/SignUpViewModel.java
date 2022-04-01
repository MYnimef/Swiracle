package com.mynimef.swiracle.fragments.signup;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.mynimef.swiracle.models.SignUpRequest;
import com.mynimef.swiracle.repository.Repository;
import com.mynimef.swiracle.models.DateModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class SignUpViewModel extends AndroidViewModel {
    private final Repository repository;

    @Inject
    public SignUpViewModel(
            @NonNull Application application,
            Repository repository
    ) {
        super(application);
        this.repository = repository;
    }

    public void signUp(SignUpRequest request, Handler signUpHandler) {
        repository.signUp(request, signUpHandler);
    }
}