package com.mynimef.swiracle;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mynimef.swiracle.AppLogic.Singleton;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Singleton list = Singleton.getInstance();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}