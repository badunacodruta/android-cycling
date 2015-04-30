package org.collaborative.cycling.records.requests;

import org.collaborative.cycling.records.ActivityRecord;
import org.collaborative.cycling.records.UserRecord;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_join_activity_requests")
public class UserJoinActivityRequestRecord {

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
    private ActivityRecord activity;


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

    public ActivityRecord getActivity() {
        return activity;
    }

    public void setActivity(ActivityRecord activity) {
        this.activity = activity;
    }
}
