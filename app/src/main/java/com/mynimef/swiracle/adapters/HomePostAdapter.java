package com.mynimef.swiracle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.mynimef.swiracle.api.database.Post;
import com.mynimef.swiracle.api.database.PostInfo;
import com.mynimef.swiracle.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomePostAdapter extends RecyclerView.Adapter<HomePostAdapter.PostView> {
    private List<Post> postList;
    private final Fragment fragment;

    public HomePostAdapter(Fragment fragment) {
        this.fragment = fragment;
        postList = new ArrayList<>();
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
        PostInfo postInfo = postList.get(position).getPostInfo();

        postView.getTopLayout().setOnClickListener(v -> {
        });
        postView.getUserImage();
        postView.getUserNickname().setText("user12345");

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(fragment.getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        postView.getRecyclerView().setLayoutManager(mLayoutManager);
        PostImageAdapter adapter = new PostImageAdapter(postInfo.getImages().getImages(), position,
                fragment);
        postView.getRecyclerView().setAdapter(adapter);

        postView.getBottomLayout().setOnClickListener(v -> {
        });
        postView.getTitle().setText(postInfo.getTitle());
        postView.getPrice().setText(postInfo.getPrice().getRub() + " RUB");

        postView.getLikes().setText(String.valueOf(postInfo.getLikesAmount()));
        postView.getLikes().setOnClickListener(v -> {
            postView.getLikes().setSelected(!postView.getLikes().isSelected());
            postInfo.increaseLikesAmount();
            //comments.setText(postList.getComments() + "");
        });

        postView.getComments().setText(String.valueOf(postInfo.getCommentsAmount()));
        postView.getComments().setOnClickListener(v -> {
            postInfo.increaseCommentsAmount();
            //comments.setText(postList.getComments() + "");
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class PostView extends RecyclerView.ViewHolder {
        private final ConstraintLayout topLayout;
        private final ImageView userImage;
        private final TextView userNickname;
        private final RecyclerView recyclerView;
        private final ConstraintLayout bottomLayout;
        private final TextView title;
        private final TextView price;
        private final Button likes;
        private final Button comments;

        public PostView(View view) {
            super(view);
            topLayout = view.findViewById(R.id.topLayout);
            userImage = view.findViewById(R.id.userImageView);
            userNickname = view.findViewById(R.id.userNicknameView);

            recyclerView = view.findViewById(R.id.imageView);
            recyclerView.setClipToOutline(true);
            PagerSnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);

            bottomLayout = view.findViewById(R.id.bottomLayout);
            title = view.findViewById(R.id.postTitleView);
            price = view.findViewById(R.id.postPriceView);

            likes = view.findViewById(R.id.likesButton);
            comments = view.findViewById(R.id.commentsButton);
        }

        public ConstraintLayout getTopLayout() { return topLayout; }
        public ImageView getUserImage() { return userImage; }
        public TextView getUserNickname() { return userNickname; }

        public RecyclerView getRecyclerView() { return recyclerView; }

        public ConstraintLayout getBottomLayout() { return bottomLayout; }
        public TextView getTitle() { return title; }
        public TextView getPrice() { return price; }

        public Button getLikes() { return likes; }
        public Button getComments() { return comments; }
    }
}