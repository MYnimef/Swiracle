package com.mynimef.swiracle.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mynimef.swiracle.Interfaces.IFragmentConnector;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.create.CreateFragment;
import com.mynimef.swiracle.fragments.home.HomeFragment;
import com.mynimef.swiracle.fragments.notifications.NotificationsFragment;
import com.mynimef.swiracle.fragments.profile.ProfileFragment;
import com.mynimef.swiracle.fragments.search.SearchFragment;

public class NavigationFragment extends Fragment {
    private FragmentManager fm;
    private IFragmentConnector connector;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_navigation, container, false);

        BottomNavigationView navView = root.findViewById(R.id.nav_view);
        fm = getChildFragmentManager();
        connector = (IFragmentConnector) getContext();

        navView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.navigation_create) {
                            connector.replaceFragment(new CreateFragment());
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

        return root;
    }

    public void replaceFragment(Fragment fragment) {    //Метод смены Фрагмента
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}