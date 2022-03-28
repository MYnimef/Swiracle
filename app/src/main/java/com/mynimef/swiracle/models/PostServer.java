package com.mynimef.swiracle.models;

import java.util.List;

public final class PostServer {
    private String title;
    private String description;
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