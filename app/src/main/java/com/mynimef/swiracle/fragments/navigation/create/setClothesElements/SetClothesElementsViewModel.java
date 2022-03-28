package com.mynimef.swiracle.fragments.navigation.create.setClothesElements;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.mynimef.swiracle.logic.Repository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class SetClothesElementsViewModel extends AndroidViewModel {
    private final Repository repository;

    @Inject
    public SetClothesElementsViewModel(@NonNull Application application) {
        super(application);
        this.repository = Repository.getInstance();
    }

    public void getClothes(String url , Handler handler) {
        repository.getClothesParsing(url, handler);
    }
}