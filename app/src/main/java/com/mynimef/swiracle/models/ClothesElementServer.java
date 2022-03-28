package com.mynimef.swiracle.models;

public final class ClothesElementServer {
    private String urlId;
    public String getUrlId() { return urlId; }
    public void setUrlId(String urlId) { this.urlId = urlId; }

    private String postId;
    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }

    private String brand;
    private String description;
    private String priceValue;
    private String priceCurrency;

    public ClothesElementServer(String urlId, String postId,
                                String brand, String description,
                                String priceValue, String priceCurrency) {
        this.urlId = urlId;
        this.postId = postId;
        this.brand = brand;
        this.description = description;
        this.priceValue = priceValue;
        this.priceCurrency = priceCurrency;
    }

    public String getBrand() { return brand; }
    public String getDescription() { return description; }
    public String getPriceValue() { return priceValue; }
    public String getPriceCurrency() { return priceCurrency; }

    public void setBrand(String brand) { this.brand = brand; }
    public void setDescription(String description) { this.description = description; }
    public void setPriceValue(String priceValue) { this.priceValue = priceValue; }
    public void setPriceCurrency(String priceCurrency) { this.priceCurrency = priceCurrency; }
}
