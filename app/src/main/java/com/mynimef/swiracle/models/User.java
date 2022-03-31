package com.mynimef.swiracle.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public final class User {
    @PrimaryKey @NonNull
    private String username;
    private String token;

    private String name;
    private String bio;

    private long followingAmount;
    private long followersAmount;
    private long postsAmount;

    @Ignore
    public User(
            @NonNull String username,
            String token,
            String name
    ) {
        this.username = username;
        this.token = token;
        this.name = name;

        followingAmount = 0;
        followersAmount = 0;
        postsAmount = 0;
    }

    public User(
            @NonNull String username,
            String token,
            String name,
            String bio,
            long followingAmount,
            long followersAmount,
            long postsAmount
    ) {
        this.username = username;
        this.token = token;

        this.name = name;
        this.bio = bio;

        this.followingAmount = followingAmount;
        this.followersAmount = followersAmount;
        this.postsAmount = postsAmount;
    }

    @NonNull
    public String getUsername() { return username; }
    public void setUsername(@NonNull String username) { this.username = username; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getName() { return name; }
    public void setName(String firstName) { this.name = name; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public long getFollowingAmount() { return followingAmount; }
    public void setFollowingAmount(long followingAmount) { this.followingAmount = followingAmount; }

    public long getFollowersAmount() { return followersAmount; }
    public void setFollowersAmount(long followersAmount) { this.followersAmount = followersAmount; }

    public long getPostsAmount() { return postsAmount; }
    public void setPostsAmount(long postsAmount) { this.postsAmount = postsAmount; }
}
