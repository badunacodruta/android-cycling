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


//        List<Coordinates> coords = new ArrayList<>();
//        coords.add(new Coordinates(44.45216343349134, 26.092872619628906));
//        JoinedUser joinedUser = new JoinedUser(new User("mihai.iancu@facebook.com", "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xfa1/v/t1.0-1/c5.0.191.191/1780731_10203168965454566_1700853055_n.jpg?oh=8537b9730cfa0676d4db6fedd5bce62c&oe=5498A777&__gda__=1419690458_7ab9c30e99c95e1f3ac4b64bf685fa13"),
//                JoinedStatus.ACCEPTED, ProgressStatus.ACTIVE, coords);
//        joinedUserList.add(joinedUser);
//
//        coords = new ArrayList<>();
//        coords.add(new Coordinates(44.45155074044464, 26.117076873779297));
//        joinedUser = new JoinedUser(new User("mihai.ciorobea@facebook.com", "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpf1/v/t1.0-1/c2.244.716.716/s320x320/1908295_513120792127754_6984548125146923198_n.jpg?oh=6313d76d9e0263d15346ff52756bfb91&oe=54CCEFC7&__gda__=1419372551_2999a34d28fb29dbabd8d78e6066cd55"),
//                JoinedStatus.ACCEPTED, ProgressStatus.ACTIVE, coords);
//        joinedUserList.add(joinedUser);
//
//        coords = new ArrayList<>();
//        coords.add(new Coordinates(44.44150165738184, 26.132183074951172));
//        joinedUser = new JoinedUser(new User("baduna.codruta@facebook.com", "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpf1/v/t1.0-1/c124.0.320.320/p320x320/10690038_643869415731523_2162529210250925525_n.jpg?oh=813fbfd6c4b7d02ac5b64bfacb79dde9&oe=54993FAF&__gda__=1419349241_fb38277b1087a91d1c60db128dc78a7c"),
//                JoinedStatus.ACCEPTED, ProgressStatus.ACTIVE, coords);
//        joinedUserList.add(joinedUser);
//
//        coords = new ArrayList<>();
//        coords.add(new Coordinates(44.42286951643662, 26.137847900390625));
//        joinedUser = new JoinedUser(new User("sinziana.gafitanu@facebook.com", "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xaf1/v/t1.0-1/c0.0.320.320/p320x320/10001401_710193512336982_597377808_n.jpg?oh=21a1a378670930ebac0fc29bec9fd86c&oe=549FE7E5&__gda__=1419815542_cf0b7802a94271162bc5f8422a90de93"),
//                JoinedStatus.ACCEPTED, ProgressStatus.ACTIVE, coords);
//        joinedUserList.add(joinedUser);
//
//        coords = new ArrayList<>();
//        coords.add(new Coordinates(44.43978578749888, 26.134586334228516));
//        joinedUser = new JoinedUser(new User("caldaruse.andrei@yahoo.com", "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpf1/v/t1.0-1/p320x320/10593133_679167158834145_7839390874062204972_n.jpg?oh=4766e6221a2a733999c1cacc24005c54&oe=549EE38D&__gda__=1418295659_fbe64727f8fd29950d11b80b812b5046"),
//                JoinedStatus.ACCEPTED, ProgressStatus.NOT_STARTED, coords);
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
            if (existingCoordinates != null && !existingCoordinates.isEmpty()) {
                userCoordinates = Utilities.deserialize(existingCoordinates, new ArrayList<Coordinates>().getClass());
            }

            userCoordinates.add(userCoordinate);
            joinedUser.setCoordinates(Utilities.serialize(userCoordinates));
        }

        return joinedUser;
    }
}
