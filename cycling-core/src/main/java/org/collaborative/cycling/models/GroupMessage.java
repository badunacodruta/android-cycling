package org.collaborative.cycling.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupMessage implements Serializable {

    private Long id;
    @JsonProperty(value = "text")
    private String message = "";
    private Date createdDate;
    @JsonProperty(value = "userInfo")
    private User sender;
    private Group group;

    public GroupMessage() {
    }

    public GroupMessage(Long id, String message, Date createdDate, User sender, Group group) {
        this.id = id;
        this.message = message;
        this.createdDate = createdDate;
        this.sender = sender;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
