package com.mynimef.swiracle.fragments.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.Interfaces.ISignUp;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.login.LoginFragment;
import com.mynimef.swiracle.fragments.signup.setBirthday.SetBirthdayFragment;
import com.mynimef.swiracle.logic.FragmentChanger;


public class SignUpFragment extends Fragment implements ISignUp {
    public enum EStage {
        BIRTHDAY,
        EMAIL,
        NAME,
        USERNAME
    }
    private EStage stage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stage = EStage.BIRTHDAY;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        FragmentChanger.replaceFragment(getChildFragmentManager(),
                R.id.signupFragment, new SetBirthdayFragment());

        Button backButton = root.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            switch (stage) {
                case BIRTHDAY:
                    FragmentChanger.replaceFragment(requireActivity()
                                    .getSupportFragmentManager(),
                            R.id.mainFragment, new LoginFragment());
                    break;
                case EMAIL:
                    FragmentChanger.replaceFragment(getChildFragmentManager(),
                            R.id.signupFragment, new SetBirthdayFragment());
                    stage = EStage.BIRTHDAY;
                    break;
                case NAME:
                    break;
                case USERNAME:
                    break;
            }
        });

        return root;
    }

    @Override
    public void setStage(EStage stage) {
        this.stage = stage;
    }
}