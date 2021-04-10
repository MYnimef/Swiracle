package com.mynimef.swiracle;

import android.os.Bundle;

import com.mynimef.swiracle.Interfaces.IFragmentConnector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements IFragmentConnector {
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void replaceFragment(Fragment fragment) {    //Метод смены Фрагмента
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mainFragment, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}