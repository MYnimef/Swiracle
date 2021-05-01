package com.mynimef.swiracle.api;

import androidx.room.Embedded;

public class ClothesInfo {
    private String brand;
    private String description;
    @Embedded
    private Price price;
    private String url;

    public ClothesInfo(String brand, String description, Price price, String url) {
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.url = url;
    }

    public String getBrand() { return brand; }
    public String getDescription() { return description; }
    public Price getPrice() { return price; }
    public String getUrl() { return url; }

    public void setBrand(String brand) { this.brand = brand; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(Price price) { this.price = price; }
    public void setUrl(String url) { this.url = url; }
}