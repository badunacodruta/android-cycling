package org.collaborative.cycling.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.collaborative.cycling.Utilities;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Activity implements Serializable {
    public long id;
    public String name;
    public User owner;
    public ActivityAccessType activityAccessType;
    public List<Coordinates> coordinates;
    public List<JoinedUser> joinedUsers;
    public ProgressStatus progressStatus;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy/MM/dd HH:mm")
    public Date startDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy/MM/dd")
    public Date createdDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy/MM/dd")
    public Date updatedDate;

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

    public List<JoinedUser> getJoinedUsers() {
        return joinedUsers;
    }

    public void setJoinedUsers(List<JoinedUser> joinedUsers) {
        this.joinedUsers = joinedUsers;
    }

    public ProgressStatus getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(ProgressStatus progressStatus) {
        this.progressStatus = progressStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
        return Utilities.serialize(coordinates);
    }

    public void setCoordinates(String coordinates) throws IOException {
        if (coordinates == null || coordinates.isEmpty()) {
            this.coordinates = new ArrayList<>();
            return;
        }
        this.coordinates = Utilities.deserialize(coordinates, new ArrayList<Coordinates>().getClass());
    }
}