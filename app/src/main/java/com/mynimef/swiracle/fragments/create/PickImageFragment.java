package com.mynimef.swiracle.fragments.create;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mynimef.swiracle.AppLogic.Singleton;
import com.mynimef.swiracle.Interfaces.IPickImage;
import com.mynimef.swiracle.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PickImageFragment extends Fragment implements IPickImage {
    private ImageView imageView;
    private ArrayList<Uri> imageUri;
    private HashMap<Integer, Uri> pickedUri;
    private boolean multiple;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pick_image, container, false);
        imageUri = Singleton.getInstance().getGallery().getImagesUriList();
        pickedUri = new HashMap<Integer, Uri>();
        addToPicked(0);

        multiple = false;
        Button multipleButton = (Button) root.findViewById(R.id.multipleButton);
        multipleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //multiple = !multiple;
            }
        });

        imageView = root.findViewById(R.id.selectedImage);
        setImageView(0);

        GridView gridView = root.findViewById(R.id.galleryGridView);
        ImageAdapter adapter = new ImageAdapter(root.getContext(), imageUri,
                this);
        gridView.setAdapter(adapter);

        return root;
    }

    public boolean getMultiple() {
        return multiple;
    }

    public void setImageView(int pos) {
        Glide.with(this).load(imageUri.get(pos)).into(imageView);
    }

    public void addToPicked(int pos) {
        pickedUri.put(pos, imageUri.get(pos));
    }

    public void removeFromPicked(int pos) {
        pickedUri.remove(pos);
    }

    @Override
    public ArrayList<Uri> getPickedUri() {
        ArrayList<Uri> result = new ArrayList<Uri>();
        Set<Integer> key = pickedUri.keySet();
        for (int i : key) {
            result.add(pickedUri.get(i));
        }
        return result;
    }
}