package org.collaborative.cycling.records;

import org.collaborative.cycling.models.ProgressStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_activity")
//TODO add unique key: (userId, activityId)
public class UserActivityRecord {

    @Id
    @GeneratedValue
    private Long id;

//    @Lob
//    @Column(name = "coordinates")
//    private CoordinatesRecord[] coordinates = new CoordinatesRecord[0];
//



    @Embedded
    private CoordinatesRecord currentCoordinates;

    @Column(name = "progress_status", nullable = false)
    private ProgressStatus progressStatus = ProgressStatus.NOT_STARTED;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @Version
    @Column(name = "updated_date", nullable = false)
    private Date updatedDate;


    @ManyToOne(optional = false)
    @JoinColumn
    private UserRecord user;

    @ManyToOne(optional = false)
    @JoinColumn
    private ActivityRecord activity;

    @ManyToOne
    @JoinColumn
    private GroupRecord group;


    public UserActivityRecord() {
    }

    public UserActivityRecord(CoordinatesRecord currentCoordinates, ProgressStatus progressStatus, Date createdDate, Date updatedDate, UserRecord user, ActivityRecord activity, GroupRecord group) {
        this.currentCoordinates = currentCoordinates;
        this.progressStatus = progressStatus;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.user = user;
        this.activity = activity;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CoordinatesRecord getCurrentCoordinates() {
        return currentCoordinates;
    }

    public void setCurrentCoordinates(CoordinatesRecord currentCoordinates) {
        this.currentCoordinates = currentCoordinates;
    }

    public ProgressStatus getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(ProgressStatus progressStatus) {
        this.progressStatus = progressStatus;
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

    public GroupRecord getGroup() {
        return group;
    }

    public void setGroup(GroupRecord group) {
        this.group = group;
    }
}
