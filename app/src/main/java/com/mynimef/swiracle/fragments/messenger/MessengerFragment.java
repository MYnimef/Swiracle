package com.mynimef.swiracle.fragments.messenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.R;
import com.mynimef.swiracle.custom.SwiracleFragment;

public final class MessengerFragment extends SwiracleFragment {
    private Fragment parentFragment;

    public MessengerFragment(Fragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_messenger, container, false);

        return root;
    }
}