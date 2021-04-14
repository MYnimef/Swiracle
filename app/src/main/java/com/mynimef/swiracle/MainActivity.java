package com.mynimef.swiracle;

import android.os.Bundle;

import com.mynimef.swiracle.AppLogic.Singleton;
import com.mynimef.swiracle.Interfaces.IFragmentConnector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements IFragmentConnector {
    private FragmentManager fm;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        Singleton.getInstance().initialiseGallery(this);
    }

    public void replaceFragment(Fragment fragment1, Fragment fragment2) {    //Метод смены Фрагмента
        this.fragment = fragment1;
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mainFragment, fragment2);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void replaceFragmentBack() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mainFragment, this.fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}