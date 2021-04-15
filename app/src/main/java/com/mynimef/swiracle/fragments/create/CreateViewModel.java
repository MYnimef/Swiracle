package com.mynimef.swiracle.fragments.create;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CreateViewModel extends ViewModel {
    private final SavedStateHandle savedStateHandle;
    private final MutableLiveData<String> buttonText;

    @Inject
    CreateViewModel(SavedStateHandle savedStateHandle) {
        this.savedStateHandle = savedStateHandle;
        this.buttonText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return this.buttonText;
    }

    public void setText(String text) {
        this.buttonText.setValue(text);
    }
}