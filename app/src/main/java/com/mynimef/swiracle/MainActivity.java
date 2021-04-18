package com.mynimef.swiracle;

import android.os.Bundle;

import com.mynimef.swiracle.AppLogic.SingletonDatabase;

import androidx.appcompat.app.AppCompatActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SingletonDatabase.getInstance(getApplicationContext()).initialiseGallery(this);
    }
}