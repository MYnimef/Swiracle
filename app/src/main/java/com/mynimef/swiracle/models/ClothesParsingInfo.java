package com.mynimef.swiracle.models;

public final class ClothesParsingInfo {
    private String brand;
    private String description;
    private String price;
    private String currency;
    private String url;

    public ClothesParsingInfo(String brand, String description, String price, String currency,
                              String url) {
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.url = url;
    }

    public String getBrand() { return brand; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public String getCurrency() { return currency; }
    public String getUrl() { return url; }

    public void setBrand(String brand) { this.brand = brand; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(String price) { this.price = price; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setUrl(String url) { this.url = url; }
}
