package com.mynimef.swiracle;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mynimef.swiracle.ui.home.HomeFragment;
import com.mynimef.swiracle.ui.notifications.NotificationsFragment;
import com.mynimef.swiracle.ui.profile.ProfileFragment;
import com.mynimef.swiracle.ui.search.SearchFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_create) {
                    startActivity(new Intent(MainActivity.this, CreateActivity.class));
                } else {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    if (itemId == R.id.navigation_home) {
                        ft.add(R.id.nav_host_fragment, new HomeFragment());
                    } else if (itemId == R.id.navigation_search) {
                        ft.add(R.id.nav_host_fragment, new SearchFragment());
                    }  else if (itemId == R.id.navigation_notifications) {
                        ft.add(R.id.nav_host_fragment, new NotificationsFragment());
                    } else if (itemId == R.id.navigation_profile) {
                        ft.add(R.id.nav_host_fragment, new ProfileFragment());
                    }
                    ft.commit();
                }
                return true;
            }
        });
    }
}