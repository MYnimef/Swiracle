package com.mynimef.swiracle.network.models;

public class PostImageServer {
    private String imageUrl;
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    private String postId;
    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }

    public PostImageServer() {}

    public PostImageServer(String imageUrl, String postId) {
        this.imageUrl = imageUrl;
        this.postId = postId;
    }
}
