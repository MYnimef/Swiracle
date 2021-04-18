package com.mynimef.swiracle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.pickImage.PickImageFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.GalleryView> {
    private final ArrayList<String> imagesList;
    private int selectedId;
    private final boolean[] selectedField;
    private final PickImageFragment fragment;

    public ImageAdapter(ArrayList<String> imagesList, PickImageFragment fragment) {
        this.imagesList = imagesList;
        this.fragment = fragment;
        this.selectedField = new boolean[imagesList.size()];
        this.selectedId = 0;
        this.selectedField[selectedId] = true;
    }

    @NotNull
    @Override
    public GalleryView onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_image, viewGroup, false);

        return new GalleryView(view);
    }

    @Override
    public void onBindViewHolder(GalleryView galleryView, final int position) {
        String image = imagesList.get(position);
        ImageView pic = galleryView.getPic();

        Glide
                .with(fragment)
                .load(image)
                .thumbnail(0.001f)
                .into(pic);

        pic.setOnClickListener(v -> {
            if (!selectedField[position]) {
                selectedField[position] = true;
                fragment.setImageView(position);
                fragment.addToPicked(position);
                if (!fragment.getMultiple()) {
                    selectedField[selectedId] = false;
                    fragment.removeFromPicked(selectedId);
                    selectedId = position;
                    notifyDataSetChanged();
                }
            } else {
                selectedField[position] = false;
            }
        });

        if (selectedField[position]) {
            pic.setForeground(ContextCompat.getDrawable(fragment.requireContext(),
                    R.drawable.foreground_image));
        } else {
            pic.setForeground(null);
        }
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    static class GalleryView extends RecyclerView.ViewHolder {
        private final ImageView pic;

        public GalleryView(View view) {
            super(view);
            pic = view.findViewById(R.id.imageView);
        }

        public ImageView getPic() {
            return pic;
        }
    }
}