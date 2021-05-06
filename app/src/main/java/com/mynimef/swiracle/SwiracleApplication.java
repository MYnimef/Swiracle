package com.mynimef.swiracle;

import android.app.Application;

import com.mynimef.swiracle.logic.Repository;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class SwiracleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Repository.getInstance().init(this);
    }
}