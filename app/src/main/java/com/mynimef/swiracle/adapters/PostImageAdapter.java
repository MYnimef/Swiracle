package com.mynimef.swiracle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mynimef.swiracle.logic.FragmentChanger;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.database.PostImage;
import com.mynimef.swiracle.fragments.post.PostFragment;
import com.mynimef.swiracle.fragments.post.PostMenuFragment;

import java.util.List;

public class PostImageAdapter extends RecyclerView.Adapter<PostImageAdapter.PostImageView> {
    private final List<PostImage> images;
    private final int pos;
    private final Fragment fragment;

    public PostImageAdapter(List<PostImage> images, int pos, Fragment fragment) {
        this.images = images;
        this.pos = pos;
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
                .load(image.getUrl())
                .into(holder.getPic());
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class PostImageView extends RecyclerView.ViewHolder {
        private final ImageView pic;

        public PostImageView(@NonNull View view) {
            super(view);
            pic = view.findViewById(R.id.imageView);
            pic.setOnClickListener(v -> {
                if (pos >= 0) {
                    int num = getBindingAdapterPosition();
                    FragmentChanger.replaceFragment(fragment.getChildFragmentManager(),
                            R.id.nav_host_fragment, new PostFragment(pos, num));
                    FragmentChanger.replaceFragment(fragment.getChildFragmentManager(),
                            R.id.up_menu_fragment, new PostMenuFragment(fragment));
                }
            });
        }

        public ImageView getPic() {
            return pic;
        }
    }
}