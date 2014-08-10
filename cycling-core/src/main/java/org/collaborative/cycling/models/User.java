package org.collaborative.cycling.models;

import java.io.Serializable;

public class User implements Serializable {

    public static final String ID = "USER_ID";
    private String email;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
