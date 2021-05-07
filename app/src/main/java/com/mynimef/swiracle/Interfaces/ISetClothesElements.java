package com.mynimef.swiracle.Interfaces;

import com.mynimef.swiracle.models.ClothesParsingInfo;

import java.util.List;

public interface ISetClothesElements {
    void showError();
    List<ClothesParsingInfo> getInfoList();
}