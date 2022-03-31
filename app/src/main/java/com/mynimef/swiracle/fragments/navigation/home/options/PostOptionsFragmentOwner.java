package com.mynimef.swiracle.fragments.navigation.home.options;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.mynimef.swiracle.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class PostOptionsFragmentOwner extends PostOptionsFragment {
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View root = inflater.inflate(
                R.layout.fragment_post_options_owner,
                container,
                false
        );

        Bundle bundle = getArguments();
        if (bundle != null) {
            initView(
                    root,
                    new ViewModelProvider(this).get(PostOptionsViewModel.class),
                    bundle.getString("post_id", "")
            );
        }

        return root;
    }

    @Override
    protected void initView(View root, PostOptionsViewModel viewModel, String postId) {
        super.initView(root, viewModel, postId);

        root.findViewById(R.id.buttonDelete).setOnClickListener(
                view -> viewModel.deletePost(postId)
        );

        viewModel.getIsDeleted().observe(getViewLifecycleOwner(), deleted -> {
            if (deleted) {
                getNavigation().hideBottomFragment();
            }
        });
    }
}
