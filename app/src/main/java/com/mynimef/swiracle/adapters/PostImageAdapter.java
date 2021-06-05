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

public final class PostImageAdapter extends RecyclerView.Adapter<PostImageAdapter.PostImageView> {
    private final List<PostImage> images;
    private final String id;
    private final Fragment homeFragment;

    public PostImageAdapter(List<PostImage> images, String id, Fragment homeFragment) {
        this.images = images;
        this.id = id;
        this.homeFragment = homeFragment;
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
                .with(homeFragment)
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
                    FragmentChanger.replaceFragmentAnim(homeFragment.getParentFragmentManager(),
                            R.id.nav_host_fragment, new PostFragment(id, num));
                }
            });
        }

        public ImageView getPic() {
            return pic;
        }
    }
}