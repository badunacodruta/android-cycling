package org.collaborative.cycling.models;

import org.collaborative.cycling.Utilities;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Activity implements Serializable {
    private long id;
    private String name;
    private User owner;
    private ActivityAccessType activityAccessType;
    private List<Coordinate> coordinates;
    private boolean deleted;
    private Date createdDate;
    private Date updatedDate;
    private Date deletedDate;

    public Activity() {
    }

    public Activity(String name, User owner, ActivityAccessType activityAccessType, List<Coordinate> coordinates) {
        this.name = name;
        this.owner = owner;
        this.activityAccessType = activityAccessType;
        this.coordinates = coordinates;

        this.deleted = false;
        this.createdDate = new Date();
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

    public String getCoordinates() {
        return Utilities.serialize(coordinates);
    }

    public void setCoordinates(String coordinates) throws IOException {
        this.coordinates = Utilities.deserialize(coordinates, new ArrayList<Coordinate>().getClass());
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }
}
