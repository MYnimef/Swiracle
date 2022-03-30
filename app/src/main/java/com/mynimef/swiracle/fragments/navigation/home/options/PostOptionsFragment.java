package com.mynimef.swiracle.fragments.navigation.home.options;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.mynimef.swiracle.R;
import com.mynimef.swiracle.custom.FragmentBottom;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostOptionsFragment extends FragmentBottom {
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View root = inflater.inflate(
                R.layout.fragment_post_options,
                container,
                false
        );

        String postId = getArguments().getString("post_id");

        PostOptionsViewModel postOptionsViewModel = new ViewModelProvider(this).get(PostOptionsViewModel.class);

        root.findViewById(R.id.buttonDelete).setOnClickListener(
                view -> postOptionsViewModel.deletePost(postId)
        );

        postOptionsViewModel.getIsDeleted().observe(getViewLifecycleOwner(), deleted -> {
            if (deleted) {
                getNavigation().hideBottomFragment();
            }
        });

        return root;
    }
}
