package com.mynimef.swiracle;

import android.os.Bundle;

import com.mynimef.swiracle.fragments.login.LoginFragment;
import com.mynimef.swiracle.logic.FragmentChanger;
import com.mynimef.swiracle.repository.Repository;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Repository.getInstance().setSharedPref(this);

        FragmentManager fm = getSupportFragmentManager();
        if (Repository.getInstance().getSignedIn() == 0) {
            FragmentChanger.replaceFragment(
                    fm,
                    R.id.mainFragment,
                    new LoginFragment(fm.findFragmentById(R.id.mainFragment))
            );
        }
    }
}