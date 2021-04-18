package com.mynimef.swiracle;

import android.app.Application;

import com.mynimef.swiracle.AppLogic.SingletonDatabase;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class SwiracleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SingletonDatabase.getInstance(getApplicationContext());
    }
}