package com.mynimef.swiracle.models;

public final class ProfileView {
    private String username;
    private String firstName;
    private String lastName;
    private String bio;

    private int postsAmount;
    private int followingAmount;
    private int followersAmount;

    private ESubscription subscription;

    public ProfileView(String username, String firstName, String lastName, String bio,
                    int postsAmount, int followingAmount, int followersAmount,
                    ESubscription subscription) {
        this.username = username;

        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;

        this.postsAmount = postsAmount;
        this.followingAmount = followingAmount;
        this.followersAmount = followersAmount;

        this.subscription = subscription;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public int getPostsAmount() { return postsAmount; }
    public void setPostsAmount(int postsAmount) { this.postsAmount = postsAmount; }

    public int getFollowingAmount() { return followingAmount; }
    public void setFollowingAmount(int followingAmount) { this.followingAmount = followingAmount; }

    public int getFollowersAmount() { return followersAmount; }
    public void setFollowersAmount(int followersAmount) { this.followersAmount = followersAmount; }

    public ESubscription getSubscription() { return subscription; }
    public void setSubscription(ESubscription subscription) { this.subscription = subscription; }
}