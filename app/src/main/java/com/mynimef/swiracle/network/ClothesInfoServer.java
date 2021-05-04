package com.mynimef.swiracle.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClothesInfoServer {
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private PriceServer price;

    public ClothesInfoServer(String brand, String description, PriceServer price) {
        this.brand = brand;
        this.description = description;
        this.price = price;
    }

    public String getBrand() { return brand; }
    public String getDescription() { return description; }
    public PriceServer getPrice() { return price; }

    public void setBrand(String brand) { this.brand = brand; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(PriceServer price) { this.price = price; }
}
