package com.mynimef.swiracle.fragments.signup.set;

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
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.Interfaces.ISignUp;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.signup.SignUpFragment;
import com.mynimef.swiracle.fragments.signup.set.SetGenderFragment;
import com.mynimef.swiracle.logic.FragmentChanger;

public class SetNameFragment extends Fragment {
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
        EditText editFirstName = root.findViewById(R.id.editFirstName);
        EditText editLastName = root.findViewById(R.id.editLastName);
        Button nextButton = root.findViewById(R.id.registerButton);

        editFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nextButton.setEnabled(s.length() != 0 &&
                        !String.valueOf(editLastName.getText()).equals(""));
            }
        });
        editLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nextButton.setEnabled(s.length() != 0 &&
                        !String.valueOf(editFirstName.getText()).equals(""));
            }
        });

        nextButton.setOnClickListener(v -> {
            String firstName = String.valueOf(editFirstName.getText());
            String lastName = String.valueOf(editLastName.getText());
            if (checkName(firstName) && checkName(lastName)) {
                signUp.setName(firstName, lastName);
                signUp.setStage(SignUpFragment.EStage.GENDER);
                FragmentChanger.replaceFragment(getParentFragmentManager(),
                        R.id.signupFragment, new SetGenderFragment());
            } else {
                Toast.makeText(getContext(), "Wrong name input!", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    boolean checkName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if ((!Character.isLetter(name.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
}