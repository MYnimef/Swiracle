package com.mynimef.swiracle.fragments.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.navigation.NavigationFragment;
import com.mynimef.swiracle.fragments.signup.SignUpFragment;
import com.mynimef.swiracle.logic.FragmentChanger;

import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        EditText usernameView = root.findViewById(R.id.usernameEditText);
        EditText passwordView = root.findViewById(R.id.passwordEditText);
        Button login = root.findViewById(R.id.loginButton);
        Button registration = root.findViewById(R.id.registrationButton);
        Button withoutAuthButton = root.findViewById(R.id.continueButton);
        ProgressBar loading = root.findViewById(R.id.loading);

        Handler handler  = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                loading.setVisibility(View.INVISIBLE);
                usernameView.setEnabled(true);
                passwordView.setEnabled(true);
                login.setEnabled(true);
                registration.setEnabled(true);
                withoutAuthButton.setEnabled(true);

                byte result = msg.getData().getByte("login");
                switch (result) {
                    case 0:
                        loginViewModel.setSignedIn(1);
                        FragmentChanger.replaceFragment(requireActivity()
                                        .getSupportFragmentManager(),
                                R.id.mainFragment, new NavigationFragment());
                        break;
                    case 1:
                        Toast.makeText(getContext(), "Wrong username or password!",
                                Toast.LENGTH_LONG).show();
                        break;
                    case -1:
                        Toast.makeText(getContext(), "No connection",
                                Toast.LENGTH_LONG).show();
                        break;
                }
                removeCallbacksAndMessages(null);
            }
        };

        login.setOnClickListener(v -> {
            String username = String.valueOf(usernameView.getText());
            String password = String.valueOf(passwordView.getText());
            if (username.equals("")) {
                Toast.makeText(getContext(), "Empty username!", Toast.LENGTH_LONG).show();
            } else if (password.equals("")) {
                Toast.makeText(getContext(), "Empty password!", Toast.LENGTH_LONG).show();
            } else {
                loading.setVisibility(View.VISIBLE);
                usernameView.setEnabled(false);
                passwordView.setEnabled(false);
                login.setEnabled(false);
                registration.setEnabled(false);
                withoutAuthButton.setEnabled(false);
                loginViewModel.loginRequest(username, password, handler);
            }
        });

        registration.setOnClickListener(v -> {
            FragmentChanger.replaceFragment(requireActivity()
                            .getSupportFragmentManager(),
                    R.id.mainFragment, new SignUpFragment());
        });

        withoutAuthButton.setOnClickListener(v -> {
            loginViewModel.setSignedIn(-1);
            FragmentChanger.replaceFragment(requireActivity()
                            .getSupportFragmentManager(),
                    R.id.mainFragment, new NavigationFragment());
        });

        return root;
    }
}
