package com.mynimef.swiracle.fragments.create;

import android.graphics.Bitmap;

import java.io.Serializable;

public class SerializableImages implements Serializable {
    private final Bitmap image;

    public SerializableImages(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImageBitmap() {
        return image;
    }
}