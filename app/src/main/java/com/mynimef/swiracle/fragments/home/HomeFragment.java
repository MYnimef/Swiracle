package com.mynimef.swiracle.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mynimef.swiracle.AppLogic.PostViewModel;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.adapters.HomePostAdapter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private PostViewModel postViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rv = root.findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mLayoutManager);

        HomePostAdapter adapter = new HomePostAdapter(getParentFragment());
        rv.setAdapter(adapter);

        this.postViewModel.getRecommendationList().observe(getViewLifecycleOwner(),
                adapter::setPosts);

        return root;
    }
}