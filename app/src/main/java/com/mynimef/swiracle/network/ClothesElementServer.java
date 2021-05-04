package com.mynimef.swiracle.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClothesElementServer {
    @SerializedName("id")
    @Expose
    private int id;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @SerializedName("postId")
    @Expose
    private String postId;
    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }

    @SerializedName("info")
    @Expose
    private ClothesInfoServer info;

    public ClothesElementServer(int id, String postId, ClothesInfoServer info) {
        this.id = id;
        this.postId = postId;
        this.info = info;
    }

    public ClothesInfoServer getInfo() {
        return info;
    }

    public void setInfo(ClothesInfoServer info) {
        this.info = info;
    }
}
