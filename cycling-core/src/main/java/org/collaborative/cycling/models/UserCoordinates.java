package org.collaborative.cycling.models;

import java.io.Serializable;

public class UserCoordinates implements Serializable {
    private User user;
    private String coordinates;

    public UserCoordinates() {
    }

    public UserCoordinates(User user, String coordinates) {
        this.user = user;
        this.coordinates = coordinates;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
