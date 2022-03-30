package com.mynimef.swiracle.fragments.signup.username;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mynimef.swiracle.Interfaces.ISignUp;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.custom.FragmentApp;
import com.mynimef.swiracle.fragments.signup.SignUpFragment;
import com.mynimef.swiracle.fragments.signup.password.SetPasswordFragment;
import com.mynimef.swiracle.logic.FragmentChanger;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class SetUsernameFragment extends FragmentApp {
    private ISignUp signUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUp = (ISignUp) getParentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup_set_username, container, false);
        EditText editUsername = root.findViewById(R.id.editUsername);
        Button nextButton = root.findViewById(R.id.nextButton);

        editUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nextButton.setEnabled(s.length() >= 3);
            }
        });

        nextButton.setOnClickListener(v -> {
            String username = String.valueOf(editUsername.getText());
            if (checkUsername(username)) {
                signUp.setUsername(username);
                signUp.setStage(SignUpFragment.EStage.PASSWORD);
                FragmentChanger.replaceFragment(getParentFragmentManager(),
                        R.id.signupFragment, new SetPasswordFragment());
            } else {
                Toast.makeText(getContext(), "Wrong username input!", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    boolean checkUsername(String username) {
        for (int i = 0; i < username.length(); i++) {
            if (!(username.charAt(i) != ' ' && (
                    username.charAt(i) >= 'a' && username.charAt(i) <= 'z' ||
                    username.charAt(i) >= 'A' && username.charAt(i) <= 'Z' ||
                    i != 0 && (username.charAt(i) >= '0' && username.charAt(i) <= '9' ||
                    username.charAt(i) == '-' || username.charAt(i) <= '_')))) {
                return false;
            }
        }
        return true;
    }
}