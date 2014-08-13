package org.collaborative.cycling.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ActivityInfo implements Serializable {
    private long id;
    private String name;
    private ActivityAccessType activityAccessType;
    private Date createdDate;
    private Date updatedDate;
    private UserActivityState state;

    public ActivityInfo() {
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

    public UserActivityState getState() {
        return state;
    }

    public void setState(UserActivityState state) {
        this.state = state;
    }
}
