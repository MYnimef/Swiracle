package com.mynimef.swiracle.fragments.navigation.create.pickImage;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mynimef.swiracle.logic.Repository;
import com.mynimef.swiracle.Interfaces.IPickImage;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.adapters.GalleryImageAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class PickImageFragment extends Fragment implements IPickImage {
    private ImageView imageView;
    private List<Uri> imageUri;
    private HashMap<Integer, Uri> pickedUri;
    private boolean multiple;
    private GalleryImageAdapter adapter;
    private int lastPicked;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageUri = Repository.getInstance().getGallery();
        pickedUri = new HashMap<>();
        multiple = false;
        adapter = new GalleryImageAdapter(imageUri, this);

        lastPicked = 0;
        addToPicked(lastPicked);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_pick_image, container, false);

        Button multipleButton = root.findViewById(R.id.multipleButton);
        multipleButton.setOnClickListener(v -> {
            if (multiple) {
                lastPicked = adapter.clearPicked();
                pickedUri = new HashMap<>();
                setImageView(lastPicked);
                addToPicked(lastPicked);
                adapter.notifyDataSetChanged();
            }
            multiple = !multiple;
        });

        imageView = root.findViewById(R.id.selectedImage);

        setImageView(lastPicked);
        RecyclerView rv = root.findViewById(R.id.galleryRecyclerView);
        rv.setHasFixedSize(true);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 4);
        mLayoutManager.scrollToPosition(0);
        rv.setLayoutManager(mLayoutManager);

        rv.setAdapter(adapter);

        return root;
    }

    public boolean getMultiple() {
        return multiple;
    }

    public void setImageView(int pos) {
        lastPicked = pos;
        Glide.with(this)
                .load(imageUri.get(pos))
                .thumbnail(0.05f)
                .centerCrop()
                .skipMemoryCache(true)
                .into(imageView);
    }

    public void addToPicked(int pos) {
        pickedUri.put(pos, imageUri.get(pos));
    }

    public void removeFromPicked(int pos) {
        pickedUri.remove(pos);
    }

    @Override
    public List<Uri> getPickedUri() {
        List<Uri> result = new ArrayList<>();
        Set<Integer> key = pickedUri.keySet();
        for (int i : key) {
            result.add(pickedUri.get(i));
        }
        return result;
    }
}