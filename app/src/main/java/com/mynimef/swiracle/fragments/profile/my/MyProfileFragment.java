package com.mynimef.swiracle.fragments.profile.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mynimef.swiracle.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MyProfileFragment extends Fragment {
    private MyProfileViewModel myProfileViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myProfileViewModel = new ViewModelProvider(this).get(MyProfileViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile_my, container, false);

        Button usernameButton = root.findViewById(R.id.usernameButton);
        myProfileViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            usernameButton.setText("@" + user.getUsername());
        });

        return root;
    }
}