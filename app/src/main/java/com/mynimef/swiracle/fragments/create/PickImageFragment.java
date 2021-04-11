package com.mynimef.swiracle.fragments.create;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.Interfaces.IPickImage;
import com.mynimef.swiracle.R;

import java.util.ArrayList;

public class PickImageFragment extends Fragment implements IPickImage {
    private ImageView imageView;
    private GridView gridView;
    private View root;
    private ArrayList<Uri> imageUri;
    private ArrayList<Bitmap> imagesBitmap;
    private boolean multiple;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_pick_image, container, false);
        GalleryViewer galleryViewer = new GalleryViewer(getActivity(), this);

        imageView = root.findViewById(R.id.selectedImage);
        gridView = root.findViewById(R.id.galleryGridView);
        imagesBitmap = new ArrayList<Bitmap>();

        multiple = false;
        Button multipleButton = (Button) root.findViewById(R.id.multipleButton);
        multipleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiple = !multiple;
            }
        });

        return root;
    }

    public boolean getMultiple() {
        return multiple;
    }

    public void getImagePosition(int pos) {
        ImagePicker imagePicker = new ImagePicker(getActivity(), this,
                imageUri.get(pos));
    }

    public void addImageBitmap(Bitmap image) {
        imagesBitmap.add(image);
        setImageView(imagesBitmap.get(imagesBitmap.size() - 1));
    }

    public void deleteImageBitmap(int position) {
        imagesBitmap.remove(imagesBitmap.size() - 1);
    }

    @Override
    public ArrayList<Bitmap> getImagesBitmap() {
        return imagesBitmap;
    }

    public void setImageView(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    public void setGalleryView(SerializableGallery image) {
        imageUri = image.getImagesUriList();
        ImagePicker imagePicker = new ImagePicker(getActivity(), this,
                imageUri.get(0));
        ImageAdapter adapter = new ImageAdapter(root.getContext(), image.getImagesBitmapList(),
                this);
        gridView.setAdapter(adapter);
    }
}