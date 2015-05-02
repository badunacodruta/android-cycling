package org.collaborative.cycling.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class Coordinates implements Serializable {

    @JsonProperty("lat")
    private double latitude;
    @JsonProperty("lng")
    private double longitude;
    @JsonProperty("time")
    private long date;

    private String provider;
    private double accuracy;
    private int battery = -1;

    public Coordinates() {
    }

    public Coordinates(double latitude, double longitude, long date) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
    }

    public Coordinates(double latitude, double longitude, long date, String provider, double accuracy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.provider = provider;
        this.accuracy = accuracy;
    }


    public Coordinates(double latitude, double longitude, long date, String provider, double accuracy, int battery) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.provider = provider;
        this.accuracy = accuracy;
        this.battery = battery;
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
        return new Date(date);
    }

    public void setDate(Date date) {
        this.date = date.getTime();
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", date=" + date +
                ", provider='" + provider + '\'' +
                ", accuracy=" + accuracy +
                '}';
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }
}