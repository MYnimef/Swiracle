package com.mynimef.swiracle.fragments.create;

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
import com.mynimef.swiracle.network.ClothesElementServer;
import com.mynimef.swiracle.network.ClothesInfoServer;
import com.mynimef.swiracle.network.ImagesServer;
import com.mynimef.swiracle.network.NetworkService;
import com.mynimef.swiracle.network.PostServer;
import com.mynimef.swiracle.network.PriceServer;

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
                if (setInfo.getTitle().isEmpty()) {
                    Toast.makeText(getContext(), R.string.toast_empty_title, Toast.LENGTH_SHORT).show();
                }
                else {
                    PostInfo postInfo = new PostInfo(String.valueOf(System.currentTimeMillis()),
                            setInfo.getTitle(),
                            setInfo.getDescription(),
                            new Images(pickImage.getPickedUri()),
                            0, 0,
                            setClothesElements.getTotalPrice());

                    List<ClothesElement> clothes = new ArrayList<>();
                    List<ClothesInfo> clothesInfoList = setClothesElements.getClothes();
                    List<ImagesServer> images = new ArrayList<>();
                    List<String> urls = setClothesElements.getUrls();
                    for (int i = 0; i < clothesInfoList.size(); i++) {
                        clothes.add(new ClothesElement(postInfo.getId(),
                                clothesInfoList.get(i),
                                urls.get(i)));
                        images.add(new ImagesServer(urls.get(i)));
                    }

                    List<ClothesElementServer> clothesList = new ArrayList<>();
                    for (int i = 0; i < clothesInfoList.size(); i++) {
                        clothesList.add(new ClothesElementServer(
                                clothes.get(i).getId(),
                                clothes.get(i).getPostId(),
                                new ClothesInfoServer(clothes.get(i).getInfo().getBrand(),
                                        clothes.get(i).getInfo().getDescription(),
                                        new PriceServer(clothes.get(i).getInfo().getPrice().getRub(), "RUB"))));
                    }

                    NetworkService.getInstance().putPost(new PostServer(postInfo.getId(),
                            postInfo.getTimeMillis(),
                            postInfo.getTitle(),
                            postInfo.getDescription(),
                            postInfo.getLikesAmount(),
                            postInfo.getCommentsAmount(),
                            clothesList,
                            new PriceServer(postInfo.getPrice().getRub(), "RUB")));

                    /*
                    for (int i = 0; i < clothesInfoList.size(); i++) {
                        NetworkService.getInstance().putClothesElement(new ClothesElementServer(
                            clothes.get(i).getId(),
                                clothes.get(i).getPostId(),
                                new ClothesInfoServer(clothes.get(i).getInfo().getBrand(),
                                        clothes.get(i).getInfo().getDescription(),
                                        new PriceServer(clothes.get(i).getInfo().getPrice().getRub(), "RUB"))));
                    }

                     */

                    createViewModel.insert(new Post(postInfo, clothes));
                    FragmentChanger.replaceFragment(requireActivity().getSupportFragmentManager(),
                            R.id.mainFragment, parentFragment);
                }
            }
        });
        return root;
    }
}