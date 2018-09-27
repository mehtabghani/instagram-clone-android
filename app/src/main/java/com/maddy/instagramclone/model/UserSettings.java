package com.maddy.instagramclone.model;

public class UserSettings {

    private User user;
    private UserAccountInfo accountInfo;


    public UserSettings() {

    }

    public UserSettings(User user, UserAccountInfo accountInfo) {
        this.user = user;
        this.accountInfo = accountInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserAccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(UserAccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    @Override
    public String toString() {
        return "UserSettings{" +
                "user=" + user +
                ", accountInfo=" + accountInfo +
                '}';
    }
}
