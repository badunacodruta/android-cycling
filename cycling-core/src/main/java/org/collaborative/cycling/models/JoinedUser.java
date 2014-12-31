package org.collaborative.cycling.models;

import org.collaborative.cycling.Utilities;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JoinedUser implements Serializable {
    private User user;
    private JoinedStatus joinedStatus;
    private ProgressStatus progressStatus;
    private List<Coordinates> coordinates = new ArrayList<>();

    public JoinedUser() {
    }

    public JoinedUser(User user, JoinedStatus joinedStatus, ProgressStatus progressStatus, List<Coordinates> coordinates) throws IOException {
        this.user = user;
        this.joinedStatus = joinedStatus;
        this.progressStatus = progressStatus;
        this.coordinates = coordinates;
    }
    public JoinedUser(User user, JoinedStatus joinedStatus, ProgressStatus progressStatus, String coordinates) throws IOException {
        this.user = user;
        this.joinedStatus = joinedStatus;
        this.progressStatus = progressStatus;
        setCoordinates(coordinates);
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

    public ProgressStatus getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(ProgressStatus progressStatus) {
        this.progressStatus = progressStatus;
    }

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }

    public void setCoordinates(String coordinates) throws IOException {
        if (coordinates == null || coordinates.isEmpty()) {
            this.coordinates = new ArrayList<>();
            return;
        }
        this.coordinates = Utilities.deserialize(coordinates, new ArrayList<Coordinates>().getClass());
    }
}
