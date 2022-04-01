package com.mynimef.swiracle.models;

public final class SignUpRequest {
    private String username;
    private String password;
    private String email;
    private String name;
    private int gender;
    private DateModel birthday;

    public SignUpRequest() {
    }

    public SignUpRequest(
            String username,
            String password,
            String email,
            String name,
            int gender,
            DateModel birthday
    ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getGender() { return gender; }
    public void setGender(int gender) { this.gender = gender; }

    public DateModel getBirthday() { return birthday; }
    public void setBirthday(DateModel birthday) { this.birthday = birthday; }
}