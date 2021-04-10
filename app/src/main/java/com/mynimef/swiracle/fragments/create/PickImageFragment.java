package com.mynimef.swiracle.fragments.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.R;

public class PickImageFragment extends Fragment {
    private ImageView imageView;
    private GridView gridView;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_pick_image, container, false);
        GalleryViewer galleryViewer = new GalleryViewer(getActivity(), this);

        imageView = root.findViewById(R.id.selectedImage);
        gridView = root.findViewById(R.id.galleryGridView);
        return root;
    }

    public void setImageView(SerializableImages image) {
        imageView.setImageBitmap(image.getImageBitmap());
    }

    public void setGalleryView(SerializableGallery image) {
        ImagePicker imagePicker = new ImagePicker(getActivity(), this, image.getImagesUriList().get(0));
        ImageAdapter adapter = new ImageAdapter(root.getContext(), image);
        gridView.setAdapter(adapter);
    }
}