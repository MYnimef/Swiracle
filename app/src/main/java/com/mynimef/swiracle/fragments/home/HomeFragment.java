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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mynimef.swiracle.Interfaces.IHome;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.adapters.HomePostAdapter;
import com.mynimef.swiracle.models.PostInfo;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment implements IHome {
    private HomeViewModel homeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.update();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rv = root.findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mLayoutManager);

        HomePostAdapter adapter = new HomePostAdapter(this, getParentFragment());
        rv.setAdapter(adapter);

        homeViewModel.getRecommendationList().observe(getViewLifecycleOwner(),
                adapter::setPosts);

        SwipeRefreshLayout swipeRefresh = root.findViewById(R.id.swipeRefreshHome);
        swipeRefresh.setOnRefreshListener(() -> {
            homeViewModel.update();
            swipeRefresh.setRefreshing(false);
        });

        return root;
    }

    @Override
    public int getSignedIn() { return homeViewModel.getSignedIn(); }
    @Override
    public void likePost(String id) { homeViewModel.likePost(id); }
    @Override
    public void updatePostInfo(PostInfo postInfo) { homeViewModel.updatePostInfo(postInfo); }
}