package com.mynimef.swiracle.AppLogic;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImagesConverter {
    @TypeConverter
    public Images storedStringToImages(String value) {
        List<String> images = Arrays.asList(value.split("\\s*,\\s*"));
        return new Images((ArrayList<String>) images);
    }

    @TypeConverter
    public String imagesToStoredString(Images images) {
        String value = "";

        for (String lang :images.getImages())
            value += lang + ",";

        return value;
    }
}
