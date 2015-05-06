package org.collaborative.cycling.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HelpMessage implements Serializable {

    @JsonProperty(value = "text")
    private String message = "";
    @JsonProperty(value = "userInfo")
    private User sender;
    private Coordinates lastLocation;

    public HelpMessage() {
    }

    public HelpMessage(String message, User sender, Coordinates lastLocation) {
        this.message = message;
        this.sender = sender;
        this.lastLocation = lastLocation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Coordinates getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Coordinates lastLocation) {
        this.lastLocation = lastLocation;
    }

    @Override
    public String toString() {
        return "HelpMessage{" +
                "message='" + message + '\'' +
                ", sender=" + sender +
                ", lastLocation=" + lastLocation +
                '}';
    }
}
