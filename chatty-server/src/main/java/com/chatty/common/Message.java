package com.chatty.common;

/**
 * Created by jomalone_jia on 2018/1/11.
 */
public class Message {
    private String message;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String type;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", username='" + username + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
