package com.mynimef.swiracle.fragments.navigation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mynimef.swiracle.custom.INavigation;
import com.mynimef.swiracle.dialogs.login.LoginDialogFragment;
import com.mynimef.swiracle.fragments.navigation.profile.MyProfileFragment;
import com.mynimef.swiracle.logic.FragmentChanger;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.navigation.create.CreateFragment;
import com.mynimef.swiracle.fragments.navigation.home.HomeFragment;
import com.mynimef.swiracle.fragments.navigation.notifications.NotificationsFragment;
import com.mynimef.swiracle.fragments.navigation.popular.PopularFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NavigationFragment extends Fragment implements INavigation {
    private NavigationViewModel navigationViewModel;
    private BottomNavigationView navView;

    private FragmentManager fm;
    private HomeFragment homeFragment;
    private PopularFragment popularFragment;
    private NotificationsFragment notificationsFragment;
    private MyProfileFragment myProfileFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationViewModel = new ViewModelProvider(this).get(NavigationViewModel.class);
        fm = getChildFragmentManager();

        homeFragment = new HomeFragment();
        popularFragment = new PopularFragment();
        notificationsFragment = new NotificationsFragment();
        myProfileFragment = new MyProfileFragment();

        FragmentChanger.replaceFragment(fm, R.id.nav_host_fragment, homeFragment);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_navigation, container, false);

        navView = root.findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(
                item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.navigation_create) {
                        if (navigationViewModel.getSignedIn() != 1) {
                            new LoginDialogFragment().show(getChildFragmentManager(), "ASK");
                        } else if (ContextCompat.checkSelfPermission(requireContext(),
                                Manifest.permission.READ_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_GRANTED) {
                            FragmentChanger.replaceFragment(requireActivity().
                                            getSupportFragmentManager(),
                                    R.id.mainFragment, new CreateFragment());
                        } else {
                            requireActivity().requestPermissions(
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);
                        }
                        return false;
                    }

                    if (itemId == R.id.navigation_home) {
                        FragmentChanger.replaceFragment(fm, R.id.nav_host_fragment, homeFragment);
                    } else if (itemId == R.id.navigation_popular) {
                        FragmentChanger.replaceFragment(fm, R.id.nav_host_fragment, popularFragment);
                    } else if (itemId == R.id.navigation_notifications) {
                        FragmentChanger.replaceFragment(fm,
                                R.id.nav_host_fragment, notificationsFragment);
                    } else if (itemId == R.id.navigation_profile) {
                        if (navigationViewModel.getSignedIn() != 1) {
                            new LoginDialogFragment().show(getChildFragmentManager(), "ASK");
                            return false;
                        }
                        FragmentChanger.replaceFragment(fm,
                                R.id.nav_host_fragment, myProfileFragment);
                    }
                    return true;
                });
        return root;
    }

    @Override
    public void replaceToMain() {
        FragmentChanger.replaceFragment(fm, R.id.nav_host_fragment, homeFragment);
        navView.getMenu().getItem(0).setChecked(true);
    }
}