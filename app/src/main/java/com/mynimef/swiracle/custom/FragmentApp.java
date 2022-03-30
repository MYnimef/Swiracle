package com.mynimef.swiracle.custom;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;

public class FragmentApp extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getParentFragmentManager().popBackStackImmediate();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
