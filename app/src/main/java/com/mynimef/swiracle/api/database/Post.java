package com.mynimef.swiracle.api.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class Post {
    @Embedded
    private PostInfo postInfo;
    @Relation(
            parentColumn = "id",
            entityColumn = "postId"
    )
    private List<PostImage> images;

    public Post(PostInfo postInfo, List<PostImage> images) {
        this.postInfo = postInfo;
        this.images = images;
    }

    public PostInfo getPostInfo() { return postInfo; }
    public List<PostImage> getImages() { return images; }

    public void setPostInfo(PostInfo postInfo) { this.postInfo = postInfo; }
    public void setImages(List<PostImage> images) { this.images = images; }
}