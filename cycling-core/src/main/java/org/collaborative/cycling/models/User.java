package org.collaborative.cycling.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    public static final String ID = "USER_ID";
    private String email;
    private String imageUrl;
    public String firstName;
    public String lastName;

    public User() {
    }

    public User(String email, String imageUrl) {
        this.email = email;
        this.imageUrl = imageUrl;
    }
    public User(String email, String imageUrl, String firstName, String lastName) {
        this.email = email;
        this.imageUrl = imageUrl;
        this.firstName = firstName;
        this.lastName = lastName;
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
