package com.mynimef.swiracle.Interfaces;

import com.mynimef.swiracle.AppLogic.ClothesElement;

import java.util.ArrayList;

public interface ISetInfo {
    String getTitle();
    String getDescription();
    void addClothes(String name, String description, String price, String url);
    ArrayList<ClothesElement> getClothes();
}