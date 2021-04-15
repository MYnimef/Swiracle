package com.mynimef.swiracle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.pickImage.PickImageFragment;

import java.util.ArrayList;

public class ImageAdapter extends ArrayAdapter<String> {
    private int selectedId;
    private final boolean[] selectedField;
    private final PickImageFragment fragment;

    public ImageAdapter(Context context, ArrayList<String> images, PickImageFragment fragment) {
        super(context, R.layout.adapter_post, images);
        selectedField = new boolean[images.size()];
        selectedId = 0;
        selectedField[selectedId] = true;
        this.fragment = fragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String uri = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_image,
                    null);
        }

        ImageView pic = (ImageView) convertView.findViewById(R.id.imageView);
        Glide
                .with(fragment)
                .load(uri)
                .into(pic);

        if (selectedField[position]) {
            pic.setForeground(ContextCompat.getDrawable(getContext(),
                    R.drawable.foreground_image));
        } else {
            pic.setForeground(null);
        }

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedField[position]) {
                    selectedField[position] = true;
                    pic.setForeground(ContextCompat.getDrawable(getContext(),
                            R.drawable.foreground_image));
                    fragment.setImageView(position);
                    fragment.addToPicked(position);
                    if (!fragment.getMultiple()) {
                        selectedField[selectedId] = false;
                        fragment.removeFromPicked(selectedId);
                        selectedId = position;
                        notifyDataSetChanged();
                    }
                } else {
                    selectedField[position] = false;
                    pic.setForeground(null);
                }
            }
        });

        return convertView;
    }
}
