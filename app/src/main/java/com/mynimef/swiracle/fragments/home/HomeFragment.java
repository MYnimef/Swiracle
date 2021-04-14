package com.mynimef.swiracle.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.R;
import com.mynimef.swiracle.AppLogic.Singleton;
import com.mynimef.swiracle.adapters.PostAdapter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        PostAdapter adapter = new PostAdapter(root.getContext(), Singleton.getInstance().getRecommendationList(),
                getParentFragment());
        ListView lv = (ListView) root.findViewById(R.id.list_view);
        lv.setAdapter(adapter);

        return root;
    }
}