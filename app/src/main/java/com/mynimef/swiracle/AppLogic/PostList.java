package com.mynimef.swiracle.AppLogic;

import com.mynimef.swiracle.AppLogic.Post;
import com.mynimef.swiracle.R;

import java.util.ArrayList;

public class PostList {
    private ArrayList<Post> list;

    public void setSampleList() {
        int[] link = {R.drawable.picture1, R.drawable.picture2, R.drawable.picture3, R.drawable.picture4};
        list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(new Post("Meme", "В этом наверное есть какой-то смысол",
                    0, 0, link));
        }
    }

    public ArrayList<Post> getList() {
        return list;
    }

    public void addPost(Post post) {
        list.add(post);
    }
}
