package com.mynimef.swiracle.fragments.create;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;

public class ImagePicker {
    private final Activity activity;
    private final Handler handler;

    public ImagePicker(Activity activity, PickImageFragment fragment, Uri uri) {
        this.activity = activity;

        this.handler = new Handler(Looper.getMainLooper()) {   // создание хэндлера
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SerializableImages images =
                        (SerializableImages) msg.getData().getSerializable("images");
                fragment.addImageBitmap(images.getImageBitmap());
            }
        };

        Thread th = new Thread(new PickerRunnable(uri));
        th.start();
    }

    private void publishProgress(Bitmap image) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("images", new SerializableImages(image));
        Message message = new Message();
        message.setData(bundle);
        handler.sendMessage(message);
    }

    class PickerRunnable implements Runnable {
        Uri uri;

        public PickerRunnable(Uri uri) {
            this.uri = uri;
        }

        @Override
        public void run() {
            try {
                Bitmap bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(
                        activity.getContentResolver(), uri));
                publishProgress(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
