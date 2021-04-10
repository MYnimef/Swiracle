package com.mynimef.swiracle.fragments.create;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

public class SerializableImage implements Serializable {
    private ArrayList<Bitmap> imagesList;

    public SerializableImage(ArrayList<Bitmap> imagesList) {
        this.imagesList = imagesList;
    }

    public ArrayList<Bitmap> getImagesList() {
        return imagesList;
    }
}