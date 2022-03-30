package com.mynimef.swiracle.Interfaces;

import com.mynimef.swiracle.custom.Fragment;

public interface INavigation {
    void replaceToMain();
    void showBottomFragment(Fragment fragment);
    void hideBottomFragment();
}
