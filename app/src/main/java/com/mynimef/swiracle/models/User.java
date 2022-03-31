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
    private int permission;

    private String name;
    private String bio;

    private long followingAmount;
    private long followersAmount;
    private long postsAmount;

    private boolean actualUser;

    @Ignore
    public User(
            @NonNull String username,
            String token,
            int permission
    ) {
        this.username = username;
        this.token = token;
        this.permission = permission;

        name = "";
        followingAmount = 0;
        followersAmount = 0;
        postsAmount = 0;

        actualUser = true;
    }

    public User(
            @NonNull String username,
            String token,
            int permission,
            String name,
            String bio,
            long followingAmount,
            long followersAmount,
            long postsAmount,
            boolean actualUser
    ) {
        this.username = username;
        this.token = token;
        this.permission = permission;

        this.name = name;
        this.bio = bio;

        this.followingAmount = followingAmount;
        this.followersAmount = followersAmount;
        this.postsAmount = postsAmount;

        this.actualUser = actualUser;
    }

    @NonNull
    public String getUsername() { return username; }
    public void setUsername(@NonNull String username) { this.username = username; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public int getPermission() { return permission; }
    public void setPermission(int permission) { this.permission = permission; }

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

    public boolean isActualUser() { return actualUser; }
    public void setActualUser(boolean actualUser) { this.actualUser = actualUser; }
}
