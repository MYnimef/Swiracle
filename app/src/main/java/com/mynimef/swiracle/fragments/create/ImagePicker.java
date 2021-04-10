package com.mynimef.swiracle.fragments.create;

import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;

import java.util.ArrayList;

public class ImagePicker {
    private final Activity activity;
    private final Handler handler;

    public ImagePicker(Activity activity, PickImageFragment fragment) {
        this.activity = activity;

        this.handler = new Handler(Looper.getMainLooper()) {   // создание хэндлера
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                fragment.setImageView((SerializableImage) msg.getData().getSerializable("images"));
            }
        };
    }

    public void startPicker() {
        Thread th = new Thread(new PickerRunnable());
        th.start();
    }

    private void publishProgress(ArrayList<Bitmap> images) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("images", new SerializableImage(images));
        Message message = new Message();
        message.setData(bundle);
        handler.sendMessage(message);
    }

    class PickerRunnable implements Runnable {
        @Override
        public void run() {
            ArrayList<Uri> imagesBitmapList = getImagesPath();
            //ArrayList<Bitmap> imagesBitmapList = getImagesPath();
            //publishProgress(imagesBitmapList);
        }
    }

    private ArrayList<Uri> getImagesPath() {
        Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;;
        String[] projection = { MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };
        Cursor cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        ArrayList<Uri> listOfAllImages = new ArrayList<Uri>();
        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        while (cursor.moveToNext()) {
            long id = cursor.getLong(idColumn);
            Uri contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
            listOfAllImages.add(contentUri);
        }

        return listOfAllImages;
    }

}
