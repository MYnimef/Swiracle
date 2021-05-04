package com.mynimef.swiracle.network;

public class PriceServer {
    private int eur;
    private int usd;
    private int rub;

    public PriceServer() {
        this.eur = 0;
        this.usd = 0;
        this.rub = 0;
    }

    public PriceServer(int value, String currency) {
        switch (currency) {
            case "EUR":
                this.eur = value;
                this.usd = value;
                this.rub = value;
                break;
            case "USD":
                this.usd = value;
                this.eur = value;
                this.rub = value;
                break;
            case "RUB":
                this.rub = value;
                this.eur = value;
                this.usd = value;
                break;
        }
    }

    public int getEur() { return eur; }
    public int getUsd() { return usd; }
    public int getRub() { return rub; }

    public void setEur(int eur) { this.eur = eur; }
    public void setUsd(int usd) { this.usd = usd; }
    public void setRub(int rub) { this.rub = rub; }

    public void sum(PriceServer price) {
        this.eur += price.getEur();
        this.usd += price.getUsd();
        this.rub += price.getRub();
    }
}
