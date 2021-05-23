package com.mynimef.swiracle.fragments.profile.random;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mynimef.swiracle.R;
import com.mynimef.swiracle.logic.FragmentChanger;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RandomProfileFragment extends Fragment {
    private final Fragment previousFragment;
    private final String username;

    public RandomProfileFragment(String username, Fragment previousFragment) {
        this.username = username;
        this.previousFragment = previousFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile_random, container, false);
        TextView profileName = root.findViewById(R.id.profileName);
        Button usernameButton = root.findViewById(R.id.usernameButton);
        TextView followingAmount = root.findViewById(R.id.followingAmountText);
        TextView followersAmount = root.findViewById(R.id.followersAmountText);
        Button subscribe = root.findViewById(R.id.subscribeButton);

        RandomProfileViewModel viewModel =
                new ViewModelProvider(this).get(RandomProfileViewModel.class);
        viewModel.loadProfile(username);
        viewModel.getProfile().observe(getViewLifecycleOwner(), profileView -> {
            profileName.setText(profileView.getFirstName() + " " + profileView.getLastName());
            usernameButton.setText(profileView.getUsername());
            followingAmount.setText(String.valueOf(profileView.getFollowingAmount()));
            followersAmount.setText(String.valueOf(profileView.getFollowersAmount()));

            subscribe.setOnClickListener(v -> {
                viewModel.subscribe(profileView.getUsername());
                viewModel.loadProfile(username);
            });
        });

        ImageButton backButton = root.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            FragmentChanger.replaceFragment(getParentFragmentManager(),
                    R.id.nav_host_fragment, previousFragment);
        });

        return root;
    }
}