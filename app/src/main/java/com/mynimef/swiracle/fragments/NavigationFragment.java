package com.mynimef.swiracle.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mynimef.swiracle.AppLogic.FragmentChanger;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.create.CreateFragment;
import com.mynimef.swiracle.fragments.home.HomeFragment;
import com.mynimef.swiracle.fragments.notifications.NotificationsFragment;
import com.mynimef.swiracle.fragments.profile.ProfileFragment;
import com.mynimef.swiracle.fragments.search.SearchFragment;

public class NavigationFragment extends Fragment {
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private NotificationsFragment notificationsFragment;
    private ProfileFragment profileFragment;
    private CreateFragment createFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        notificationsFragment = new NotificationsFragment();
        profileFragment = new ProfileFragment();
        createFragment = new CreateFragment(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_navigation, container, false);

        BottomNavigationView navView = root.findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.navigation_create) {
                            FragmentChanger.replaceFragment(requireActivity().
                                    getSupportFragmentManager(),
                                    R.id.mainFragment, createFragment);
                        } else {
                            if (itemId == R.id.navigation_home) {
                                FragmentChanger.replaceFragment(getChildFragmentManager(),
                                        R.id.nav_host_fragment, homeFragment);
                            } else if (itemId == R.id.navigation_search) {
                                FragmentChanger.replaceFragment(getChildFragmentManager(),
                                        R.id.nav_host_fragment, searchFragment);
                            }  else if (itemId == R.id.navigation_notifications) {
                                FragmentChanger.replaceFragment(getChildFragmentManager(),
                                        R.id.nav_host_fragment, notificationsFragment);
                            } else if (itemId == R.id.navigation_profile) {
                                FragmentChanger.replaceFragment(getChildFragmentManager(),
                                        R.id.nav_host_fragment, profileFragment);
                            }
                        }
                        return true;
                    }
                });

        return root;
    }
}