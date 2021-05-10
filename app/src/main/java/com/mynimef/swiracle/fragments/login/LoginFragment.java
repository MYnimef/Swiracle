package com.mynimef.swiracle.fragments.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.R;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends Fragment {
    private EditText usernameView;
    private EditText passwordView;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        usernameView = root.findViewById(R.id.usernameEditText);
        passwordView = root.findViewById(R.id.passwordEditText);

        Button login = root.findViewById(R.id.loginButton);
        login.setOnClickListener(v -> {
            String username = String.valueOf(usernameView.getText());
            String password = String.valueOf(passwordView.getText());
            if (username.isEmpty()) {
                Toast.makeText(getContext(), "Empty username!", Toast.LENGTH_LONG).show();
            } else if (password.isEmpty()) {
                Toast.makeText(getContext(), "Empty password!", Toast.LENGTH_LONG).show();
            } else {

            }
        });

        Button registration = root.findViewById(R.id.registrationButton);
        registration.setOnClickListener(v -> {

        });

        return root;
    }
}
