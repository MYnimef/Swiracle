package com.mynimef.swiracle.fragments.signup.setName;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.R;

public class SetNameFragment extends Fragment {
    private EditText editFirstName;
    private EditText editLastName;
    private Button nextButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup_set_name, container, false);

        editFirstName = root.findViewById(R.id.editFirstName);
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

        editLastName = root.findViewById(R.id.editLastName);
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

        nextButton = root.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(v -> {

        });

        return root;
    }
}