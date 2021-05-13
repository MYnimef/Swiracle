package com.mynimef.swiracle.fragments.signup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.R;

public class SetUsernameFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup_set_username, container, false);
        EditText editUsername = root.findViewById(R.id.editUsername);
        Button registerButton = root.findViewById(R.id.registerButton);

        editUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerButton.setEnabled(s.length() >= 3);
            }
        });

        registerButton.setOnClickListener(v -> {

        });

        return root;
    }
}