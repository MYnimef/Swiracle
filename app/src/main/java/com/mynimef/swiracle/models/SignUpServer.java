package com.mynimef.swiracle.models;

public class SignUpServer {
    private String username;
    private String password;
    private String email;

    private String firstName;
    private String secondName;

    private int gender;
    private DateModel birthday;

    public SignUpServer(String username, String password, String email,
                        String firstName, String secondName,
                        int gender, DateModel birthday) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.gender = gender;
        this.birthday = birthday;
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

    public int getGender() { return gender; }
    public void setGender(int gender) { this.gender = gender; }

    public DateModel getBirthday() { return birthday; }
    public void setBirthday(DateModel birthday) { this.birthday = birthday; }
}