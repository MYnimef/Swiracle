package com.mynimef.swiracle.models;

public final class SignInCallback {
    private String token;
    private String name;

    public SignInCallback(String token, String name) {
        this.token = token;
        this.name = name;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
