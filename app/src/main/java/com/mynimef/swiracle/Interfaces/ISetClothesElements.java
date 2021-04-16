package com.mynimef.swiracle.Interfaces;

import com.mynimef.swiracle.AppLogic.ClothesElement;

import java.util.ArrayList;

public interface ISetClothesElements {
    void showError();
    void addClothes(String name, String description, String price, String url);
    ArrayList<ClothesElement> getClothes();
}