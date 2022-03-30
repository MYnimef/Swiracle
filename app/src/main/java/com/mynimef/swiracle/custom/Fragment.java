package com.mynimef.swiracle.custom;

import com.mynimef.swiracle.Interfaces.INavigation;
import com.mynimef.swiracle.fragments.navigation.NavigationFragment;

public class Fragment extends androidx.fragment.app.Fragment {
    public final INavigation getNavigation() {
        Fragment fragment = (Fragment) getParentFragment();
        if (fragment != null) {
            if (fragment instanceof NavigationFragment) {
                return (INavigation) fragment;
            } else {
                return fragment.getNavigation();
            }
        } else {
            return (INavigation) this;
        }
    }
}
