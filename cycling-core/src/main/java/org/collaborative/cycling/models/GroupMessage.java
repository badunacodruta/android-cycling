package org.collaborative.cycling.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupMessage implements Serializable {

    private Long id;
    private byte[] message = new byte[0];
    private Date createdDate;
    private User sender;
    private Group group;

    public GroupMessage() {
    }

    public GroupMessage(Long id, byte[] message, Date createdDate, User sender, Group group) {
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

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
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
