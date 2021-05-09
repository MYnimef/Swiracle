package com.mynimef.swiracle.models;

public class User {
    private UserDetails userDetails;
    private String token;

    public User(UserDetails userDetails, String token) {
        this.userDetails = userDetails;
        this.token = token;
    }

    public UserDetails getUserDetails() { return userDetails; }
    public void setUserDetails(UserDetails userDetails) { this.userDetails = userDetails; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}