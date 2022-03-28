package com.mynimef.swiracle.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class Price implements Serializable {
    @SerializedName("eur")
    @Expose
    private int eur;
    @SerializedName("usd")
    @Expose
    private int usd;
    @SerializedName("rub")
    @Expose
    private int rub;

    public Price() {
        this.eur = 0;
        this.usd = 0;
        this.rub = 0;
    }

    public int getEur() { return eur; }
    public int getUsd() { return usd; }
    public int getRub() { return rub; }

    public void setEur(int eur) { this.eur = eur; }
    public void setUsd(int usd) { this.usd = usd; }
    public void setRub(int rub) { this.rub = rub; }
}