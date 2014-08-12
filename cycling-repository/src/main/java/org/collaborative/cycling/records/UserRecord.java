package org.collaborative.cycling.records;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class UserRecord {

    @Id
    @Column(name = "email", nullable = false)
    private String email;

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    @OrderBy("created_date desc")
    private List<ActivityRecord> activityRecordList;

    @Column(name = "untitled_activities_index", nullable = false)
    private long untitledActivitiesIndex;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "deleted_date")
    private Date deletedDate;

    public UserRecord() {
    }

    public UserRecord(String email) {
        this.email = email;
        this.deleted = false;
        this.createdDate = new Date();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public long getUntitledActivitiesIndex() {
        return untitledActivitiesIndex;
    }

    public void setUntitledActivitiesIndex(long untitledActivitiesIndex) {
        this.untitledActivitiesIndex = untitledActivitiesIndex;
    }

    public List<ActivityRecord> getActivityRecordList() {
        return activityRecordList;
    }

    public void setActivityRecordList(List<ActivityRecord> activityRecordList) {
        this.activityRecordList = activityRecordList;
    }
}
