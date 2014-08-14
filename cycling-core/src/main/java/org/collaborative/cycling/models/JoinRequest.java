package org.collaborative.cycling.models;

import java.io.Serializable;

public class JoinRequest implements Serializable {
    private long id;
    private User user;
    private long activityId;
    private boolean waitingForAcceptance;

    public JoinRequest() {
    }

    public JoinRequest(long id, User user, long activityId, boolean waitingForAcceptance) {
        this.id = id;
        this.user = user;
        this.activityId = activityId;
        this.waitingForAcceptance = waitingForAcceptance;
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

    public boolean isWaitingForAcceptance() {
        return waitingForAcceptance;
    }

    public void setWaitingForAcceptance(boolean waitingForAcceptance) {
        this.waitingForAcceptance = waitingForAcceptance;
    }
}
