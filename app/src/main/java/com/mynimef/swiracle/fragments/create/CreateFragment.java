package com.mynimef.swiracle.fragments.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mynimef.swiracle.AppLogic.FragmentChanger;
import com.mynimef.swiracle.AppLogic.Singleton;
import com.mynimef.swiracle.Interfaces.IPickImage;
import com.mynimef.swiracle.Interfaces.ISetInfo;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.pickImage.PickImageFragment;
import com.mynimef.swiracle.fragments.setInfo.SetInfoFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateFragment extends Fragment {
    private boolean pickStage;
    private final Fragment parentFragment;
    private PickImageFragment pickImageFragment;
    private SetInfoFragment setInfoFragment;
    private CreateViewModel createViewModel;

    public CreateFragment(Fragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pickImageFragment = new PickImageFragment();
        setInfoFragment = new SetInfoFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        createViewModel = new ViewModelProvider(this).get(CreateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create, container, false);
        pickStage = true;
        FragmentChanger.replaceFragment(getChildFragmentManager(),
                R.id.createFrameView, pickImageFragment);

        IPickImage pickImage = (IPickImage) pickImageFragment;
        ISetInfo setInfo = (ISetInfo) setInfoFragment;

        Button back = root.findViewById(R.id.backButton);
        Button next = root.findViewById(R.id.nextButton);
        createViewModel.setText(getResources().getString(R.string.next));

        createViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                next.setText(s);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickStage) {
                    FragmentChanger.replaceFragment(requireActivity().getSupportFragmentManager(),
                            R.id.mainFragment, parentFragment);
                }
                else {
                    FragmentChanger.replaceFragment(getChildFragmentManager(),
                            R.id.createFrameView, pickImageFragment);
                    pickStage = true;
                    createViewModel.setText(getResources().getString(R.string.next));
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickStage) {
                    FragmentChanger.replaceFragment(getChildFragmentManager(),
                            R.id.createFrameView,
                            setInfoFragment);
                    pickStage = false;
                    createViewModel.setText(getResources().getString(R.string.share));
                }
                else {
                    Singleton.getInstance().setPostInfo(setInfo.getTitle(),
                            setInfo.getDescription(),
                            pickImage.getPickedUri(), getContext());
                    FragmentChanger.replaceFragment(requireActivity().getSupportFragmentManager(),
                            R.id.mainFragment, parentFragment);
                }
            }
        });

        return root;
    }
}