package com.mynimef.swiracle.dialogs.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.login.LoginFragment;
import com.mynimef.swiracle.logic.FragmentChanger;

import org.jetbrains.annotations.NotNull;

public class LoginDialogFragment extends DialogFragment {
    private final Fragment navFragment;

    public LoginDialogFragment(Fragment navFragment) {
        this.navFragment = navFragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_login, container, false);

        Button okButton = root.findViewById(R.id.okButton);
        okButton.setOnClickListener(v -> {
            FragmentChanger.replaceFragment(
                    requireActivity().getSupportFragmentManager(),
                    R.id.mainFragment,
                    new LoginFragment(navFragment)
            );
            dismiss();
        });

        Button cancelButton = root.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> dismiss());

        return root;
    }
}