package org.collaborative.cycling.models;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.collaborative.cycling.Utilities;

public class JoinedActivity implements Serializable {

    private long id;
    private String name;
    private User owner;
    private List<Coordinates> coordinates;
    private JoinedStatus joinedStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date startDate;

    public JoinedActivity() {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public JoinedStatus getJoinedStatus() {
        return joinedStatus;
    }

    public void setJoinedStatus(JoinedStatus joinedStatus) {
        this.joinedStatus = joinedStatus;
    }

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) throws IOException {
        if (coordinates == null) {
            return;
        }
        this.coordinates = Utilities.deserialize(coordinates, new ArrayList<Coordinates>().getClass());
    }
}
