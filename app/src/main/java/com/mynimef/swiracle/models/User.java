package com.mynimef.swiracle.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "user_table")
public final class User {
    @PrimaryKey @NonNull
    private String username;
    private String email;
    private String token;

    private String firstName;
    private String lastName;
    private String bio;

    private int followingAmount;
    private int followersAmount;
    private int postsAmount;

    public User(@NotNull String username, String email, String token,
                String firstName, String lastName, String bio,
                int followingAmount, int followersAmount, int postsAmount) {
        this.username = username;
        this.email = email;
        this.token = token;

        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;

        this.followingAmount = followingAmount;
        this.followersAmount = followersAmount;
        this.postsAmount = postsAmount;
    }

    @NotNull
    public String getUsername() { return username; }
    public void setUsername(@NotNull String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public int getFollowingAmount() { return followingAmount; }
    public void setFollowingAmount(int followingAmount) { this.followingAmount = followingAmount; }

    public int getFollowersAmount() { return followersAmount; }
    public void setFollowersAmount(int followersAmount) { this.followersAmount = followersAmount; }

    public int getPostsAmount() { return postsAmount; }
    public void setPostsAmount(int postsAmount) { this.postsAmount = postsAmount; }
}
