package com.mynimef.swiracle.fragments.navigation.create.pickImage;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mynimef.swiracle.Interfaces.IPickImage;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.adapters.GalleryImageAdapter;
import com.mynimef.swiracle.fragments.navigation.create.CreateFragment;

import java.util.LinkedList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class PickImageFragment extends Fragment implements IPickImage {
    private PickImageViewModel pickImageViewModel;

    private ImageView imageView;
    private List<Uri> pickedUri;
    private boolean multiple;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pickImageViewModel = new ViewModelProvider(this).get(PickImageViewModel.class);

        pickedUri = new LinkedList<>();
        multiple = false;
    }

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View root = inflater.inflate(
                R.layout.fragment_create_pick_image,
                container,
                false
        );

        ProgressBar loadingProgress = root.findViewById(R.id.loadingProgress);
        TextView noImagesText = root.findViewById(R.id.noImagesText);
        Button multipleButton = root.findViewById(R.id.multipleButton);
        imageView = root.findViewById(R.id.selectedImage);
        RecyclerView rv = root.findViewById(R.id.galleryRecyclerView);

        rv.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 4);
        mLayoutManager.scrollToPosition(0);
        rv.setLayoutManager(mLayoutManager);

        pickImageViewModel.getImages().observe(getViewLifecycleOwner(), images -> {
            loadingProgress.setVisibility(View.GONE);

            if (images.isEmpty()) {
                noImagesText.setVisibility(View.VISIBLE);
            } else {
                CreateFragment fragment = (CreateFragment) getParentFragment();
                if (fragment != null) {
                    fragment.makeNextVisible();
                }

                multipleButton.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                rv.setVisibility(View.VISIBLE);

                GalleryImageAdapter adapter = new GalleryImageAdapter(images, this);
                rv.setAdapter(adapter);

                Uri firstImage = images.get(0);
                setImageView(firstImage);
                addToPicked(firstImage);

                multipleButton.setOnClickListener(v -> {
                    if (multiple) {
                        pickedUri.clear();

                        Uri lastImage = adapter.clearPicked();
                        setImageView(lastImage);
                        addToPicked(lastImage);

                        adapter.notifyDataSetChanged();
                    }
                    multiple = !multiple;
                });
            }
        });

        return root;
    }

    public boolean getMultiple() {
        return multiple;
    }

    public void setImageView(Uri image) {
        Glide.with(this)
                .load(image)
                .thumbnail(0.05f)
                .centerCrop()
                .skipMemoryCache(true)
                .into(imageView);
    }

    public void addToPicked(Uri image) {
        pickedUri.add(image);
    }

    public void removeFromPicked(Uri image) {
        pickedUri.remove(image);
    }

    @Override
    public List<Uri> getPickedUri() {
        return pickedUri;
    }
}