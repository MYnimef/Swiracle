package com.mynimef.swiracle.fragments.signup.password;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mynimef.swiracle.Interfaces.ISignUp;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.custom.FragmentApp;
import com.mynimef.swiracle.fragments.navigation.NavigationFragment;
import com.mynimef.swiracle.logic.FragmentChanger;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class SetPasswordFragment extends FragmentApp {
    private ISignUp signUp;
    EditText editPassword;
    EditText editPasswordAgain;
    Button finishButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUp = (ISignUp) getParentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup_set_password, container, false);
        editPassword = root.findViewById(R.id.editPassword);
        editPasswordAgain = root.findViewById(R.id.editPasswordAgain);
        finishButton = root.findViewById(R.id.finishButton);

        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                finishButton.setEnabled(s.length() >= 8 &&
                        s.length() == String.valueOf(editPasswordAgain.getText()).length());
            }
        });
        editPasswordAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                finishButton.setEnabled(s.length() >= 8 &&
                        s.length() == String.valueOf(editPassword.getText()).length());
            }
        });

        Handler signUpHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int result = msg.arg1;
                if (result == 0) {
                    FragmentChanger.replaceFragment(
                            requireActivity().getSupportFragmentManager(),
                            R.id.mainFragment,
                            new NavigationFragment()
                    );
                } else if (result == 1) {
                    Toast.makeText(
                            getContext(),
                            "Error creating account",
                            Toast.LENGTH_SHORT
                    )
                            .show();
                } else if (result == -1) {
                    Toast.makeText(
                            getContext(),
                            "No connection",
                            Toast.LENGTH_SHORT
                    )
                            .show();
                }
                changeElementsAccess(true);
                removeCallbacksAndMessages(null);
            }
        };

        finishButton.setOnClickListener(v -> {
            String password = String.valueOf(editPassword.getText());
            String passwordAgain = String.valueOf(editPasswordAgain.getText());
            if (password.equals(passwordAgain)) {
                if (checkPassword(password)) {
                    changeElementsAccess(false);
                    signUp.setPassword(password);
                    signUp.completeRegistration(signUpHandler);
                } else {
                    Toast.makeText(getContext(), "Wrong input type!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Passwords doesn't match", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void changeElementsAccess(boolean access) {
        editPassword.setEnabled(access);
        editPasswordAgain.setEnabled(access);
        finishButton.setEnabled(access);
    }

    private boolean checkPassword(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (!(password.charAt(i) != ' ' && (
                    password.charAt(i) >= 'a' && password.charAt(i) <= 'z' ||
                            password.charAt(i) >= 'A' && password.charAt(i) <= 'Z' ||
                            i != 0 && (password.charAt(i) >= '0' && password.charAt(i) <= '9' ||
                                    password.charAt(i) == '-' || password.charAt(i) <= '_')))) {
                return false;
            }
        }
        return true;
    }
}