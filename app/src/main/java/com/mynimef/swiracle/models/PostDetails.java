package com.mynimef.swiracle.models;

import java.io.Serializable;
import java.util.List;

public class PostDetails implements Serializable {
    private String description;
    private List<ClothesElement> clothes;

    public PostDetails(String description, List<ClothesElement> clothes) {
        this.description = description;
        this.clothes = clothes;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<ClothesElement> getClothes() { return clothes; }
    public void setClothes(List<ClothesElement> clothes) { this.clothes = clothes; }
}