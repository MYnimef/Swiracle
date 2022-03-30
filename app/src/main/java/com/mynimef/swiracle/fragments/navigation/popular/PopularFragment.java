package com.mynimef.swiracle.fragments.navigation.popular;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.mynimef.swiracle.R;
import com.mynimef.swiracle.custom.FragmentMenu;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class PopularFragment extends FragmentMenu {
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        return root;
    }
}