package com.mynimef.swiracle.fragments.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.mynimef.swiracle.Interfaces.IPickNavigation;
import com.mynimef.swiracle.adapters.PostClothesAdapter;
import com.mynimef.swiracle.database.Post;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.adapters.PostImageAdapter;
import com.mynimef.swiracle.logic.FragmentChanger;
import com.mynimef.swiracle.models.PostDetails;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostFragment extends Fragment {
    private Post post;
    private PostDetails details;
    private final int pos;
    private final int num;
    private final Fragment fragment;
    private final IPickNavigation pick;

    public PostFragment(int pos, int num, Fragment fragment) {
        this.pos = pos;
        this.num = num;
        this.fragment = fragment;
        this.pick = (IPickNavigation) fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post, container, false);

        Button backButton = root.findViewById(R.id.backToHomeButton);
        backButton.setOnClickListener(v -> {
            FragmentChanger.replaceFragment(fragment.getChildFragmentManager(),
                    R.id.nav_host_fragment, pick.getHomeFragment());
        });

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
        postViewModel.getRecommendationList().observe(getViewLifecycleOwner(),
                posts -> {
            post = posts.get(pos);
            PostImageAdapter imagesAdapter = new PostImageAdapter(post.getImages(),
                            -1, this);
            imagesRecyclerView.setAdapter(imagesAdapter);
            imagesRecyclerView.scrollToPosition(num);
            title.setText(post.getPostInfo().getTitle());
            postViewModel.loadDetails(post.getPostInfo().getId());
        });

        details = postViewModel.getDetails().getValue();
        postViewModel.getDetails().observe(getViewLifecycleOwner(), newDetails -> {
            details = newDetails;
            description.setText(details.getDescription());
            PostClothesAdapter clothesAdapter = new PostClothesAdapter(details.getClothes(), this);
            clothesRecyclerView.setAdapter(clothesAdapter);
        });

        return root;
    }
}