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

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    @OrderBy("created_date desc")
    private List<ActivityRecord> createdActivityRecordList;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @OrderBy("created_date desc")
    private List<UserActivityRecord> joinedUserActivityRecordList;

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

    public UserRecord(String email, String imageUrl) {
        this.email = email;
        this.imageUrl = imageUrl;

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

    public List<ActivityRecord> getCreatedActivityRecordList() {
        return createdActivityRecordList;
    }

    public void setCreatedActivityRecordList(List<ActivityRecord> createdActivityRecordList) {
        this.createdActivityRecordList = createdActivityRecordList;
    }

    public List<UserActivityRecord> getJoinedUserActivityRecordList() {
        return joinedUserActivityRecordList;
    }

    public void setJoinedUserActivityRecordList(List<UserActivityRecord> joinedUserActivityRecordList) {
        this.joinedUserActivityRecordList = joinedUserActivityRecordList;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
