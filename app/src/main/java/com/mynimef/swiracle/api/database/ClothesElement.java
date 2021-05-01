package com.mynimef.swiracle.api.database;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.mynimef.swiracle.api.ClothesInfo;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "clothes_element_table")
public class ClothesElement {
    @PrimaryKey(autoGenerate = true)
    private int id;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @NonNull
    private String postId;
    @NotNull
    public String getPostId() { return postId; }
    public void setPostId(@NotNull String postId) { this.postId = postId; }

    @Embedded
    private ClothesInfo info;

    public ClothesElement(@NotNull String postId, ClothesInfo info) {
        this.postId = postId;
        this.info = info;
    }

    public ClothesInfo getInfo() { return info; }
    public void setInfo(ClothesInfo info) { this.info = info; }
}