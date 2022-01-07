package com.mynimef.swiracle.fragments.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.mynimef.swiracle.adapters.PostClothesAdapter;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.adapters.PostImageAdapter;
import com.mynimef.swiracle.custom.SwiracleFragment;
import com.mynimef.swiracle.fragments.profile.RandomProfileFragment;
import com.mynimef.swiracle.logic.FragmentChanger;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostFragment extends SwiracleFragment {
    private final String id;
    private final int num;

    public PostFragment(String id, int num) {
        this.id = id;
        this.num = num;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post, container, false);

        Button backButton = root.findViewById(R.id.backToHomeButton);
        backButton.setOnClickListener(v -> getParentFragmentManager().popBackStackImmediate());
        Button profileButton = root.findViewById(R.id.viewProfileButton);

        RecyclerView imagesRecyclerView = root.findViewById(R.id.PostImageView);
        imagesRecyclerView.setHasFixedSize(true);
        imagesRecyclerView.setClipToOutline(true);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(imagesRecyclerView);
        LinearLayoutManager imagesLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        imagesRecyclerView.setLayoutManager(imagesLayoutManager);

        TextView title = root.findViewById(R.id.titleView);
        TextView description = root.findViewById(R.id.descriptionView);

        RecyclerView clothesRecyclerView = root.findViewById(R.id.elementsView);
        clothesRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL));
        clothesRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager clothesLayoutManager = new LinearLayoutManager(getActivity());
        clothesRecyclerView.setLayoutManager(clothesLayoutManager);

        PostViewModel postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.getPost(id).observe(getViewLifecycleOwner(), post -> {
            profileButton.setText(post.getPostInfo().getUsername());
            profileButton.setOnClickListener(v ->
                    FragmentChanger.replaceFragmentAnim(getParentFragmentManager(),
                    R.id.nav_host_fragment,
                    new RandomProfileFragment(post.getPostInfo().getUsername())));

            PostImageAdapter imagesAdapter = new PostImageAdapter(post.getImages(),
                            "", this);
            imagesRecyclerView.setAdapter(imagesAdapter);
            imagesRecyclerView.scrollToPosition(num);
            title.setText(post.getPostInfo().getTitle());
            postViewModel.loadDetails(post.getPostInfo().getId());
        });

        postViewModel.getDetails().observe(getViewLifecycleOwner(), newDetails -> {
            description.setText(newDetails.getDescription());
            PostClothesAdapter clothesAdapter =
                    new PostClothesAdapter(newDetails.getClothes(), this);
            clothesRecyclerView.setAdapter(clothesAdapter);
        });

        return root;
    }
}