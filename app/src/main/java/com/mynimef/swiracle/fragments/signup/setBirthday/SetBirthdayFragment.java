package com.mynimef.swiracle.fragments.signup.setBirthday;

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

import com.mynimef.swiracle.R;

import java.util.GregorianCalendar;

public class SetBirthdayFragment extends Fragment {
    private Button nextButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_set_birthday, container, false);

        EditText birthdayText = root.findViewById(R.id.editBirthdayDate);
        DatePicker birthdayPicker = root.findViewById(R.id.birthdayPicker);
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

        nextButton = root.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(v -> {

        });

        return root;
    }
}