package com.mynimef.swiracle.fragments.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.AppLogic.FragmentChanger;
import com.mynimef.swiracle.AppLogic.Singleton;
import com.mynimef.swiracle.Interfaces.IPickImage;
import com.mynimef.swiracle.Interfaces.ISetInfo;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.NavigationFragment;

public class CreateFragment extends Fragment {
    private boolean pickStage;
    private Button back;
    private Button next;
    private IPickImage pickImage;
    private PickImageFragment pickImageFragment;
    private ISetInfo setInfo;
    private SetInfoFragment setInfoFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create, container, false);

        pickStage = true;

        pickImageFragment = new PickImageFragment();
        pickImage = (IPickImage) pickImageFragment;
        FragmentChanger.replaceFragment(getChildFragmentManager(),
                R.id.createFrameView, pickImageFragment);

        setInfoFragment = new SetInfoFragment();
        setInfo = (ISetInfo) setInfoFragment;

        back = root.findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickStage) {
                    FragmentChanger.replaceFragment(requireActivity().getSupportFragmentManager(),
                            R.id.mainFragment, new NavigationFragment());
                }
                else {
                    FragmentChanger.replaceFragment(getChildFragmentManager(),
                            R.id.createFrameView, pickImageFragment);
                    pickStage = true;
                    next.setText(R.string.next);
                }
            }
        });

        next = root.findViewById(R.id.nextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickStage) {
                    FragmentChanger.replaceFragment(getChildFragmentManager(),
                            R.id.createFrameView,
                            setInfoFragment);
                    pickStage = false;
                    next.setText(R.string.share);
                }
                else {
                    Singleton.getInstance().setPostInfo(setInfo.getTitle(),
                            setInfo.getDescription(),
                            pickImage.getPickedUri(), getContext());
                    FragmentChanger.replaceFragment(requireActivity().getSupportFragmentManager(),
                            R.id.mainFragment, new NavigationFragment());
                }
            }
        });

        return root;
    }
}