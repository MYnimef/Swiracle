package com.mynimef.swiracle.fragments.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mynimef.swiracle.api.ClothesInfo;
import com.mynimef.swiracle.api.FragmentChanger;
import com.mynimef.swiracle.api.Images;
import com.mynimef.swiracle.api.database.ClothesElement;
import com.mynimef.swiracle.api.database.Post;
import com.mynimef.swiracle.api.database.PostInfo;
import com.mynimef.swiracle.Interfaces.IPickImage;
import com.mynimef.swiracle.Interfaces.ISetClothesElements;
import com.mynimef.swiracle.Interfaces.ISetInfo;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.pickImage.PickImageFragment;
import com.mynimef.swiracle.fragments.setClothesElements.SetClothesElementsFragment;
import com.mynimef.swiracle.fragments.setInfo.SetInfoFragment;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateFragment extends Fragment {
    private boolean pickStage;
    private boolean setElementsStage;
    private final Fragment parentFragment;
    private PickImageFragment pickImageFragment;
    private SetClothesElementsFragment setClothesElementsFragment;
    private SetInfoFragment setInfoFragment;
    private CreateViewModel createViewModel;

    public CreateFragment(Fragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.pickImageFragment = new PickImageFragment();
        this.setClothesElementsFragment = new SetClothesElementsFragment();
        this.setInfoFragment = new SetInfoFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.createViewModel = new ViewModelProvider(this).get(CreateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create, container, false);
        this.pickStage = true;
        this.setElementsStage = false;

        FragmentChanger.replaceFragment(getChildFragmentManager(),
                R.id.createFrameView, this.pickImageFragment);

        IPickImage pickImage = this.pickImageFragment;
        ISetClothesElements setClothesElements = this.setClothesElementsFragment;
        ISetInfo setInfo = this.setInfoFragment;

        Button back = root.findViewById(R.id.backButton);
        Button next = root.findViewById(R.id.nextButton);
        this.createViewModel.setText(getResources().getString(R.string.next));

        this.createViewModel.getText().observe(getViewLifecycleOwner(), next::setText);

        back.setOnClickListener(v -> {
            if (pickStage) {
                FragmentChanger.replaceFragment(requireActivity().getSupportFragmentManager(),
                        R.id.mainFragment, parentFragment);
            }
            else if (setElementsStage) {
                FragmentChanger.replaceFragment(getChildFragmentManager(),
                    R.id.createFrameView, pickImageFragment);
                setElementsStage = false;
                pickStage = true;
            }
            else {
                FragmentChanger.replaceFragment(getChildFragmentManager(),
                        R.id.createFrameView, setClothesElementsFragment);
                setElementsStage = true;
                createViewModel.setText(getResources().getString(R.string.next));
            }
        });

        next.setOnClickListener(v -> {
            if (pickStage) {
                FragmentChanger.replaceFragment(getChildFragmentManager(),
                        R.id.createFrameView,
                        setClothesElementsFragment);
                pickStage = false;
                setElementsStage = true;

            }
            else if (setElementsStage) {
                FragmentChanger.replaceFragment(getChildFragmentManager(),
                        R.id.createFrameView,
                        setInfoFragment);
                setElementsStage = false;
                createViewModel.setText(getResources().getString(R.string.share));
            }
            else {
                PostInfo postInfo = new PostInfo(setInfo.getTitle(),
                        setInfo.getDescription(),
                        new Images(pickImage.getPickedUri()),
                        0, 0,
                        setClothesElements.getTotalPrice());

                List<ClothesElement> clothes = new ArrayList<>();
                for (ClothesInfo info: setClothesElements.getClothes()) {
                    clothes.add(new ClothesElement(postInfo.getId(), info));
                }

                createViewModel.insert(new Post(postInfo, clothes));
                FragmentChanger.replaceFragment(requireActivity().getSupportFragmentManager(),
                        R.id.mainFragment, parentFragment);
            }
        });
        return root;
    }
}