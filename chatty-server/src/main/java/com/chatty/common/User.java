package com.chatty.common;

/**
 * Created by jomalone_jia on 2018/1/11.
 */
public class User {
    private String username;
    private long time;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public User(String username, long time) {
        this.username = username;
        this.time = time;
    }
}
