package com.mynimef.swiracle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mynimef.swiracle.logic.FragmentChanger;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.models.PostImage;
import com.mynimef.swiracle.fragments.post.PostFragment;

import java.util.List;

public class PostImageAdapter extends RecyclerView.Adapter<PostImageAdapter.PostImageView> {
    private final List<PostImage> images;
    private final String id;
    private final Fragment fragment;

    public PostImageAdapter(List<PostImage> images, String id, Fragment fragment) {
        this.images = images;
        this.id = id;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public PostImageView onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_image_post, viewGroup, false);

        return new PostImageView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostImageView holder, int position) {
        PostImage image = images.get(position);
        Glide
                .with(fragment)
                .load(image.getImageUrl())
                .into(holder.getPic());
    }

    @Override
    public int getItemCount() { return images.size(); }

    class PostImageView extends RecyclerView.ViewHolder {
        private final ImageView pic;

        public PostImageView(@NonNull View view) {
            super(view);
            pic = view.findViewById(R.id.imageView);
            pic.setOnClickListener(v -> {
                if (!id.equals("")) {
                    int num = getBindingAdapterPosition();
                    FragmentChanger.replaceFragment(fragment.getChildFragmentManager(),
                            R.id.nav_host_fragment, new PostFragment(id, num, fragment));
                }
            });
        }

        public ImageView getPic() {
            return pic;
        }
    }
}