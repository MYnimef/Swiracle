package com.mynimef.swiracle.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClothesParsingInfo {
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("url")
    @Expose
    private String url;

    public ClothesParsingInfo(String brand, String description, String price, String currency,
                              String url) {
        this.brand = brand;
        this.description = description;
        this.price = price;
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
