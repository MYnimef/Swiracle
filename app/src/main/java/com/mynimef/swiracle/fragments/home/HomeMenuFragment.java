package com.mynimef.swiracle.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.AppLogic.FragmentChanger;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.messenger.MessengerFragment;

public class HomeMenuFragment extends Fragment {
    private MessengerFragment messengerFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messengerFragment = new MessengerFragment(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_menu, container, false);
        ImageButton messenger = root.findViewById(R.id.messages);
        messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentChanger.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.mainFragment, messengerFragment);
            }
        });

        return root;
    }
}
