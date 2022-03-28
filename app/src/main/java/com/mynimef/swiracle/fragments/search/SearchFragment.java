package com.mynimef.swiracle.fragments.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.mynimef.swiracle.R;
import com.mynimef.swiracle.custom.SwiracleFragment;

public final class SearchFragment extends SwiracleFragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        return root;
    }
}