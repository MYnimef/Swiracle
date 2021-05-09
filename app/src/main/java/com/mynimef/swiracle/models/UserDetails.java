package com.mynimef.swiracle.models;

public class UserDetails {
    private String username;
    private String password;

    private String email;
    private String firstName;
    private String secondName;

    public UserDetails(String username, String password, String email, String firstName, String secondName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getSecondName() { return secondName; }
    public void setSecondName(String secondName) { this.secondName = secondName; }
}
