package org.collaborative.cycling.records;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class CoordinatesRecord implements Serializable {

    @Column(nullable = true)
    private double latitude;
    @Column(nullable = true)
    private double longitude;
    @Column(nullable = true)
    private Date date;
    @Column(nullable = true)
    private String provider;
    @Column(nullable = true)
    private double accuracy;
    @Column(nullable = true)
    private int battery = -1;

    public CoordinatesRecord() {
    }

    public CoordinatesRecord(double latitude, double longitude, Date date, String provider, double accuracy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.provider = provider;
        this.accuracy = accuracy;
    }

    public CoordinatesRecord(double latitude, double longitude, Date date, String provider, double accuracy, int battery) {
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
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }
}