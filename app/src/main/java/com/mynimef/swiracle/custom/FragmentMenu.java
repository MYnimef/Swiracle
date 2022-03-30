package com.mynimef.swiracle.custom;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;

import com.mynimef.swiracle.Interfaces.INavigation;

public class FragmentMenu extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                INavigation fragment = getNavigation();
                if (fragment != null) {
                    fragment.replaceToMain();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
