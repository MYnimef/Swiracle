package com.mynimef.swiracle.AppLogic;

import java.util.ArrayList;

public class PostList {
    private ArrayList<Post> list;

    public PostList () {
        list = new ArrayList<>();
    }

    public ArrayList<Post> getList() {
        return list;
    }

    public void addPost(Post post) {
        list.add(post);
    }
}