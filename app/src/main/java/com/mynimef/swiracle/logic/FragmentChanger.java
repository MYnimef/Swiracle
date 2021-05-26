package com.mynimef.swiracle.logic;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mynimef.swiracle.R;

public class FragmentChanger {
    public static void replaceFragment(FragmentManager fm, int resId, Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(resId, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public static void replaceFragmentAnim(FragmentManager fm, int resId, Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out);
        ft.replace(resId, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}