package org.collaborative.cycling.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class Coordinates implements Serializable {
//    TODO: modify from k and B to lat and lng -> web + app
    @JsonProperty("k")
    private double latitude;
    @JsonProperty("B")
    private double longitude;

//    TODO: json prop
    private Date date;

    public Coordinates() {
    }

    public Coordinates(double latitude, double longitude) {
        this(latitude, longitude, null);
    }

    public Coordinates(double latitude, double longitude, Date date) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", date=" + date +
                '}';
    }
}