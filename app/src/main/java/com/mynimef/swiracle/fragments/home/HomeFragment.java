package com.mynimef.swiracle.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mynimef.swiracle.R;
import com.mynimef.swiracle.AppLogic.Singleton;
import com.mynimef.swiracle.adapters.PostAdapter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rv = (RecyclerView) root.findViewById(R.id.recycler_view);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mLayoutManager);

        PostAdapter adapter = new PostAdapter(Singleton.getInstance()
                .getRecommendationList()
                .getList(),
                getParentFragment());
        rv.setAdapter(adapter);

        return root;
    }
}