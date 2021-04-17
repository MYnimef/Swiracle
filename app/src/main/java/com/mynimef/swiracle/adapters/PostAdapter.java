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

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private final ArrayList<Post> postList;
    private final Fragment fragment;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button title;
        private final ImageView pic;
        private final Button description;
        private final Button likes;
        private final Button comments;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            title = (Button) view.findViewById(R.id.button);
            title.setOnClickListener(v -> {
            });

            pic = view.findViewById(R.id.imageView);
            pic.setClipToOutline(true);
            //pic.setImageBitmap(post.getImages().get(0));
            pic.setOnClickListener(v -> {
                //fragment.replaceFragment(new PostFragment());
            });

            description = (Button) view.findViewById(R.id.description);
            description.setOnClickListener(v -> {
            });

            likes = (Button) view.findViewById(R.id.likes);
            likes.setOnClickListener(v -> {
                //postList.increaseLikes();
                //likes.setText(postList.getLikes() + "");
            });

            comments = (Button) view.findViewById(R.id.comments);
            comments.setOnClickListener(v -> {
                //postList.increaseComments();
                //comments.setText(postList.getComments() + "");
            });
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

    public PostAdapter(ArrayList<Post> list, Fragment fragment) {
        this.postList = list;
        this.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_post, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        //viewHolder.getTextView().setText(localDataSet[position]);
        viewHolder.getTitle().setText(postList.get(position).getTitle());

        Glide
                .with(fragment)
                .load(postList.get(position).getImages().get(0))
                .into(viewHolder.getPic());

        viewHolder.getDescription().setText(postList.get(position).getDescription());

        viewHolder.getLikes().setText(postList.get(position).getLikes() + "");

        viewHolder.getComments().setText(postList.get(position).getComments() + "");

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}