package com.mynimef.swiracle.fragments.create.setInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.Interfaces.ISetInfo;
import com.mynimef.swiracle.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SetInfoFragment extends Fragment implements ISetInfo {
    private EditText title;
    private EditText description;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_set_info, container, false);

        title = root.findViewById(R.id.addTitle);
        description = root.findViewById(R.id.addDescription);

        return root;
    }

    @Override
    public String getTitle() {
        return title.getText().toString();
    }

    @Override
    public String getDescription() {
        return description.getText().toString();
    }
}