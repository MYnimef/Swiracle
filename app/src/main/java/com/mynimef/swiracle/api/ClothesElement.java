package com.mynimef.swiracle.api;

public class ClothesElement {
    private String title;
    private String brand;
    private String price;
    private String url;

    public ClothesElement(String title, String brand, String price, String url) {
        this.title = title;
        this.brand = brand;
        this.price = price;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getBrand() {
        return brand;
    }

    public String getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }
}