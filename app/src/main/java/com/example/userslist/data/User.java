package com.example.userslist.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by vinod on 9/16/2017.
 */

public class User implements Comparable {
    private String name;
    private String email;
    private String lastActive;
    private String avatarUrl;
    private String point;
    private ArrayList<String> userGroup;

    public User() {
    }

    public User(String name,String lastActive,String point) {
        this.name=name  ;
        this.lastActive=lastActive;
        this.point=point;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getLastActive() {
        return lastActive;
    }

    public void setLastActive(String lastActive) {
        this.lastActive = lastActive;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public ArrayList<String> getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(ArrayList<String> userGroup) {
        this.userGroup = userGroup;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}