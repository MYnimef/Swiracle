package com.mynimef.swiracle.fragments.create;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.mynimef.swiracle.R;

public class ImageAdapter extends ArrayAdapter<Bitmap> {
    private boolean[] selected;

    public ImageAdapter(Context context, SerializableGallery image) {
        super(context, R.layout.adapter_post, image.getImagesBitmapList());
        selected = new boolean[image.getImagesBitmapList().size()];

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Bitmap image = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_image, null);
        }

        ImageView pic = (ImageView) convertView.findViewById(R.id.imageView);
        pic.setImageBitmap(image);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selected[position]) {
                    selected[position] = true;
                    pic.setForeground(ContextCompat.getDrawable(getContext(),
                            R.drawable.foreground_image));
                } else {

                }

            }
        });

        return convertView;
    }
}
