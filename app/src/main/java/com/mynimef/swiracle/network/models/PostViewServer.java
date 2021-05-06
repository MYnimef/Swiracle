package com.mynimef.swiracle.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mynimef.swiracle.logic.Price;
import com.mynimef.swiracle.network.models.PostImageServer;

import java.util.List;

public class PostViewServer {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("likesAmount")
    @Expose
    private int likesAmount;
    @SerializedName("commentsAmount")
    @Expose
    private int commentsAmount;
    @SerializedName("images")
    @Expose
    private List<PostImageServer> images;
    @SerializedName("price")
    @Expose
    private Price price;

    public PostViewServer(String id, String title,
                    int likesAmount, int commentsAmount,
                    List<PostImageServer> images, Price price) {
        this.id = id;
        this.title = title;
        this.likesAmount = likesAmount;
        this.commentsAmount = commentsAmount;
        this.images = images;
        this.price = price;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getLikesAmount() { return likesAmount; }
    public int getCommentsAmount() { return commentsAmount; }
    public List<PostImageServer> getImages() { return images; }
    public Price getPrice() { return price; }

    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setLikesAmount(int likesAmount) { this.likesAmount = likesAmount; }
    public void setCommentsAmount(int commentsAmount) { this.commentsAmount = commentsAmount; }
    public void setImages(List<PostImageServer> images) { this.images = images; }
    public void setPrice(Price price) { this.price = price; }
}