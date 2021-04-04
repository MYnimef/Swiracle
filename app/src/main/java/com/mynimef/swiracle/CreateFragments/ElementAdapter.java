package com.mynimef.swiracle.CreateFragments;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.mynimef.swiracle.R;

public class ElementAdapter extends ArrayAdapter<String[]> {
    public ElementAdapter(@NonNull Context context) {
        super(context, R.layout.adapter_element);
    }
}