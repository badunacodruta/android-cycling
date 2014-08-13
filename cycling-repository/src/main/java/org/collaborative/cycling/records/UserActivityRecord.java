package org.collaborative.cycling.records;

import org.collaborative.cycling.models.UserActivityState;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_activity")
public class UserActivityRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", referencedColumnName = "email", nullable = false)
    private UserRecord user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity", referencedColumnName = "id", nullable = false)
    private ActivityRecord activity;

    @Column(name = "state", nullable = false)
    private UserActivityState state = UserActivityState.NOT_STARTED;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "deleted_date")
    private Date deletedDate;

    public UserActivityRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserActivityState getState() {
        return state;
    }

    public void setState(UserActivityState state) {
        this.state = state;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }
}
