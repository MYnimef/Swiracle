package com.mynimef.swiracle.fragments.login;

import android.os.Bundle;
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
import com.mynimef.swiracle.fragments.signup.SignUpFragment;
import com.mynimef.swiracle.logic.FragmentChanger;

import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class LoginFragment extends Fragment {
    private LoginViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getSupportFragmentManager().popBackStackImmediate();
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

        EditText usernameView = root.findViewById(R.id.usernameEditText);
        EditText passwordView = root.findViewById(R.id.passwordEditText);
        Button login = root.findViewById(R.id.loginButton);
        Button registration = root.findViewById(R.id.registrationButton);
        Button withoutAuthButton = root.findViewById(R.id.continueButton);
        ProgressBar loading = root.findViewById(R.id.loading);

        login.setOnClickListener(v -> viewModel.loginRequest(
                String.valueOf(usernameView.getText()),
                String.valueOf(passwordView.getText())
        ));

        registration.setOnClickListener(v -> FragmentChanger.replaceFragment(
                requireActivity().getSupportFragmentManager(),
                R.id.mainFragment,
                new SignUpFragment()
        ));

        withoutAuthButton.setOnClickListener(
                v -> requireActivity().getSupportFragmentManager().popBackStackImmediate()
        );

        viewModel.getElementAccess().observe(getViewLifecycleOwner(), access -> {
            loading.setVisibility(access ? View.INVISIBLE : View.VISIBLE);
            usernameView.setEnabled(access);
            passwordView.setEnabled(access);
            login.setEnabled(access);
            registration.setEnabled(access);
            withoutAuthButton.setEnabled(access);
        });

        viewModel.getErrorMessage().observe(
                getViewLifecycleOwner(),
                message -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show()
        );

        viewModel.getFragment().observe(
                getViewLifecycleOwner(),
                fragment -> FragmentChanger.replaceFragment(
                        requireActivity().getSupportFragmentManager(),
                        R.id.mainFragment,
                        fragment
                )
        );

        return root;
    }
}
