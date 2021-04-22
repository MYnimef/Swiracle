package com.mynimef.swiracle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.mynimef.swiracle.AppLogic.Post;
import com.mynimef.swiracle.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomePostAdapter extends RecyclerView.Adapter<HomePostAdapter.PostView> {
    private List<Post> postList;
    private final Fragment fragment;

    public HomePostAdapter(Fragment fragment) {
        this.fragment = fragment;
        postList = new ArrayList<Post>();
    }

    public void setPosts(List<Post> posts) {
        this.postList = posts;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public PostView onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_post, viewGroup, false);

        return new PostView(view);
    }

    @Override
    public void onBindViewHolder(PostView postView, final int position) {
        Post post = postList.get(position);

        postView.getTitle().setText(post.getTitle());
        postView.getTitle().setOnClickListener(v -> {
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(fragment.getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        postView.getRecyclerView().setLayoutManager(mLayoutManager);
        PostImageAdapter adapter = new PostImageAdapter(post.getImages().getImages(), position,
                fragment);
        postView.getRecyclerView().setAdapter(adapter);

        postView.getDescription().setText(String.format("%s - %s", post.getTitle(),
                post.getDescription()));
        postView.getDescription().setOnClickListener(v -> {
        });

        postView.getLikes().setText(String.valueOf(post.getLikes()));
        postView.getLikes().setOnClickListener(v -> {
            post.increaseLikes();
            //likes.setText(postList.getLikes() + "");
        });

        postView.getComments().setText(String.valueOf(post.getComments()));
        postView.getComments().setOnClickListener(v -> {
            post.increaseComments();
            //comments.setText(postList.getComments() + "");
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class PostView extends RecyclerView.ViewHolder {
        private final Button title;
        private final RecyclerView recyclerView;
        private final Button description;
        private final Button likes;
        private final Button comments;

        public PostView(View view) {
            super(view);
            title = view.findViewById(R.id.button);

            recyclerView = view.findViewById(R.id.imageView);
            recyclerView.setClipToOutline(true);
            PagerSnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);

            description = view.findViewById(R.id.description);
            likes = view.findViewById(R.id.likes);
            comments = view.findViewById(R.id.comments);
        }

        public Button getTitle() {
            return title;
        }

        public RecyclerView getRecyclerView() {
            return recyclerView;
        }

        public Button getDescription() {
            return description;
        }

        public Button getLikes() {
            return likes;
        }

        public Button getComments() {
            return comments;
        }
    }
}