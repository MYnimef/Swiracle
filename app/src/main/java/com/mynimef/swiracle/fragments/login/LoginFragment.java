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

import androidx.activity.OnBackPressedCallback;
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
public final class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private EditText usernameView;
    private EditText passwordView;
    private Button login;
    private Button registration;
    private Button withoutAuthButton;
    private ProgressBar loading;

    private final Fragment navFragment;

    public LoginFragment(Fragment navFragment) {
        this.navFragment = navFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                moveTo(navFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        usernameView = root.findViewById(R.id.usernameEditText);
        passwordView = root.findViewById(R.id.passwordEditText);
        login = root.findViewById(R.id.loginButton);
        registration = root.findViewById(R.id.registrationButton);
        withoutAuthButton = root.findViewById(R.id.continueButton);
        loading = root.findViewById(R.id.loading);

        Handler handler  = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                changeElementsAccess(true);
                switch (msg.arg1) {
                    case 0:
                        moveTo(new NavigationFragment());
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
                changeElementsAccess(false);
                loginViewModel.loginRequest(username, password, handler);
            }
        });

        registration.setOnClickListener(v -> moveTo(new SignUpFragment()));

        withoutAuthButton.setOnClickListener(v -> {
            moveTo(navFragment);
        });

        return root;
    }

    private void changeElementsAccess(boolean access) {
        loading.setVisibility(access ? View.INVISIBLE : View.VISIBLE);
        usernameView.setEnabled(access);
        passwordView.setEnabled(access);
        login.setEnabled(access);
        registration.setEnabled(access);
        withoutAuthButton.setEnabled(access);
    }

    private void moveTo(Fragment fragment) {
        FragmentChanger.replaceFragment(
                requireActivity().getSupportFragmentManager(),
                R.id.mainFragment,
                fragment
        );
    }
}
