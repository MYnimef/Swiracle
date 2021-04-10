package com.mynimef.swiracle.fragments.create;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.mynimef.swiracle.Interfaces.IFragmentConnector;
import com.mynimef.swiracle.R;

public class ImageAdapter extends ArrayAdapter<Bitmap> {
    public ImageAdapter(Context context, SerializableImage image) {
        super(context, R.layout.adapter_post, image.getImagesList());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Bitmap image = getItem(position);
        IFragmentConnector fc = (IFragmentConnector) getContext();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_image, null);
        }

        ImageView pic = (ImageView) convertView.findViewById(R.id.imageView);
        pic.setImageBitmap(image);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
}
