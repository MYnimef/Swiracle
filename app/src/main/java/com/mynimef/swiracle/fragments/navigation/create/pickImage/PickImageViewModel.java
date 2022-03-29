package com.mynimef.swiracle.fragments.navigation.create.pickImage;

import android.app.Application;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class PickImageViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<Uri>> images;

    @Inject
    PickImageViewModel(@NonNull Application application) {
        super(application);
        this.images = new MutableLiveData<>();
        setImages();
    }

    public MutableLiveData<ArrayList<Uri>> getImages() {
        return images;
    }

    private void setImages() {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                images.setValue(msg.getData().getParcelableArrayList("images"));
                removeCallbacksAndMessages(null);
            }
        };

        new Thread(() -> {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("images", findImages());
            Message message = new Message();
            message.setData(bundle);
            handler.sendMessage(message);
        })
                .start();
    }

    private ArrayList<Uri> findImages() {
        Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        };
        Cursor cursor = getApplication().getContentResolver().query(
                uri,
                projection,
                null,
                null,
                MediaStore.Images.Media.DATE_ADDED + " DESC"
        );

        ArrayList<Uri> uriList = new ArrayList<>();
        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        while (cursor.moveToNext()) {
            long id = cursor.getLong(idColumn);
            Uri contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
            );
            uriList.add(contentUri);
        }

        cursor.close();
        return uriList;
    }
}
