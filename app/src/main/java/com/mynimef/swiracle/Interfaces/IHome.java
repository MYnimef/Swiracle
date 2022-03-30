package com.mynimef.swiracle.Interfaces;

import com.mynimef.swiracle.custom.Fragment;
import com.mynimef.swiracle.models.PostInfo;

public interface IHome {
    int getSignedIn();
    void likePost(String id);
    void updatePostInfo(PostInfo postInfo);

    Fragment getFragment();
}