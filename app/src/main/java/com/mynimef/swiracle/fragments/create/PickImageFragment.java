package com.mynimef.swiracle.fragments.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.R;

public class PickImageFragment extends Fragment {
    private GridView gridView;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_pick_image, container, false);
        GalleryViewer galleryViewer = new GalleryViewer(getActivity(), this);

        gridView = root.findViewById(R.id.galleryGridView);
        return root;
    }

    public void setImageView(SerializableImage image) {
        ImageAdapter adapter = new ImageAdapter(root.getContext(), image);
        gridView.setAdapter(adapter);
    }
}