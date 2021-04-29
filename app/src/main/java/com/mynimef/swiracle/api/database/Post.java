package com.mynimef.swiracle.api.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class Post {
    @Embedded
    private final PostInfo postInfo;
    @Relation(
            parentColumn = "uid",
            entityColumn = "postId"
    )
    private final List<ClothesElement> clothes;

    public Post(PostInfo postInfo, List<ClothesElement> clothes) {
        this.postInfo = postInfo;
        this.clothes = clothes;
    }

    public PostInfo getPostInfo() { return postInfo; }
    public List<ClothesElement> getClothes() { return clothes; }
}