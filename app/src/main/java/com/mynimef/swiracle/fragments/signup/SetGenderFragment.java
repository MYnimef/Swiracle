package com.mynimef.swiracle.fragments.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.Interfaces.ISignUp;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.logic.FragmentChanger;

public class SetGenderFragment extends Fragment {
    private ISignUp signUp;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUp = (ISignUp) getParentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup_set_gender, container, false);
        CheckBox checkMale = root.findViewById(R.id.checkMale);
        CheckBox checkFemale = root.findViewById(R.id.checkFemale);
        CheckBox checkOther = root.findViewById(R.id.checkOther);
        Button nextButton = root.findViewById(R.id.registerButton);

        checkMale.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkFemale.setChecked(false);
                checkOther.setChecked(false);
                nextButton.setEnabled(true);
            } else {
                nextButton.setEnabled(false);
            }
        });
        checkFemale.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkMale.setChecked(false);
                checkOther.setChecked(false);
                nextButton.setEnabled(true);
            } else {
                nextButton.setEnabled(false);
            }
        });
        checkOther.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkMale.setChecked(false);
                checkFemale.setChecked(false);
                nextButton.setEnabled(true);
            } else {
                nextButton.setEnabled(false);
            }
        });

        nextButton.setOnClickListener(v -> {
            if (checkMale.isChecked()) {
                signUp.setGender(0);
            } else if (checkFemale.isChecked()) {
                signUp.setGender(1);
            } else {
                signUp.setGender(2);
            }
            signUp.setStage(SignUpFragment.EStage.USERNAME);
            FragmentChanger.replaceFragment(getParentFragmentManager(),
                    R.id.signupFragment, new SetUsernameFragment());
        });

        return root;
    }
}