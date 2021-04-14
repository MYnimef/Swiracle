package com.mynimef.swiracle.AppLogic;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class SerializableGallery implements Serializable {
    private final ArrayList<Uri> imagesUriList;

    public SerializableGallery(ArrayList<Uri> imagesUriList) {
        this.imagesUriList = imagesUriList;
    }

    public ArrayList<Uri> getImagesUriList() {
        return imagesUriList;
    }
}