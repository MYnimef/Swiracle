package com.mynimef.swiracle.fragments.profile.random;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mynimef.swiracle.R;
import com.mynimef.swiracle.custom.SwiracleFragment;
import com.mynimef.swiracle.models.ESubscription;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RandomProfileFragment extends SwiracleFragment {
    private final String username;

    public RandomProfileFragment(String username) {
        this.username = username;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile_random, container, false);
        TextView profileName = root.findViewById(R.id.profileName);
        Button usernameButton = root.findViewById(R.id.usernameButton);
        TextView postsAmount = root.findViewById(R.id.postsAmountText);
        TextView followingAmount = root.findViewById(R.id.followingAmountText);
        TextView followersAmount = root.findViewById(R.id.followersAmountText);

        LinearLayout interactLayout = root.findViewById(R.id.interactLayout);
        Button subscribe = root.findViewById(R.id.subscribeButton);
        Button message = root.findViewById(R.id.messageButton);
        ImageButton unsubscribe = root.findViewById(R.id.unsubscribeButton);

        RandomProfileViewModel viewModel =
                new ViewModelProvider(this).get(RandomProfileViewModel.class);
        viewModel.loadProfile(username);
        viewModel.getProfile().observe(getViewLifecycleOwner(), profileView -> {
            profileName.setText(profileView.getFirstName() + " " + profileView.getLastName());
            usernameButton.setText("@" + profileView.getUsername());
            postsAmount.setText(String.valueOf(profileView.getPostsAmount()));
            followingAmount.setText(String.valueOf(profileView.getFollowingAmount()));
            followersAmount.setText(String.valueOf(profileView.getFollowersAmount()));

            if (profileView.getSubscription() == ESubscription.SUBSCRIBED) {
                subscribe.setVisibility(View.GONE);
                message.setVisibility(View.VISIBLE);
                unsubscribe.setVisibility(View.VISIBLE);
            } else if (profileView.getSubscription() == ESubscription.OWNER) {
                interactLayout.setVisibility(View.GONE);
            }

            subscribe.setOnClickListener(v -> {
                viewModel.subscribe(profileView.getUsername());
                if (profileView.getSubscription() == ESubscription.NOT_SUBSCRIBED) {
                    followersAmount.setText(String.valueOf(profileView.getFollowersAmount() + 1));
                } else {
                    followersAmount.setText(String.valueOf(profileView.getFollowersAmount()));
                }
                subscribe.setVisibility(View.GONE);
                message.setVisibility(View.VISIBLE);
                unsubscribe.setVisibility(View.VISIBLE);
            });

            unsubscribe.setOnClickListener(v -> {
                viewModel.subscribe(profileView.getUsername());
                if (profileView.getSubscription() == ESubscription.SUBSCRIBED) {
                    followersAmount.setText(String.valueOf(profileView.getFollowersAmount() - 1));
                } else {
                    followersAmount.setText(String.valueOf(profileView.getFollowersAmount()));
                }
                unsubscribe.setVisibility(View.GONE);
                message.setVisibility(View.GONE);
                subscribe.setVisibility(View.VISIBLE);
            });
        });

        ImageButton backButton = root.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> getParentFragmentManager().popBackStackImmediate());

        SwipeRefreshLayout swipeRefresh = root.findViewById(R.id.swipeRefreshProfile);
        swipeRefresh.setOnRefreshListener(() -> {
            viewModel.loadProfile(username);
            swipeRefresh.setRefreshing(false);
        });

        return root;
    }
}