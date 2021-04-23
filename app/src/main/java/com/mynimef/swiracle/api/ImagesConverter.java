package com.mynimef.swiracle.api;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

public class ImagesConverter {
    @TypeConverter
    public Images storedStringToImages(String value) {
        List<String> images = Arrays.asList(value.split("\\s*,\\s*"));
        return new Images(images);
    }

    @TypeConverter
    public String imagesToStoredString(Images images) {
        StringBuilder value = new StringBuilder();

        for (String lang :images.getImages())
            value.append(lang).append(",");

        return value.toString();
    }
}
