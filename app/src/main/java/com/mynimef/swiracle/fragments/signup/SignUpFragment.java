package com.mynimef.swiracle.fragments.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.signup.setBirthday.SetBirthdayFragment;
import com.mynimef.swiracle.logic.FragmentChanger;


public class SignUpFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        FragmentChanger.replaceFragment(getChildFragmentManager(),
                R.id.signupFragment, new SetBirthdayFragment());

        return root;
    }
}