package com.mynimef.swiracle.fragments.signup;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mynimef.swiracle.Interfaces.ISignUp;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.signup.birthday.SetBirthdayFragment;
import com.mynimef.swiracle.fragments.signup.email.SetEmailFragment;
import com.mynimef.swiracle.fragments.signup.gender.SetGenderFragment;
import com.mynimef.swiracle.fragments.signup.name.SetNameFragment;
import com.mynimef.swiracle.fragments.signup.username.SetUsernameFragment;
import com.mynimef.swiracle.logic.FragmentChanger;
import com.mynimef.swiracle.models.DateModel;
import com.mynimef.swiracle.models.SignUpRequest;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class SignUpFragment extends Fragment implements ISignUp {
    private SignUpViewModel signUpViewModel;

    public enum EStage {
        BIRTHDAY,
        EMAIL,
        NAME,
        GENDER,
        USERNAME,
        PASSWORD
    }
    private EStage stage;

    private SignUpRequest request;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        stage = EStage.BIRTHDAY;
        request = new SignUpRequest();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        FragmentChanger.replaceFragment(
                getChildFragmentManager(),
                R.id.signupFragment,
                new SetBirthdayFragment()
        );

        Button backButton = root.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            switch (stage) {
                case BIRTHDAY:
                    requireActivity().getSupportFragmentManager().popBackStackImmediate();
                    break;
                case EMAIL:
                    FragmentChanger.replaceFragment(
                            getChildFragmentManager(),
                            R.id.signupFragment,
                            new SetBirthdayFragment()
                    );
                    stage = EStage.BIRTHDAY;
                    break;
                case NAME:
                    FragmentChanger.replaceFragment(
                            getChildFragmentManager(),
                            R.id.signupFragment,
                            new SetEmailFragment()
                    );
                    stage = EStage.EMAIL;
                    break;
                case GENDER:
                    FragmentChanger.replaceFragment(
                            getChildFragmentManager(),
                            R.id.signupFragment,
                            new SetNameFragment()
                    );
                    stage = EStage.NAME;
                    break;
                case USERNAME:
                    FragmentChanger.replaceFragment(
                            getChildFragmentManager(),
                            R.id.signupFragment,
                            new SetGenderFragment()
                    );
                    stage = EStage.GENDER;
                    break;
                case PASSWORD:
                    FragmentChanger.replaceFragment(
                            getChildFragmentManager(),
                            R.id.signupFragment,
                            new SetUsernameFragment()
                    );
                    stage = EStage.USERNAME;
                    break;
            }
        });

        return root;
    }

    @Override
    public void setBirthday(int year, int month, int day) {
        request.setBirthday(new DateModel(year, month, day));
    }

    @Override
    public void setEmail(String email) {
        request.setEmail(email);
    }

    @Override
    public void setName(String name) { request.setName(name);
    }

    @Override
    public void setGender(int gender) {
        request.setGender(gender);
        //0 - male
        //1 - female
        //2 - other
    }

    @Override
    public void setUsername(String username) { request.setUsername(username); }

    @Override
    public void setPassword(String password) { request.setPassword(password); }

    @Override
    public void setStage(EStage stage) { this.stage = stage; }

    @Override
    public void completeRegistration(Handler signUpHandler) {
        signUpViewModel.signUp(request, signUpHandler);
    }
}