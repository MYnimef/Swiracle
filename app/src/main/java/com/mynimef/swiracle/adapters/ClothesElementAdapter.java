package com.mynimef.swiracle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.mynimef.swiracle.AppLogic.ClothesElement;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.pickImage.PickImageFragment;

import java.util.ArrayList;

public class ClothesElementAdapter extends ArrayAdapter<ClothesElement> {
    private int selectedId;
    private final PickImageFragment fragment;

    public ClothesElementAdapter(Context context, ArrayList<ClothesElement> clothes, PickImageFragment fragment) {
        super(context, R.layout.adapter_post, clothes);
        this.fragment = fragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ClothesElement clothes = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_image,
                    null);
        }

        return convertView;
    }
}