package com.mynimef.swiracle.fragments.signup.name;

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
import com.mynimef.swiracle.logic.FragmentChanger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class SetNameFragment extends FragmentApp {
    private ISignUp signUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUp = (ISignUp) getParentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup_set_name, container, false);
        EditText editName = root.findViewById(R.id.editName);
        Button nextButton = root.findViewById(R.id.nextButton);

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nextButton.setEnabled(s.length() != 0 &&
                        !String.valueOf(editName.getText()).equals(""));
            }
        });

        nextButton.setOnClickListener(v -> {
            String name = String.valueOf(editName.getText());
            if (checkName(name)) {
                signUp.setName(name);
                signUp.setStage(SignUpFragment.EStage.GENDER);
                FragmentChanger.replaceFragment(
                        getParentFragmentManager(),
                        R.id.signupFragment,
                        new SetGenderFragment()
                );
            } else {
                Toast.makeText(getContext(), "Wrong name input!", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    boolean checkName(String name) {
        Pattern p = Pattern.compile("[A-Za-zА-Яа-я][A-Za-zА-Яа-я ]*[A-Za-zА-Яа-я]");
        Matcher m = p.matcher(name);
        return m.matches();
    }
}