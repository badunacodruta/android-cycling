package org.collaborative.cycling.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class JoinRequest implements Serializable {
    private long id;
    private User user;
    private String activityName;
    private JoinedStatus joinedStatus;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy/MM/dd")
    private Date startDate;


    public JoinRequest() {
    }

    public JoinRequest(long id, User user, String activityName, JoinedStatus joinedStatus, Date startDate) {
        this.id = id;
        this.user = user;
        this.activityName = activityName;
        this.joinedStatus = joinedStatus;
        this.startDate = startDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JoinedStatus getJoinedStatus() {
        return joinedStatus;
    }

    public void setJoinedStatus(JoinedStatus joinedStatus) {
        this.joinedStatus = joinedStatus;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
