package com.maddy.instagramclone.model;

public class UserAccountInfo {

    //Properties name should match with FireBase properties.
    private String user_name;
    private String display_name;
    private String description;
    private String profile_photo;
    private String website;
    private long followers;
    private long following;
    private long posts;

    public UserAccountInfo() {

    }

    public UserAccountInfo(String user_name, String display_name, String description, String profile_photo, String website, long followers, long following, long posts) {
        this.user_name = user_name;
        this.display_name = display_name;
        this.description = description;
        this.profile_photo = profile_photo;
        this.website = website;
        this.followers = followers;
        this.following = following;
        this.posts = posts;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public long getFollowers() {
        return followers;
    }

    public void setFollowers(long followers) {
        this.followers = followers;
    }

    public long getFollowing() {
        return following;
    }

    public void setFollowing(long following) {
        this.following = following;
    }

    public long getPosts() {
        return posts;
    }

    public void setPosts(long posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "UserAccountInfo{" +
                "user_name='" + user_name + '\'' +
                ", display_name='" + display_name + '\'' +
                ", description='" + description + '\'' +
                ", profile_photo='" + profile_photo + '\'' +
                ", website='" + website + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                ", posts=" + posts +
                '}';
    }
}
