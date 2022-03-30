package com.mynimef.swiracle.fragments.navigation.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.mynimef.swiracle.R;
import com.mynimef.swiracle.custom.FragmentMenu;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class MyProfileFragment extends FragmentMenu {
    private MyProfileViewModel myProfileViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myProfileViewModel = new ViewModelProvider(this).get(MyProfileViewModel.class);
    }

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View root = inflater.inflate(
                R.layout.fragment_profile_my,
                container,
                false
        );

        Button usernameButton = root.findViewById(R.id.usernameButton);
        TextView postsAmount = root.findViewById(R.id.likesAmountText);
        TextView followingAmount = root.findViewById(R.id.followingAmountText);
        TextView followersAmount = root.findViewById(R.id.followersAmountText);

        myProfileViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            usernameButton.setText("@" + user.getUsername());
            postsAmount.setText(String.valueOf(user.getPostsAmount()));
            followingAmount.setText(String.valueOf(user.getFollowingAmount()));
            followersAmount.setText(String.valueOf(user.getFollowersAmount()));
        });

        return root;
    }
}