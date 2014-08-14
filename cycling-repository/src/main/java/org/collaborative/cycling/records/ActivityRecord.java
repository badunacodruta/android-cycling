package org.collaborative.cycling.records;

import org.collaborative.cycling.models.ActivityAccessType;
import org.collaborative.cycling.models.User;
import org.collaborative.cycling.models.UserActivityState;
import org.collaborative.cycling.models.UserCoordinates;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "activities"
//        uniqueConstraints=@UniqueConstraint(columnNames={"name", "owner"})
)
public class ActivityRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner", referencedColumnName = "email", nullable = false)
    private UserRecord owner;

    @Column(name = "activity_access_type", nullable = false)
    private ActivityAccessType activityAccessType;

    @Column(name = "coordinates", nullable = false, length = 50000)
    private String coordinates;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "activity")
    @OrderBy("created_date desc")
    private List<UserActivityRecord> joinedUserActivityRecordList;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "deleted_date")
    private Date deletedDate;

    public ActivityRecord() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRecord getOwner() {
        return owner;
    }

    public void setOwner(UserRecord owner) {
        this.owner = owner;
    }

    public ActivityAccessType getActivityAccessType() {
        return activityAccessType;
    }

    public void setActivityAccessType(ActivityAccessType activityAccessType) {
        this.activityAccessType = activityAccessType;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
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

    public List<UserActivityRecord> getJoinedUserActivityRecordList() {
        return joinedUserActivityRecordList;
    }

    public void setJoinedUserActivityRecordList(List<UserActivityRecord> joinedUserActivityRecordList) {
        this.joinedUserActivityRecordList = joinedUserActivityRecordList;
    }

    /**
     * Computes the state of the activity:
     *  NOT_STARTED if no user has started the activity or no user joined the activity
     *  FINISHED if there is no active user and at least one user has finished
     *  ACTIVE if there is at least one active user
     *
     * @return the state of the activity
     */
    public UserActivityState getState() {
        boolean finished = false;

        if (joinedUserActivityRecordList == null || joinedUserActivityRecordList.isEmpty()) {
            return UserActivityState.NOT_STARTED;
        }

        for (UserActivityRecord userActivityRecord : joinedUserActivityRecordList) {
            UserActivityState state = userActivityRecord.getState();
            if (state == UserActivityState.ACTIVE || state == UserActivityState.PAUSED) {
                return UserActivityState.ACTIVE;
            }

            if (state == UserActivityState.FINISHED) {
                finished = true;
            }
        }

        return finished ? UserActivityState.FINISHED : UserActivityState.NOT_STARTED;
    }

    public List<UserCoordinates> getUserCoordinates() {
        List<UserCoordinates> userCoordinatesList = new ArrayList<>();

        if (joinedUserActivityRecordList == null || joinedUserActivityRecordList.isEmpty()) {
            return userCoordinatesList;
        }

        for (UserActivityRecord joinedUserActivityRecord : joinedUserActivityRecordList) {
            if (joinedUserActivityRecord.isActive()) {

                UserRecord userRecord = joinedUserActivityRecord.getUser();
                User user = new User(userRecord.getEmail(), userRecord.getImageUrl());
                String coordinates = joinedUserActivityRecord.getCoordinates();
                UserCoordinates userCoordinates = new UserCoordinates(user, coordinates);

                userCoordinatesList.add(userCoordinates);
            }
        }

        return userCoordinatesList;
    }
}
