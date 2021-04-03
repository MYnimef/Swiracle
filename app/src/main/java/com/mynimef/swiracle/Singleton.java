package com.mynimef.swiracle;

public class Singleton {
    private static Singleton instance;
    private PostList recommendationList;
    private PostList userList;

    private Singleton() {
        recommendationList = new PostList();
        recommendationList.setSampleList();
    }

    public static Singleton getInstance() {
        if (Singleton.instance == null) {
            Singleton.instance = new Singleton();
        }
        return Singleton.instance;
    }

    public PostList getRecommendationList() {
        return recommendationList;
    }

    public void addToList(Post post) {
        recommendationList.addPost(post);
    }
}
