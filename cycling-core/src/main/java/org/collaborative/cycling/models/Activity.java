package org.collaborative.cycling.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Activity implements Serializable {
    private long id;
    private String name;
    private User owner;
    private ActivityAccessType activityAccessType;
    private String coordinates;
    private Date startDate;
    private List<JoinedUser> joinedUsers;
    private Date createdDate;
    private Date updatedDate;
    private ProgressStatus progressStatus;

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

    public ProgressStatus getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(ProgressStatus progressStatus) {
        this.progressStatus = progressStatus;
    }

    public List<JoinedUser> getJoinedUsers() {
        return joinedUsers;
    }

    public void setJoinedUsers(List<JoinedUser> joinedUsers) {
        this.joinedUsers = joinedUsers;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
