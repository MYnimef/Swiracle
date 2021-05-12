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
import com.mynimef.swiracle.fragments.NavigationFragment;
import com.mynimef.swiracle.logic.FragmentChanger;

import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private EditText usernameView;
    private EditText passwordView;
    private ProgressBar loading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        Handler handler  = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                loading.setVisibility(View.INVISIBLE);
                String result = msg.getData().getString("result");
                switch (result) {
                    case "success":
                        loginViewModel.setSignedIn(1);
                        FragmentChanger.replaceFragment(requireActivity()
                                        .getSupportFragmentManager(),
                                R.id.mainFragment, new NavigationFragment());
                        break;
                    case "failure":
                        Toast.makeText(getContext(), "Wrong username or password!",
                                Toast.LENGTH_LONG).show();
                        break;
                    case "no connection":
                        Toast.makeText(getContext(), "No connection",
                                Toast.LENGTH_LONG).show();
                        break;
                }
                removeCallbacksAndMessages(null);
            }
        };

        usernameView = root.findViewById(R.id.usernameEditText);
        passwordView = root.findViewById(R.id.passwordEditText);

        Button login = root.findViewById(R.id.loginButton);
        login.setOnClickListener(v -> {
            String username = String.valueOf(usernameView.getText());
            String password = String.valueOf(passwordView.getText());
            if (username.equals("")) {
                Toast.makeText(getContext(), "Empty username!", Toast.LENGTH_LONG).show();
            } else if (password.equals("")) {
                Toast.makeText(getContext(), "Empty password!", Toast.LENGTH_LONG).show();
            } else {
                loading.setVisibility(View.VISIBLE);
                loginViewModel.loginRequest(username, password, handler);
            }
        });

        Button registration = root.findViewById(R.id.registrationButton);
        registration.setOnClickListener(v -> {

        });

        loading = root.findViewById(R.id.loading);

        Button withoutAuthButton = root.findViewById(R.id.continueButton);
        withoutAuthButton.setOnClickListener(v -> {
            loginViewModel.setSignedIn(-1);
            FragmentChanger.replaceFragment(requireActivity()
                            .getSupportFragmentManager(),
                    R.id.mainFragment, new NavigationFragment());
        });

        return root;
    }
}
