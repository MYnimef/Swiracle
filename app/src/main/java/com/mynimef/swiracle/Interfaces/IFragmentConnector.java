// Интерфейс, для вызова метода смены фрагмента, который
// расположен в Activity, из текущего фрагмента.

package com.mynimef.swiracle.Interfaces;

import androidx.fragment.app.Fragment;

public interface IFragmentConnector {
    void replaceFragment(Fragment fragment1, Fragment fragment2);
    void replaceFragmentBack();
}