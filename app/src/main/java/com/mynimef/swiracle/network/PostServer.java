package com.mynimef.swiracle.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostServer {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("clothesInfo")
    @Expose
    private List<ClothesParsingInfo> clothesInfo;

    public PostServer(String title, String description,
                      List<ClothesParsingInfo> clothesInfo) {
        this.title = title;
        this.description = description;
        this.clothesInfo = clothesInfo;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<ClothesParsingInfo> getClothesInfo() { return clothesInfo; }
    public void setClothesInfo(List<ClothesParsingInfo> clothesInfo) { this.clothesInfo = clothesInfo; }
}