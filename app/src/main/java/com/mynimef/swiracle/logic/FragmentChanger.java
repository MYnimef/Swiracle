package com.mynimef.swiracle.logic;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mynimef.swiracle.R;

public final class FragmentChanger {
    public static void replaceFragment(FragmentManager fm, int resId, Fragment newFragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(resId, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public static void replaceFragmentAnim(FragmentManager fm, int resId, Fragment newFragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
        );
        ft.replace(resId, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public static void replaceFragmentAnimBottom(FragmentManager fm, int resId, Fragment newFragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(
                R.anim.slide_in_bottom,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out_bottom
        );
        ft.replace(resId, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}