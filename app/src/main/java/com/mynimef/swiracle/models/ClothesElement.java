package com.mynimef.swiracle.models;

public class ClothesElement {
    private String urlId;
    public String getUrlId() { return urlId; }
    public void setUrlId(String urlId) { this.urlId = urlId; }

    private String brand;
    private String description;
    private Price price;

    public ClothesElement() {}

    public ClothesElement(String urlId, String brand, String description,
                          Price price) {
        this.urlId = urlId;
        this.brand = brand;
        this.description = description;
        this.price = price;
    }

    public String getBrand() { return brand; }
    public String getDescription() { return description; }

    public void setBrand(String brand) { this.brand = brand; }
    public void setDescription(String description) { this.description = description; }

    public Price getPrice() { return price; }
    public void setPrice(Price price) { this.price = price; }
}