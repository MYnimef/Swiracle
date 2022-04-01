package com.mynimef.swiracle.models;

public final class UserInit {
    private String username;
    private String token;
    private int permission;

    public UserInit(String username, String token, int permission) {
        this.username = username;
        this.token = token;
        this.permission = permission;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public int getPermission() { return permission; }
    public void setPermission(int permission) { this.permission = permission; }
}
