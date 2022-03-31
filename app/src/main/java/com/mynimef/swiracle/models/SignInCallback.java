package com.mynimef.swiracle.models;

public final class SignInCallback {
    private String token;
    private Integer permission;

    public SignInCallback(String token, Integer permission) {
        this.token = token;
        this.permission = permission;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Integer getPermission() { return permission; }
    public void setPermission(Integer permission) { this.permission = permission; }
}
