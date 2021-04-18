package com.mynimef.swiracle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mynimef.swiracle.AppLogic.Post;
import com.mynimef.swiracle.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostView> {
    private final ArrayList<Post> postList;
    private final Fragment fragment;

    public PostAdapter(ArrayList<Post> list, Fragment fragment) {
        this.postList = list;
        this.fragment = fragment;
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

        Glide
                .with(fragment)
                .load(post.getImages().getImages().get(0))
                .into(postView.getPic());
        postView.getPic().setOnClickListener(v -> {
            //fragment.replaceFragment(new PostFragment());
        });

        postView.getDescription().setText(post.getDescription());
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
        private final ImageView pic;
        private final Button description;
        private final Button likes;
        private final Button comments;

        public PostView(View view) {
            super(view);
            title = (Button) view.findViewById(R.id.button);
            pic = view.findViewById(R.id.imageView);
            pic.setClipToOutline(true);
            description = (Button) view.findViewById(R.id.description);
            likes = (Button) view.findViewById(R.id.likes);
            comments = (Button) view.findViewById(R.id.comments);
        }

        public Button getTitle() {
            return title;
        }

        public ImageView getPic() {
            return pic;
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