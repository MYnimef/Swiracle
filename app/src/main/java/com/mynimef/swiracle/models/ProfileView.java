package com.mynimef.swiracle.models;

public final class ProfileView {
    private String username;
    private String firstName;
    private String lastName;
    private String bio;

    private long followingAmount;
    private long followersAmount;
    private long likesAmount;

    private ESubscription subscription;

    public ProfileView(
            String username,
            String firstName,
            String lastName,
            String bio,
            long followingAmount,
            long followersAmount,
            long likesAmount,
            ESubscription subscription
    ) {
        this.username = username;

        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;

        this.followingAmount = followingAmount;
        this.followersAmount = followersAmount;
        this.likesAmount = likesAmount;

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

    public long getFollowingAmount() { return followingAmount; }
    public void setFollowingAmount(long followingAmount) { this.followingAmount = followingAmount; }

    public long getFollowersAmount() { return followersAmount; }
    public void setFollowersAmount(long followersAmount) { this.followersAmount = followersAmount; }

    public long getLikesAmount() { return likesAmount; }
    public void setLikesAmount(long postsAmount) { this.likesAmount = postsAmount; }

    public ESubscription getSubscription() { return subscription; }
    public void setSubscription(ESubscription subscription) { this.subscription = subscription; }
}