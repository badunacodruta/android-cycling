package org.collaborative.cycling.records;

import org.collaborative.cycling.Utilities;
import org.collaborative.cycling.models.ActivityAccessType;
import org.collaborative.cycling.models.Coordinates;
import org.collaborative.cycling.models.JoinedStatus;
import org.collaborative.cycling.models.JoinedUser;
import org.collaborative.cycling.models.ProgressStatus;
import org.collaborative.cycling.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

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

    @Lob
    @Column(name = "coordinates")
    private byte[] coordinates = new byte[0];

    @Column(name = "start_date")
    private Date startDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "activity")
    @OrderBy("created_date desc")
    private Set<UserActivityRecord> joinedUserActivityRecordList;

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
        return new String(coordinates);
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates.getBytes();
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

    public Set<UserActivityRecord> getJoinedUserActivityRecordList() {
        return joinedUserActivityRecordList;
    }

    public void setJoinedUserActivityRecordList(Set<UserActivityRecord> joinedUserActivityRecordList) {
        this.joinedUserActivityRecordList = joinedUserActivityRecordList;
    }

    public void addJoinedUserActivityRecord(UserActivityRecord joinedUserActivityRecord) {
        if (joinedUserActivityRecordList == null) {
            joinedUserActivityRecordList = new HashSet<>();
        }
        joinedUserActivityRecordList.add(joinedUserActivityRecord);
    }

    /**
     * Computes the state of the activity: NOT_STARTED if no user has started the activity or no user joined the activity FINISHED if there is no
     * active user and at least one user has finished ACTIVE if there is at least one active user
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


//
//        UserRecord userRecord = new UserRecord("andra.mihalache@gmail.com", "https://scontent-fra3-1.xx.fbcdn.net/hphotos-xpa1/t31.0-8/1602055_10201459345701891_282709368_o.jpg");
//        User user = new User(userRecord.getEmail(), userRecord.getImageUrl());
//        JoinedStatus joinedStatus = JoinedStatus.ACCEPTED;
//        ProgressStatus progressStatus = ProgressStatus.ACTIVE;
//        String coordinates = "[{\"lat\" :  44.63002, \"lng\" : 26.170079999999984}]";
//        JoinedUser joinedUser = new JoinedUser(user, joinedStatus, progressStatus, coordinates);
//        joinedUserList.add(joinedUser);
//
//
//        userRecord = new UserRecord("cristina.marinas@gmail.com", "https://scontent-fra3-1.xx.fbcdn.net/hphotos-xtp1/v/t1.0-9/10849985_777835528948367_565091510447989263_n.jpg?oh=dcc44730f1e417dae15a8265a1087fe8&oe=562F5E6F");
//        user = new User(userRecord.getEmail(), userRecord.getImageUrl());
//        joinedStatus = JoinedStatus.ACCEPTED;
//        progressStatus = ProgressStatus.ACTIVE;
//        coordinates = "[{\"lat\" :  44.63453, \"lng\" : 26.16190000000006}]";
//        joinedUser = new JoinedUser(user, joinedStatus, progressStatus, coordinates);
//        joinedUserList.add(joinedUser);
//
//
//        userRecord = new UserRecord("badunacodruta@gmail.com", "https://scontent-fra3-1.xx.fbcdn.net/hphotos-xpf1/t31.0-8/10580947_643869415731523_2162529210250925525_o.jpg");
//        user = new User(userRecord.getEmail(), userRecord.getImageUrl());
//        joinedStatus = JoinedStatus.ACCEPTED;
//        progressStatus = ProgressStatus.ACTIVE;
//        coordinates = "[{\"lat\" :  44.63011, \"lng\" : 26.14542000000006}]";
//        joinedUser = new JoinedUser(user, joinedStatus, progressStatus, coordinates);
//        joinedUserList.add(joinedUser);
//
//
//        userRecord = new UserRecord("aurel.savoiu@gmail.com", "https://scontent-fra3-1.xx.fbcdn.net/hphotos-xap1/v/t1.0-9/10676316_932628956777123_510584342867574881_n.jpg?oh=019cde41a525aa7f86f5146f40eaa6e7&oe=55F65AFA");
//        user = new User(userRecord.getEmail(), userRecord.getImageUrl());
//        joinedStatus = JoinedStatus.ACCEPTED;
//        progressStatus = ProgressStatus.ACTIVE;
//        coordinates = "[{\"lat\" :  44.65894, \"lng\" : 26.15201000000002}]";
//        joinedUser = new JoinedUser(user, joinedStatus, progressStatus, coordinates);
//        joinedUserList.add(joinedUser);
//
//
//        userRecord = new UserRecord("mihai.ciorobea@gmail.com", "https://scontent-fra3-1.xx.fbcdn.net/hphotos-xtf1/t31.0-8/10494944_568193256620507_6594003520634166946_o.jpg");
//        user = new User(userRecord.getEmail(), userRecord.getImageUrl());
//        joinedStatus = JoinedStatus.ACCEPTED;
//        progressStatus = ProgressStatus.ACTIVE;
//        coordinates = "[{\"lat\" :  44.71396, \"lng\" : 26.211479999999938}]";
//        joinedUser = new JoinedUser(user, joinedStatus, progressStatus, coordinates);
//        joinedUserList.add(joinedUser);


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
        Set<UserActivityRecord> joinedUsers = getJoinedUserActivityRecordList();
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

    public UserActivityRecord updateJoinedUser(UserRecord userRecord, ProgressStatus progressStatus, JoinedStatus joinedStatus,
                                               String userCoordinates) {

        UserActivityRecord joinedUser = updateJoinedUser(userRecord, progressStatus, joinedStatus);
        if (joinedUser == null) {
            return joinedUser;
        }

        if (userCoordinates != null) {
            joinedUser.setCoordinates(userCoordinates);
        }

        return joinedUser;
    }

    public UserActivityRecord updateJoinedUser(UserRecord userRecord, ProgressStatus progressStatus, JoinedStatus joinedStatus,
                                               Coordinates userCoordinate) {

        UserActivityRecord joinedUser = updateJoinedUser(userRecord, progressStatus, joinedStatus);
        if (joinedUser == null) {
            return joinedUser;
        }

        if (userCoordinate != null) {
            ArrayList<Coordinates> userCoordinates = new ArrayList<>();
            String existingCoordinates = joinedUser.getCoordinates();
            if (existingCoordinates != null && !existingCoordinates.isEmpty()) {
                userCoordinates = Utilities.deserialize(existingCoordinates, new ArrayList<Coordinates>().getClass());
            }

            userCoordinates.add(userCoordinate);
            joinedUser.setCoordinates(Utilities.serialize(userCoordinates));
        }

        return joinedUser;
    }
}
