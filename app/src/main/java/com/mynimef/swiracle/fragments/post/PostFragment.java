package com.mynimef.swiracle.fragments.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.mynimef.swiracle.AppLogic.Post;
import com.mynimef.swiracle.AppLogic.PostViewModel;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.adapters.PostImageAdapter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostFragment extends Fragment {
    private Post post;
    private final int pos;
    private final int num;

    public PostFragment(int pos, int num) {
        this.pos = pos;
        this.num = num;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post, container, false);

        PostViewModel postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        post = postViewModel.getRecommendationList().getValue().get(pos);
        postViewModel.getRecommendationList().observe(getViewLifecycleOwner(),
                posts -> post = posts.get(pos));

        RecyclerView recyclerView = root.findViewById(R.id.PostImageView);
        recyclerView.setClipToOutline(true);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        PostImageAdapter adapter = new PostImageAdapter(post.getImages().getImages(),
                -1, this);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(num);

        TextView title = root.findViewById(R.id.titleView);
        title.setText(post.getTitle());

        TextView description = root.findViewById(R.id.descriptionView);
        description.setText(post.getDescription());

        return root;
    }
}