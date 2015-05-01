package org.collaborative.cycling.records;


import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class CoordinatesRecord implements Serializable {

    private double latitude;
    private double longitude;
    private Date date;
    private String provider;
    private double accuracy;

    public CoordinatesRecord() {
    }

    public CoordinatesRecord(double latitude, double longitude, Date date, String provider, double accuracy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.provider = provider;
        this.accuracy = accuracy;
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
}