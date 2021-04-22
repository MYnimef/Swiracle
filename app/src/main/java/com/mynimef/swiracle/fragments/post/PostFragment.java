package com.mynimef.swiracle.fragments.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.mynimef.swiracle.AppLogic.Post;
import com.mynimef.swiracle.AppLogic.SingletonDatabase;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.adapters.PostImageAdapter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostFragment extends Fragment {
    private final Post post;

    public PostFragment(int pos) {
        this.post = SingletonDatabase.getInstance(getContext())
                .getRecommendationList().getList().get(pos);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.PostImageView);
        recyclerView.setClipToOutline(true);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        PostImageAdapter adapter = new PostImageAdapter(post.getImages().getImages(), -1,
                this);
        recyclerView.setAdapter(adapter);

        TextView title = root.findViewById(R.id.titleView);
        title.setText(post.getTitle());

        TextView description = root.findViewById(R.id.descriptionView);
        description.setText(post.getDescription());

        return root;
    }
}