package com.mynimef.swiracle.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostServer {
    @SerializedName("id")
    @Expose
    private String id;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @SerializedName("timeMillis")
    @Expose
    private String timeMillis;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("likesAmount")
    @Expose
    private int likesAmount;
    @SerializedName("commentsAmount")
    @Expose
    private int commentsAmount;

    @SerializedName("clothes")
    @Expose
    private List<ClothesElementServer> clothes;

    @SerializedName("price")
    @Expose
    private PriceServer price;

    public PostServer() {}

    public PostServer(String id, String timeMillis,
                      String title, String description,
                      int likesAmount, int commentsAmount,
                      List<ClothesElementServer> clothes,
                      PriceServer price) {
        this.id = id;
        this.timeMillis = timeMillis;
        this.title = title;
        this.description = description;
        this.likesAmount = likesAmount;
        this.commentsAmount = commentsAmount;
        this.clothes = clothes;
        this.price = price;
    }

    public String getTimeMillis() { return timeMillis; }
    public void setTimeMillis(String timeMillis) { this.timeMillis = timeMillis; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getLikesAmount() { return likesAmount; }
    public void setLikesAmount(int likesAmount) { this.likesAmount = likesAmount; }

    public int getCommentsAmount() { return commentsAmount; }
    public void setCommentsAmount(int commentsAmount) { this.commentsAmount = commentsAmount; }

    public PriceServer getPrice() { return price; }
    public void setPrice(PriceServer price) { this.price = price; }
}
