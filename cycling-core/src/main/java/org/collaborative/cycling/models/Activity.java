package org.collaborative.cycling.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Activity implements Serializable {
    private long id;
    private String name;
    private User owner;
    private ActivityAccessType activityAccessType;
    private String coordinates;
    private List<UserCoordinates> userCoordinates;
    private Date createdDate;
    private Date updatedDate;
    private UserActivityState state;

    public Activity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public ActivityAccessType getActivityAccessType() {
        return activityAccessType;
    }

    public void setActivityAccessType(ActivityAccessType activityAccessType) {
        this.activityAccessType = activityAccessType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public UserActivityState getState() {
        return state;
    }

    public void setState(UserActivityState state) {
        this.state = state;
    }

    public List<UserCoordinates> getUserCoordinates() {
        return userCoordinates;
    }

    public void setUserCoordinates(List<UserCoordinates> userCoordinates) {
        this.userCoordinates = userCoordinates;
    }
}
