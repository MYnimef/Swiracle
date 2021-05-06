package com.mynimef.swiracle.Interfaces;

import com.mynimef.swiracle.network.models.ClothesParsingInfo;

import java.util.List;

public interface ISetClothesElements {
    void showError();
    List<ClothesParsingInfo> getInfoList();
}