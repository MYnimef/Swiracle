package com.mynimef.swiracle.api;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class UriReader {
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        String[] imageProjection = {"_data"};
        Cursor cursor = context.getContentResolver().query(contentUri,
                imageProjection,
                null, null, null);
        cursor.moveToFirst();
        int indexImage = cursor.getColumnIndex(imageProjection[0]);
        String path = cursor.getString(indexImage);
        cursor.close();
        return path;
    }
}