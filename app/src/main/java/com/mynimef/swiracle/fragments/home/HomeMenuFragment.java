package com.mynimef.swiracle.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mynimef.swiracle.dialogs.login.LoginDialogFragment;
import com.mynimef.swiracle.logic.FragmentChanger;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.messenger.MessengerFragment;
import com.mynimef.swiracle.fragments.search.SearchFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeMenuFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private MessengerFragment messengerFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        messengerFragment = new MessengerFragment(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_menu, container, false);

        ImageButton search = root.findViewById(R.id.search);
        search.setOnClickListener(v -> FragmentChanger
                .replaceFragment(getParentFragmentManager(),
                        R.id.nav_host_fragment, new SearchFragment()));

        Button following = root.findViewById(R.id.following);
        Button forYou = root.findViewById(R.id.forYou);

        forYou.setSelected(true);
        following.setOnClickListener(v -> {
            if (homeViewModel.getSignedIn() != 1) {
                new LoginDialogFragment().show(getChildFragmentManager(), "ASK");
            } else {
                forYou.setSelected(false);
                following.setSelected(true);
            }
        });
        forYou.setOnClickListener(v -> {
            following.setSelected(false);
            forYou.setSelected(true);
        });

        ImageButton messenger = root.findViewById(R.id.messages);
        messenger.setOnClickListener(v -> {
            if (homeViewModel.getSignedIn() != 1) {
                new LoginDialogFragment().show(getChildFragmentManager(), "ASK");
            } else {
                FragmentChanger.replaceFragment(requireActivity().getSupportFragmentManager(),
                        R.id.mainFragment, messengerFragment);
            }
        });

        return root;
    }
}
