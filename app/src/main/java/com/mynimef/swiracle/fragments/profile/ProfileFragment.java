package com.mynimef.swiracle.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.R;
import com.mynimef.swiracle.network.NetworkService;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        Button button = root.findViewById(R.id.button);
        button.setOnClickListener(v -> {
            NetworkService.getInstance().signUp();
        });

        Button buttonLogin = root.findViewById(R.id.login);
        buttonLogin.setOnClickListener(v -> {
            NetworkService.getInstance().signIn();
        });

        return root;
    }
}