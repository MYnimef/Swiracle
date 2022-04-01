package com.mynimef.swiracle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mynimef.swiracle.repository.Repository;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
public final class SplashActivity extends AppCompatActivity {
    @Inject
    public Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository.getSignedIn();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}