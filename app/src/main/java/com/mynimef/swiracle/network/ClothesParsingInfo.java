package com.mynimef.swiracle.network;

public class ClothesParsingInfo {
    private String brand;
    private String description;
    private String price;

    public ClothesParsingInfo(String brand, String description, String price) {
        this.brand = brand;
        this.description = description;
        this.price = price;
    }

    public String getBrand() { return brand; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }

    public void setBrand(String brand) { this.brand = brand; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(String price) { this.price = price; }
}
