package com.mynimef.swiracle.AppLogic;

import java.net.URL;

public class ClothesElement {
    private String title;
    private String brand;
    private String price;
    private URL url;

    public ClothesElement(String title, String brand, String price, URL url) {
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

    public URL getUrl() {
        return url;
    }
}