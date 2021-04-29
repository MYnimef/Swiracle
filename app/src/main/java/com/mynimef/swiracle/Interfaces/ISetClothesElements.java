package com.mynimef.swiracle.Interfaces;

import com.mynimef.swiracle.api.Price;
import com.mynimef.swiracle.api.database.ClothesElement;

import java.util.List;

public interface ISetClothesElements {
    void showError();
    void addClothes(String name, String description, Price price, String url);
    List<ClothesElement> getClothes();
    Price getTotalPrice();
}