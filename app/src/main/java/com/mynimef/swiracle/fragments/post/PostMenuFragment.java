package com.mynimef.swiracle.fragments.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.AppLogic.FragmentChanger;
import com.mynimef.swiracle.Interfaces.IPickNavigation;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.home.HomeMenuFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostMenuFragment extends Fragment {
    private final Fragment fragment;
    private final IPickNavigation pick;

    public PostMenuFragment(Fragment fragment) {
        this.fragment = fragment;
        this.pick = (IPickNavigation) fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post_menu, container, false);

        Button backButton = root.findViewById(R.id.backToHomeButton);
        backButton.setOnClickListener(v -> {
            FragmentChanger.replaceFragment(fragment.getChildFragmentManager(),
                    R.id.nav_host_fragment, pick.getHomeFragment());
            FragmentChanger.replaceFragment(fragment.getChildFragmentManager(),
                    R.id.up_menu_fragment, new HomeMenuFragment());
        });

        return root;
    }
}