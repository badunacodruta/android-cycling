package org.collaborative.cycling.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Coordinate implements Serializable {

    @JsonProperty("k")
    private double latitude;

    @JsonProperty("B")
    private double longitude;

    public Coordinate() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
