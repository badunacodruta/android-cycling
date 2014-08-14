package org.collaborative.cycling.models;

import java.io.Serializable;

public class User implements Serializable {

    public static final String ID = "USER_ID";
    private String email;
    private String imageUrl;

    public User() {
    }

    public User(String email, String imageUrl) {
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public static String getId() {
        return ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
