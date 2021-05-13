package com.mynimef.swiracle.fragments.signup;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.Interfaces.ISignUp;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.logic.FragmentChanger;

import java.util.GregorianCalendar;

public class SetBirthdayFragment extends Fragment {
    private ISignUp signUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUp = (ISignUp) getParentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup_set_birthday, container, false);
        EditText birthdayText = root.findViewById(R.id.editBirthdayDate);
        DatePicker birthdayPicker = root.findViewById(R.id.birthdayPicker);
        Button nextButton = root.findViewById(R.id.nextButton);

        birthdayPicker.setMaxDate(System.currentTimeMillis());
        birthdayPicker.updateDate(birthdayPicker.getYear() - 1,
                birthdayPicker.getMonth(),
                birthdayPicker.getDayOfMonth());
        birthdayPicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
            String monthString  = (String) DateFormat.format("MMM",
                    new GregorianCalendar(year, monthOfYear, dayOfMonth));
            birthdayText.setText(monthString + " " + dayOfMonth + ", " + year);
            nextButton.setEnabled(true);
        });

        nextButton.setOnClickListener(v -> {
            signUp.setBirthday(birthdayPicker.getYear(),
                    birthdayPicker.getMonth(),
                    birthdayPicker.getDayOfMonth());
            signUp.setStage(SignUpFragment.EStage.EMAIL);
            FragmentChanger.replaceFragment(getParentFragmentManager(),
                    R.id.signupFragment, new SetEmailFragment());
        });

        return root;
    }
}