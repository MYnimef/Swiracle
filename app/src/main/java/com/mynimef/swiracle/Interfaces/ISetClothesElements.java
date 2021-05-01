package com.mynimef.swiracle.Interfaces;

import com.mynimef.swiracle.api.ClothesInfo;
import com.mynimef.swiracle.api.Price;

import java.util.List;

public interface ISetClothesElements {
    void showError();
    void addClothes(String name, String description, Price price, String url);
    List<ClothesInfo> getClothes();
    Price getTotalPrice();
}