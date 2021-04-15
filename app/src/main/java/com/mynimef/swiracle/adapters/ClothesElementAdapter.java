package com.mynimef.swiracle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.mynimef.swiracle.AppLogic.ClothesElement;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.create.CreateViewModel;
import com.mynimef.swiracle.fragments.pickImage.PickImageFragment;

import java.util.ArrayList;

public class ClothesElementAdapter extends ArrayAdapter<ClothesElement> {
    public ClothesElementAdapter(Context context, ArrayList<ClothesElement> clothes) {
        super(context, R.layout.adapter_clothes_element, clothes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ClothesElement clothes = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_clothes_element,
                    null);
        }

        TextView title = convertView.findViewById(R.id.elementTitle);
        title.setText(clothes.getTitle());

        TextView brand = convertView.findViewById(R.id.elementBrand);
        brand.setText(clothes.getBrand());

        TextView price = convertView.findViewById(R.id.elementPrice);
        price.setText(clothes.getPrice());

        return convertView;
    }
}