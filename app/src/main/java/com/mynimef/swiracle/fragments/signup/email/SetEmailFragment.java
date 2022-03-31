package com.mynimef.swiracle.fragments.signup.email;

import android.os.Bundle;
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
import com.mynimef.swiracle.fragments.signup.SignUpFragment;
import com.mynimef.swiracle.fragments.signup.gender.SetGenderFragment;
import com.mynimef.swiracle.fragments.signup.name.SetNameFragment;
import com.mynimef.swiracle.logic.FragmentChanger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class SetEmailFragment extends FragmentApp {
    private ISignUp signUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUp = (ISignUp) getParentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup_set_email, container, false);
        EditText emailEdit = root.findViewById(R.id.editEmail);
        Button nextButton = root.findViewById(R.id.nextButton);

        emailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nextButton.setEnabled(s.length() != 0);
            }
        });

        nextButton.setOnClickListener(v -> {
            String email = String.valueOf(emailEdit.getText());

            if (checkEmail(email)) {
                signUp.setEmail(String.valueOf(emailEdit.getText()));
                signUp.setStage(SignUpFragment.EStage.NAME);
                FragmentChanger.replaceFragment(
                        getParentFragmentManager(),
                        R.id.signupFragment,
                        new SetNameFragment()
                );
            } else {
                Toast.makeText(getContext(), "Wrong email input!", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    boolean checkEmail(String email) {
        Pattern p = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
        Matcher m = p.matcher(email);
        return m.matches();
    }
}