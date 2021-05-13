package com.mynimef.swiracle.fragments.signup.setEmail;

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

import com.mynimef.swiracle.Interfaces.ISignUp;
import com.mynimef.swiracle.R;

public class SetEmailFragment extends Fragment {
    private ISignUp signUp;
    private Button nextButton;

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

        nextButton = root.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(v -> {

        });

        return root;
    }
}