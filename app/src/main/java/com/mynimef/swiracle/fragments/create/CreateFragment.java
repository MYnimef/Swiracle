package com.mynimef.swiracle.fragments.create;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mynimef.swiracle.Interfaces.IFragmentConnector;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.NavigationFragment;

import java.util.ArrayList;

public class CreateFragment extends Fragment {
    private FragmentManager fm;
    private ArrayList<Bitmap> images;
    private boolean pickStage;
    private Button back, next;
    private IFragmentConnector connector;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create, container, false);

        fm = getChildFragmentManager();
        connector = (IFragmentConnector) getContext();
        pickStage = true;

        back = root.findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickStage) {
                    connector.replaceFragment(new NavigationFragment());
                }
                else {
                    pickStage = true;
                }
            }
        });

        next = root.findViewById(R.id.nextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickStage) {
                    replaceFragment(new SetInfoFragment());
                    pickStage = false;
                }
                else {

                }
            }
        });

        images = new ArrayList<>();
        return root;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.createFragment, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}