package org.collaborative.cycling.records;

import org.collaborative.cycling.comparators.UserActivityRecordComparatorByUser;
import org.collaborative.cycling.models.ActivityAccessType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "activities")
public class ActivityRecord {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "activity_access_type", nullable = false)
    private ActivityAccessType activityAccessType = ActivityAccessType.PRIVATE;

    @Lob
    @Column(name = "coordinates")
    private byte[] coordinates = new byte[0];

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @Column(name = "updated_date", nullable = false)
    private Date updatedDate;


    @ManyToOne(optional = true)
    @JoinColumn
    private UserRecord owner;

    @OneToMany(targetEntity = UserActivityRecord.class, mappedBy = "activity")
    private List<UserActivityRecord> users = new ArrayList<>();

    @OneToMany(targetEntity = GroupActivityRecord.class, mappedBy = "activity")
    private List<GroupActivityRecord> groups = new ArrayList<>();;


    public ActivityRecord() {
    }

    public ActivityRecord(String name, ActivityAccessType activityAccessType, byte[] coordinates, Date startDate, Date createdDate, Date updatedDate) {
        this.name = name;
        this.activityAccessType = activityAccessType;
        this.coordinates = coordinates;
        this.startDate = startDate;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActivityAccessType getActivityAccessType() {
        return activityAccessType;
    }

    public void setActivityAccessType(ActivityAccessType activityAccessType) {
        this.activityAccessType = activityAccessType;
    }

    public byte[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(byte[] coordinates) {
        this.coordinates = coordinates;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public UserRecord getOwner() {
        return owner;
    }

    public void setOwner(UserRecord owner) {
        this.owner = owner;
    }

    public List<UserActivityRecord> getUsers() {
        Collections.sort(users, new UserActivityRecordComparatorByUser());
        return users;
    }

    public void setUsers(List<UserActivityRecord> users) {
        this.users = users;
    }

    public List<GroupActivityRecord> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupActivityRecord> groups) {
        this.groups = groups;
    }
}
