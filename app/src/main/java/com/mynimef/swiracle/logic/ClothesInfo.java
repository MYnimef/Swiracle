package com.mynimef.swiracle.logic;

import androidx.room.Embedded;

public class ClothesInfo {
    private String brand;
    private String description;
    @Embedded
    private Price price;

    public ClothesInfo(String brand, String description, Price price) {
        this.brand = brand;
        this.description = description;
        this.price = price;
    }

    public String getBrand() { return brand; }
    public String getDescription() { return description; }
    public Price getPrice() { return price; }

    public void setBrand(String brand) { this.brand = brand; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(Price price) { this.price = price; }
}