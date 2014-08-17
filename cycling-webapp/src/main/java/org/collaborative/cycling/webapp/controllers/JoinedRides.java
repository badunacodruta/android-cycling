package org.collaborative.cycling.webapp.controllers;

import java.util.Date;
import java.util.List;

public class JoinedRides {
    public String name;
    public Date startDate;
    public JoinedStatus joinedStatus;
    public List<Pair<Double, Double>> track;

    public enum JoinedStatus {
        MINE, PENDING, ACCEPTED
    };

    public JoinedRides() {
    }

    public JoinedRides(String name, Date startDate, JoinedStatus joinedStatus, List<Pair<Double, Double>> track) {
        this.name = name;
        this.startDate = startDate;
        this.joinedStatus = joinedStatus;
        this.track = track;
    }
}
