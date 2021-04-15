package com.mynimef.swiracle.fragments.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.mynimef.swiracle.AppLogic.Singleton;
import com.mynimef.swiracle.Interfaces.IPickImage;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.adapters.ImageAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PickImageFragment extends Fragment implements IPickImage {
    private ImageView imageView;
    private ArrayList<String> imageUri;
    private HashMap<Integer, String> pickedUri;
    private boolean multiple;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pick_image, container, false);
        imageUri = Singleton.getInstance().getGallery();
        pickedUri = new HashMap<>();

        multiple = false;
        Button multipleButton = (Button) root.findViewById(R.id.multipleButton);
        multipleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //multiple = !multiple;
            }
        });

        imageView = root.findViewById(R.id.selectedImage);

        if (!imageUri.isEmpty()) {
            setImageView(0);
            addToPicked(0);
            GridView gridView = root.findViewById(R.id.galleryGridView);
            ImageAdapter adapter = new ImageAdapter(root.getContext(), imageUri,
                    this);
            gridView.setAdapter(adapter);
        }

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
    public ArrayList<String> getPickedUri() {
        ArrayList<String> result = new ArrayList<>();
        Set<Integer> key = pickedUri.keySet();
        for (int i : key) {
            result.add(pickedUri.get(i));
        }
        return result;
    }
}