package com.mynimef.swiracle.fragments.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.Interfaces.IFragmentConnector;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.home.HomeFragment;

public class PostFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post, container, false);

        IFragmentConnector fc = (IFragmentConnector) getContext();

        Button back = (Button) root.findViewById(R.id.backToMainButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fc.replaceFragment(new HomeFragment());
            }
        });

        return root;
    }
}