package com.mynimef.swiracle.api;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentChanger {
    public static void replaceFragment(FragmentManager fm, int resId, Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(resId, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}