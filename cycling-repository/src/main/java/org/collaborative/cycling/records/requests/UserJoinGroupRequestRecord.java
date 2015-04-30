package org.collaborative.cycling.records.requests;

import org.collaborative.cycling.records.GroupRecord;
import org.collaborative.cycling.records.UserRecord;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_join_group_requests")
public class UserJoinGroupRequestRecord {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;


    @ManyToOne(optional = false)
    @JoinColumn
    private UserRecord user;

    @ManyToOne(optional = false)
    @JoinColumn
    private GroupRecord group;


    public UserJoinGroupRequestRecord() {
    }

    public UserJoinGroupRequestRecord(Date createdDate, UserRecord user, GroupRecord group) {
        this.createdDate = createdDate;
        this.user = user;
        this.group = group;
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

    public UserRecord getUser() {
        return user;
    }

    public void setUser(UserRecord user) {
        this.user = user;
    }

    public GroupRecord getGroup() {
        return group;
    }

    public void setGroup(GroupRecord group) {
        this.group = group;
    }
}
