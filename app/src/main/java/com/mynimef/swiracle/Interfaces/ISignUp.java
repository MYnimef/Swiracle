package com.mynimef.swiracle.Interfaces;

import com.mynimef.swiracle.fragments.signup.SignUpFragment;

public interface ISignUp {
    void setBirthday(int year, int month, int day);
    void setEmail(String email);
    void setName(String firstName, String lastName);
    void setGender(int gender);
    void setUsername(String username);
    void setStage(SignUpFragment.EStage stage);
}