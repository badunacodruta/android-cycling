package org.collaborative.cycling.models;

import java.io.Serializable;

public class JoinRequest implements Serializable {
    private long id;
    private User user;
    private long activityId;
    private JoinedStatus joinedStatus;

    public JoinRequest() {
    }

    public JoinRequest(long id, User user, long activityId, JoinedStatus joinedStatus) {
        this.id = id;
        this.user = user;
        this.activityId = activityId;
        this.joinedStatus = joinedStatus;
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

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public JoinedStatus getJoinedStatus() {
        return joinedStatus;
    }

    public void setJoinedStatus(JoinedStatus joinedStatus) {
        this.joinedStatus = joinedStatus;
    }
}
