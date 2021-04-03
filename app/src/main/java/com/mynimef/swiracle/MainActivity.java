package com.mynimef.swiracle;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mynimef.swiracle.Interfaces.IFragmentConnector;
import com.mynimef.swiracle.MainFragments.home.HomeFragment;
import com.mynimef.swiracle.MainFragments.notifications.NotificationsFragment;
import com.mynimef.swiracle.MainFragments.profile.ProfileFragment;
import com.mynimef.swiracle.MainFragments.search.SearchFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements IFragmentConnector {
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        fm = getSupportFragmentManager();

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_create) {
                    startActivity(new Intent(MainActivity.this, CreateActivity.class));
                } else {
                    if (itemId == R.id.navigation_home) {
                        replaceFragment(new HomeFragment());
                    } else if (itemId == R.id.navigation_search) {
                        replaceFragment(new SearchFragment());
                    }  else if (itemId == R.id.navigation_notifications) {
                        replaceFragment(new NotificationsFragment());
                    } else if (itemId == R.id.navigation_profile) {
                        replaceFragment(new ProfileFragment());
                    }
                }
                return true;
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}