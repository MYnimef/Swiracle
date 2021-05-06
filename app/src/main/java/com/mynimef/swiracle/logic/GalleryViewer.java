package com.mynimef.swiracle.logic;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;

import java.util.ArrayList;

public class GalleryViewer {
    private final Application application;
    private final Handler handler;

    public GalleryViewer(Application application) {
        this.application = application;
        this.handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Repository.getInstance().setGallery(msg.getData().getParcelableArrayList("images"));
                removeCallbacksAndMessages(null);
            }
        };
        Thread th = new Thread(new GalleryRunnable());
        th.start();
    }

    private class GalleryRunnable implements Runnable {
        @Override
        public void run() { publishProgress(getImagesPath()); }

        private void publishProgress(ArrayList<Uri> images) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("images", images);
            Message message = new Message();
            message.setData(bundle);
            handler.sendMessage(message);
        }

        private ArrayList<Uri> getImagesPath() {
            Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = { MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME };
            @SuppressLint("Recycle") Cursor cursor = application.getContentResolver().query(uri,
                    projection, null,
                    null, MediaStore.Images.Media.DATE_ADDED + " DESC");

            ArrayList<Uri> uriList = new ArrayList<>();
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                Uri contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                uriList.add(contentUri);
            }
            return uriList;
        }
    }
}