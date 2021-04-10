package com.mynimef.swiracle.fragments.create;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class SerializableGallery implements Serializable {
    private final ArrayList<Bitmap> imagesBitmapList;
    private final ArrayList<Uri> imagesUriList;

    public SerializableGallery(ArrayList<Bitmap> imagesBitmapList, ArrayList<Uri> imagesUriList) {
        this.imagesBitmapList = imagesBitmapList;
        this.imagesUriList = imagesUriList;
    }

    public ArrayList<Bitmap> getImagesBitmapList() {
        return imagesBitmapList;
    }

    public ArrayList<Uri> getImagesUriList() {
        return imagesUriList;
    }
}