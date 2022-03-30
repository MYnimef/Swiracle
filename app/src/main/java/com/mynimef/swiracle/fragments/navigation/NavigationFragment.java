package com.mynimef.swiracle.fragments.navigation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.Interfaces.INavigation;
import com.mynimef.swiracle.custom.Fragment;
import com.mynimef.swiracle.dialogs.login.LoginDialogFragment;
import com.mynimef.swiracle.fragments.navigation.create.CreateFragment;
import com.mynimef.swiracle.fragments.navigation.home.HomeFragment;
import com.mynimef.swiracle.fragments.navigation.notifications.NotificationsFragment;
import com.mynimef.swiracle.fragments.navigation.popular.PopularFragment;
import com.mynimef.swiracle.fragments.navigation.profile.MyProfileFragment;
import com.mynimef.swiracle.logic.FragmentChanger;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class NavigationFragment extends Fragment implements INavigation {
    private NavigationViewModel navigationViewModel;
    private BottomNavigationView navView;

    private HomeFragment homeFragment;
    private PopularFragment popularFragment;
    private NotificationsFragment notificationsFragment;
    private MyProfileFragment myProfileFragment;

    private ImageView transparentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationViewModel = new ViewModelProvider(this).get(NavigationViewModel.class);

        homeFragment = new HomeFragment();
        popularFragment = new PopularFragment();
        notificationsFragment = new NotificationsFragment();
        myProfileFragment = new MyProfileFragment();

        FragmentChanger.replaceFragment(
                getChildFragmentManager(),
                R.id.nav_host_fragment,
                homeFragment
        );
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View root = inflater.inflate(
                R.layout.fragment_navigation,
                container,
                false
        );

        navView = root.findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(
                item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.navigation_create) {
                        if (navigationViewModel.getSignedIn() != 1) {
                            new LoginDialogFragment(this)
                                    .show(getChildFragmentManager(), "ASK");
                        } else if (
                                ContextCompat.checkSelfPermission(
                                        requireContext(),
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            FragmentChanger.replaceFragment(
                                    requireActivity().getSupportFragmentManager(),
                                    R.id.mainFragment,
                                    new CreateFragment()
                            );
                        } else {
                            requireActivity().requestPermissions(
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1
                            );
                        }
                        return false;
                    }

                    if (itemId == R.id.navigation_home) {
                        FragmentChanger.replaceFragment(
                                getChildFragmentManager(),
                                R.id.nav_host_fragment,
                                homeFragment
                        );
                    } else if (itemId == R.id.navigation_popular) {
                        FragmentChanger.replaceFragment(
                                getChildFragmentManager(),
                                R.id.nav_host_fragment,
                                popularFragment
                        );
                    } else if (itemId == R.id.navigation_notifications) {
                        FragmentChanger.replaceFragment(
                                getChildFragmentManager(),
                                R.id.nav_host_fragment,
                                notificationsFragment
                        );
                    } else if (itemId == R.id.navigation_profile) {
                        if (navigationViewModel.getSignedIn() != 1) {
                            new LoginDialogFragment(this)
                                    .show(getChildFragmentManager(), "ASK");
                            return false;
                        }
                        FragmentChanger.replaceFragment(
                                getChildFragmentManager(),
                                R.id.nav_host_fragment,
                                myProfileFragment
                        );
                    }
                    return true;
                });

        transparentView = root.findViewById(R.id.transparentView);
        transparentView.setOnClickListener(v -> hideBottomFragment());

        root.findViewById(R.id.bottomFragment).setOnClickListener(view -> {});

        return root;
    }

    @Override
    public void replaceToMain() {
        FragmentChanger.replaceFragment(
                getChildFragmentManager(),
                R.id.nav_host_fragment,
                homeFragment
        );
        navView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void showBottomFragment(Fragment fragment) {
        transparentView.setVisibility(View.VISIBLE);
        FragmentChanger.replaceFragmentAnimBottom(
                getChildFragmentManager(),
                R.id.bottomFragment,
                fragment
        );
    }

    @Override
    public void hideBottomFragment() {
        getChildFragmentManager().popBackStack();
        transparentView.setVisibility(View.GONE);
    }
}