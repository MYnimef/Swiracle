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

import com.mynimef.swiracle.Interfaces.IHome;
import com.mynimef.swiracle.database.Post;
import com.mynimef.swiracle.dialogs.login.LoginDialogFragment;
import com.mynimef.swiracle.fragments.post.PostFragment;
import com.mynimef.swiracle.fragments.profile.random.RandomProfileFragment;
import com.mynimef.swiracle.logic.FragmentChanger;
import com.mynimef.swiracle.models.PostInfo;
import com.mynimef.swiracle.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomePostAdapter extends RecyclerView.Adapter<HomePostAdapter.PostView> {
    private final IHome home;
    private final Fragment homeFragment;
    private List<Post> postList;

    public HomePostAdapter(Fragment homeFragment) {
        this.home = (IHome) homeFragment;
        this.homeFragment = homeFragment;
        this.postList = new ArrayList<>();
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

        postView.getTopLayout().setOnClickListener(v ->
                FragmentChanger.replaceFragment(homeFragment.getParentFragmentManager(),
                        R.id.nav_host_fragment,
                        new RandomProfileFragment(postInfo.getUsername(), homeFragment)));
        postView.getUserImage();
        postView.getUsername().setText("@" + postInfo.getUsername());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(homeFragment.requireActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        postView.getRecyclerView().setLayoutManager(mLayoutManager);
        PostImageAdapter adapter = new PostImageAdapter(postList.get(position).getImages(),
                postInfo.getId(), homeFragment);
        postView.getRecyclerView().setAdapter(adapter);

        postView.getBottomLayout().setOnClickListener(v -> {
            FragmentChanger.replaceFragment(homeFragment.getParentFragmentManager(),
                    R.id.nav_host_fragment, new PostFragment(postInfo.getId(),
                            0, homeFragment));
        });
        postView.getTitle().setText(postInfo.getTitle());
        postView.getPrice().setText(postInfo.getPrice().getRub() + " RUB");

        postView.getLikes().setText(String.valueOf(postInfo.getLikesAmount()));
        postView.getLikes().setSelected(postInfo.getIsLiked());
        postView.getLikes().setOnClickListener(v -> {
            if (home.getSignedIn() != 1) {
                new LoginDialogFragment().show(homeFragment.getChildFragmentManager(), "ASK");
            } else {
                home.likePost(postInfo.getId());
                if (v.isSelected()) {
                    postInfo.setLikesAmount(postInfo.getLikesAmount() - 1);
                    postInfo.setIsLiked(false);
                } else {
                    postInfo.setLikesAmount(postInfo.getLikesAmount() + 1);
                    postInfo.setIsLiked(true);
                }
                home.updatePostInfo(postInfo);
                v.setSelected(!postView.getLikes().isSelected());
            }
        });

        postView.getComments().setText(String.valueOf(postInfo.getCommentsAmount()));
        postView.getComments().setOnClickListener(v -> {
            //comments.setText(postList.getComments() + "");
        });
    }

    @Override
    public int getItemCount() { return postList.size(); }

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
        public TextView getUsername() { return userNickname; }

        public RecyclerView getRecyclerView() { return recyclerView; }

        public ConstraintLayout getBottomLayout() { return bottomLayout; }
        public TextView getTitle() { return title; }
        public TextView getPrice() { return price; }

        public Button getLikes() { return likes; }
        public Button getComments() { return comments; }
    }
}