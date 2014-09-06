package org.collaborative.cycling.records;

import org.collaborative.cycling.Utilities;
import org.collaborative.cycling.models.*;

import javax.persistence.*;
import java.io.IOException;
import java.util.*;

@Entity
@Table(name = "activities"
//        uniqueConstraints=@UniqueConstraint(columnNames={"name", "owner"})
)
public class ActivityRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner", referencedColumnName = "email", nullable = false)
    private UserRecord owner;

    @Column(name = "activity_access_type", nullable = false)
    private ActivityAccessType activityAccessType = ActivityAccessType.PRIVATE;

    @Column(name = "coordinates", nullable = false, length = 50000)
    private String coordinates = "[]";

    @Column(name = "start_date")
    private Date startDate;

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


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<UserActivityRecord> getJoinedUserActivityRecordList() {
        return joinedUserActivityRecordList;
    }

    public void setJoinedUserActivityRecordList(List<UserActivityRecord> joinedUserActivityRecordList) {
        this.joinedUserActivityRecordList = joinedUserActivityRecordList;
    }

    public void addJoinedUserActivityRecord(UserActivityRecord joinedUserActivityRecord) {
        if (joinedUserActivityRecordList == null) {
            joinedUserActivityRecordList = new ArrayList<>();
        }
//        for (UserActivityRecord userActivityRecord : joinedUserActivityRecordList) {
//            if (userActivityRecord.getUser().getEmail().equals(joinedUserActivityRecord.getUser().getEmail())) {
//                joinedUserActivityRecordList.remove(userActivityRecord);
//            }
//        }
        joinedUserActivityRecordList.add(joinedUserActivityRecord);
    }

    /**
     * Computes the state of the activity:
     *  NOT_STARTED if no user has started the activity or no user joined the activity
     *  FINISHED if there is no active user and at least one user has finished
     *  ACTIVE if there is at least one active user
     *
     * @return the state of the activity
     */
    public ProgressStatus getProgressStatus() {
        boolean finished = false;

        if (joinedUserActivityRecordList == null || joinedUserActivityRecordList.isEmpty()) {
            return ProgressStatus.NOT_STARTED;
        }

        for (UserActivityRecord userActivityRecord : joinedUserActivityRecordList) {
            ProgressStatus state = userActivityRecord.getProgressStatus();
            if (state == ProgressStatus.ACTIVE || state == ProgressStatus.PAUSED) {
                return ProgressStatus.ACTIVE;
            }

            if (state == ProgressStatus.FINISHED) {
                finished = true;
            }
        }

        return finished ? ProgressStatus.FINISHED : ProgressStatus.NOT_STARTED;
    }

    public List<JoinedUser> getJoinedUsers() throws IOException {
        List<JoinedUser> joinedUserList = new ArrayList<>();

        if (joinedUserActivityRecordList == null || joinedUserActivityRecordList.isEmpty()) {
            return joinedUserList;
        }

        for (UserActivityRecord joinedUserActivityRecord : joinedUserActivityRecordList) {
            UserRecord userRecord = joinedUserActivityRecord.getUser();
            User user = new User(userRecord.getEmail(), userRecord.getImageUrl());
            JoinedStatus joinedStatus = joinedUserActivityRecord.getJoinedStatus();
            ProgressStatus progressStatus = joinedUserActivityRecord.getProgressStatus();
            String coordinates = joinedUserActivityRecord.getCoordinates();

            JoinedUser joinedUser = new JoinedUser(user, joinedStatus, progressStatus, coordinates);
            joinedUserList.add(joinedUser);
        }

        return joinedUserList;
    }

    public UserActivityRecord updateJoinedUser(UserRecord userRecord, ProgressStatus progressStatus, JoinedStatus joinedStatus) {
        if (userRecord == null) {
            return null;
        }

        UserActivityRecord newJoinedUser = null;
        List<UserActivityRecord> joinedUsers = getJoinedUserActivityRecordList();
        if (joinedUsers != null) {
            for (UserActivityRecord joinedUser : joinedUsers) {
                if (joinedUser.getUser().getEmail().equals(userRecord.getEmail())) {
                    newJoinedUser = joinedUser;
                    break;
                }
            }
        }

        Date currentDate = new Date();
        if (newJoinedUser == null) {
            newJoinedUser = new UserActivityRecord();
            newJoinedUser.setCreatedDate(currentDate);
            addJoinedUserActivityRecord(newJoinedUser);
        }

        newJoinedUser.setUser(userRecord);
        newJoinedUser.setActivity(this);
        if (progressStatus != null) {
            newJoinedUser.setProgressStatus(progressStatus);
        }
        if (joinedStatus != null) {
            newJoinedUser.setJoinedStatus(joinedStatus);
        }

        newJoinedUser.setUpdatedDate(currentDate);

        return newJoinedUser;
    }

    public UserActivityRecord updateJoinedUser(UserRecord userRecord, ProgressStatus progressStatus) {
        return updateJoinedUser(userRecord, progressStatus, null);
    }

    public UserActivityRecord updateJoinedUser(UserRecord userRecord, JoinedStatus joinedStatus) {
        return updateJoinedUser(userRecord, null, joinedStatus);
    }

    public UserActivityRecord updateJoinedUser(UserRecord userRecord) {
        return updateJoinedUser(userRecord, null, null);
    }

    public UserActivityRecord updateJoinedUser(UserRecord userRecord, ProgressStatus progressStatus, JoinedStatus joinedStatus, String userCoordinates) {

        UserActivityRecord joinedUser = updateJoinedUser(userRecord, progressStatus, joinedStatus);
        if (joinedUser == null) {
            return joinedUser;
        }

        if (userCoordinates != null) {
            joinedUser.setCoordinates(userCoordinates);
        }

        return joinedUser;
    }

    public UserActivityRecord updateJoinedUser(UserRecord userRecord, ProgressStatus progressStatus, JoinedStatus joinedStatus, Coordinates userCoordinate) {

        UserActivityRecord joinedUser = updateJoinedUser(userRecord, progressStatus, joinedStatus);
        if (joinedUser == null) {
            return joinedUser;
        }

        if (userCoordinate != null) {
            ArrayList<Coordinates> userCoordinates = new ArrayList<>();
            String existingCoordinates = joinedUser.getCoordinates();
            if (existingCoordinates != null) {
                userCoordinates = Utilities.deserialize(existingCoordinates, new ArrayList<Coordinates>().getClass());
            }

            userCoordinates.add(userCoordinate);
            joinedUser.setCoordinates(Utilities.serialize(userCoordinates));
        }

        return joinedUser;
    }
}
