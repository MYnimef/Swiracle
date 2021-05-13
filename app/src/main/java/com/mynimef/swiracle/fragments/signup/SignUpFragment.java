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
import com.mynimef.swiracle.fragments.signup.set.SetBirthdayFragment;
import com.mynimef.swiracle.fragments.signup.set.SetEmailFragment;
import com.mynimef.swiracle.fragments.signup.set.SetGenderFragment;
import com.mynimef.swiracle.fragments.signup.set.SetNameFragment;
import com.mynimef.swiracle.logic.FragmentChanger;
import com.mynimef.swiracle.models.DateModel;

public class SignUpFragment extends Fragment implements ISignUp {
    public enum EStage {
        BIRTHDAY,
        EMAIL,
        NAME,
        GENDER,
        USERNAME
    }
    private EStage stage;

    private DateModel birthday;
    private String email;
    private String firstName;
    private String lastName;
    private int gender;
    private String username;

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
                    FragmentChanger.replaceFragment(getChildFragmentManager(),
                            R.id.signupFragment, new SetEmailFragment());
                    stage = EStage.EMAIL;
                    break;
                case GENDER:
                    FragmentChanger.replaceFragment(getChildFragmentManager(),
                            R.id.signupFragment, new SetNameFragment());
                    stage = EStage.NAME;
                    break;
                case USERNAME:
                    FragmentChanger.replaceFragment(getChildFragmentManager(),
                            R.id.signupFragment, new SetGenderFragment());
                    stage = EStage.GENDER;
                    break;
            }
        });

        return root;
    }

    @Override
    public void setBirthday(int year, int month, int day) {
        this.birthday = new DateModel(year, month, day);
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public void setGender(int gender) {
        this.gender = gender;
        //0 - male
        //1 - female
        //2 - other
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setStage(EStage stage) {
        this.stage = stage;
    }

    @Override
    public void completeRegistration() {

    }
}