package com.maddy.instagramclone.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {


    //Properties name should match with FireBase properties.
    private String user_id;
    private String email;
    private long phone_number;
    private String user_name;


    public User(){

    }

    public User(String user_id, String email, long phone_number, String user_name) {
        this.user_id = user_id;
        this.email = email;
        this.phone_number = phone_number;
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", email='" + email + '\'' +
                ", phone_number=" + phone_number +
                ", user_name='" + user_name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeLong(phone_number);
        dest.writeString(email);
        dest.writeString(user_name);
    }
}
