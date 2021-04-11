package com.mynimef.swiracle.fragments.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mynimef.swiracle.AppLogic.Post;
import com.mynimef.swiracle.AppLogic.Singleton;
import com.mynimef.swiracle.Interfaces.IFragmentConnector;
import com.mynimef.swiracle.Interfaces.IPickImage;
import com.mynimef.swiracle.Interfaces.ISetInfo;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.NavigationFragment;

public class CreateFragment extends Fragment {
    private FragmentManager fm;
    private boolean pickStage;
    private Button back;
    private Button next;
    private IFragmentConnector connector;
    private IPickImage pickImage;
    private PickImageFragment pickImageFragment;
    private ISetInfo setInfo;
    private SetInfoFragment setInfoFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create, container, false);

        fm = getChildFragmentManager();
        connector = (IFragmentConnector) getContext();
        pickStage = true;

        pickImageFragment = new PickImageFragment();
        pickImage = (IPickImage) pickImageFragment;
        replaceFragment(pickImageFragment);

        setInfoFragment = new SetInfoFragment();
        setInfo = (ISetInfo) setInfoFragment;

        back = root.findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickStage) {
                    connector.replaceFragment(new NavigationFragment());
                }
                else {
                    replaceFragment(pickImageFragment);
                    pickStage = true;
                    next.setText("Next");
                }
            }
        });

        next = root.findViewById(R.id.nextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickStage) {
                    replaceFragment(setInfoFragment);
                    pickStage = false;
                    next.setText("Share");
                }
                else {
                    Singleton list = Singleton.getInstance();
                    list.addToList(new Post(setInfo.getTitle(),
                            setInfo.getDescription(),
                            pickImage.getImagesBitmap()));
                    connector.replaceFragment(new NavigationFragment());
                }
            }
        });

        return root;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.createFrameView, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}