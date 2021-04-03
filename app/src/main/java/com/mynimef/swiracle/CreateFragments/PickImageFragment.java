package com.mynimef.swiracle.CreateFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.Interfaces.IPickImage;
import com.mynimef.swiracle.R;

public class PickImageFragment extends Fragment {
    ImageView imageView;
    IPickImage pickImage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pick_image, container, false);

        pickImage = (IPickImage) getContext();

        Button get = root.findViewById(R.id.getButton);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                getActivity().startActivityForResult(photoPickerIntent, 1);
            }
        });

        imageView = root.findViewById(R.id.imageView);
        return root;
    }
}