package com.mynimef.swiracle.ui.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.FragmentConnector;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.ui.home.HomeFragment;

public class PostFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post, container, false);

        FragmentConnector fc = (FragmentConnector) getContext();

        Button back = (Button) root.findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fc.replaceFragment(new HomeFragment());
            }
        });

        return root;
    }
}