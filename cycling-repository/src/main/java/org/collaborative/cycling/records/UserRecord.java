package org.collaborative.cycling.records;

import org.collaborative.cycling.models.ProgressStatus;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

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
    private Set<ActivityRecord> createdActivityRecordList;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @OrderBy("created_date desc")
    private Set<UserActivityRecord> joinedUserActivityRecordList;

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

    public Set<ActivityRecord> getCreatedActivityRecordList() {
        return createdActivityRecordList;
    }

    public void setCreatedActivityRecordList(Set<ActivityRecord> createdActivityRecordList) {
        this.createdActivityRecordList = createdActivityRecordList;
    }

    public Set<UserActivityRecord> getJoinedUserActivityRecordList() {
        return joinedUserActivityRecordList;
    }

    public void setJoinedUserActivityRecordList(Set<UserActivityRecord> joinedUserActivityRecordList) {
        this.joinedUserActivityRecordList = joinedUserActivityRecordList;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ProgressStatus getProgressStatusForActivity(long activityId) {
        if (joinedUserActivityRecordList == null || joinedUserActivityRecordList.isEmpty()) {
            return ProgressStatus.NOT_STARTED;
        }

        for (UserActivityRecord userActivityRecord : joinedUserActivityRecordList) {
            if (userActivityRecord.getActivity() != null && userActivityRecord.getActivity().getId() == activityId) {
                return userActivityRecord.getProgressStatus();
            }
        }

        return ProgressStatus.NOT_STARTED;
    }
}
