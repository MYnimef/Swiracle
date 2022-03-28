package com.mynimef.swiracle.fragments.navigation.create;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mynimef.swiracle.logic.FragmentChanger;
import com.mynimef.swiracle.Interfaces.IPickImage;
import com.mynimef.swiracle.Interfaces.ISetClothesElements;
import com.mynimef.swiracle.Interfaces.ISetInfo;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.logic.UriReader;
import com.mynimef.swiracle.fragments.navigation.create.pickImage.PickImageFragment;
import com.mynimef.swiracle.fragments.navigation.create.setClothesElements.SetClothesElementsFragment;
import com.mynimef.swiracle.fragments.navigation.create.setInfo.SetInfoFragment;
import com.mynimef.swiracle.models.PostServer;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class CreateFragment extends Fragment {
    private boolean pickStage;
    private boolean setElementsStage;
    private PickImageFragment pickImageFragment;
    private SetClothesElementsFragment setClothesElementsFragment;
    private SetInfoFragment setInfoFragment;
    private CreateViewModel createViewModel;

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
                R.id.createFragment, this.pickImageFragment);

        IPickImage pickImage = this.pickImageFragment;
        ISetClothesElements setClothesElements = this.setClothesElementsFragment;
        ISetInfo setInfo = this.setInfoFragment;

        Button back = root.findViewById(R.id.backButton);
        Button next = root.findViewById(R.id.nextButton);
        this.createViewModel.setText(getResources().getString(R.string.next));

        this.createViewModel.getText().observe(getViewLifecycleOwner(), next::setText);

        back.setOnClickListener(v -> {
            if (pickStage) {
                requireActivity().getSupportFragmentManager().popBackStackImmediate();
            }
            else if (setElementsStage) {
                FragmentChanger.replaceFragment(getChildFragmentManager(),
                    R.id.createFragment, pickImageFragment);
                setElementsStage = false;
                pickStage = true;
            }
            else {
                FragmentChanger.replaceFragment(getChildFragmentManager(),
                        R.id.createFragment, setClothesElementsFragment);
                setElementsStage = true;
                createViewModel.setText(getResources().getString(R.string.next));
            }
        });

        next.setOnClickListener(v -> {
            if (pickStage) {
                FragmentChanger.replaceFragment(getChildFragmentManager(),
                        R.id.createFragment,
                        setClothesElementsFragment);
                pickStage = false;
                setElementsStage = true;

            }
            else if (setElementsStage) {
                FragmentChanger.replaceFragment(getChildFragmentManager(),
                        R.id.createFragment,
                        setInfoFragment);
                setElementsStage = false;
                createViewModel.setText(getResources().getString(R.string.share));
            }
            else {
                if (setInfo.getTitle().isEmpty()) {
                    Toast.makeText(getContext(), R.string.toast_empty_title, Toast.LENGTH_SHORT).show();
                }
                else {
                    List<String> absolutePath = new ArrayList<>();
                    for (Uri uri : pickImage.getPickedUri()) {
                        absolutePath.add(UriReader.getRealPathFromUri(requireContext(), uri));
                    }

                    createViewModel.uploadPost(new PostServer(setInfo.getTitle(),
                            setInfo.getDescription(),
                            setClothesElements.getInfoList()), absolutePath);

                    requireActivity().getSupportFragmentManager().popBackStackImmediate();
                }
            }
        });
        return root;
    }
}