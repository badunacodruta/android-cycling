package org.collaborative.cycling.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ActivityUser implements Serializable {

    @JsonProperty("userInfo")
    private User user;
    @JsonProperty("userLocation")
    private Coordinates coordinates;
    private boolean isGroup;

    public ActivityUser() {
    }

    public ActivityUser(User user, Coordinates coordinates, boolean isGroup) {
        this.user = user;
        this.coordinates = coordinates;
        this.isGroup = isGroup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setIsGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityUser that = (ActivityUser) o;

        return !(user != null ? !user.equals(that.user) : that.user != null);

    }

    @Override
    public int hashCode() {
        return user != null ? user.hashCode() : 0;
    }
}
