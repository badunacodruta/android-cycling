package org.collaborative.cycling.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupInviteUserRequest implements Serializable {

    private Long id;
    private Date createdDate;
    private User user;
    private Group group;
    private User sender;

    public GroupInviteUserRequest() {
    }

    public GroupInviteUserRequest(Long id, Date createdDate, User user, Group group, User sender) {
        this.id = id;
        this.createdDate = createdDate;
        this.user = user;
        this.group = group;
        this.sender = sender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
