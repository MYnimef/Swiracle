package com.mynimef.swiracle.fragments.login;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mynimef.swiracle.custom.Fragment;
import com.mynimef.swiracle.fragments.navigation.NavigationFragment;
import com.mynimef.swiracle.models.SignInRequest;
import com.mynimef.swiracle.repository.Repository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class LoginViewModel extends ViewModel {
    private final Repository repository;

    private final MutableLiveData<Boolean> elementAccess;
    private final MutableLiveData<String> errorMessage;
    private final MutableLiveData<Fragment> fragment;

    @Inject
    public LoginViewModel(Repository repository) {
        this.repository = repository;

        elementAccess = new MutableLiveData<>(true);
        errorMessage = new MutableLiveData<>();
        fragment = new MutableLiveData<>();
    }

    public void loginRequest(String username, String password) {
        if (username.equals("")) {
            errorMessage.setValue("Empty username!");
            return;
        } else if (password.equals("")) {
            errorMessage.setValue("Empty password!");
            return;
        }
        elementAccess.setValue(false);

        repository.login(
                new SignInRequest(username, password),
                new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        elementAccess.setValue(true);

                        switch (msg.arg1) {
                            case 0:
                                fragment.setValue(new NavigationFragment());
                                break;
                            case 1:
                                errorMessage.setValue("Wrong username or password!");
                                break;
                            case -1:
                                errorMessage.setValue("No connection");
                                break;
                        }
                        removeCallbacksAndMessages(null);
                    }
                }
        );
    }

    public LiveData<Boolean> getElementAccess() {
        return elementAccess;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Fragment> getFragment() {
        return fragment;
    }
}