package com.mynimef.swiracle.api.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class Post {
    @Embedded
    public PostInfo postInfo;
    @Relation(
            parentColumn = "uid",
            entityColumn = "postId"
    )
    public List<ClothesElement> clothes;

    public Post(PostInfo postInfo, List<ClothesElement> clothes) {
        this.postInfo = postInfo;
        this.clothes = clothes;
    }
}