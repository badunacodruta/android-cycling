package org.collaborative.cycling.records;

import org.collaborative.cycling.comparators.UserGroupRecordComparatorByUser;
import org.collaborative.cycling.records.requests.UserJoinGroupRequestRecord;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "groups")
public class GroupRecord {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @Version
    @Column(name = "updated_date", nullable = false)
    private Date updatedDate;


    @ManyToOne(optional = false)
    @JoinColumn
    private UserRecord owner;

    @OneToMany(targetEntity = UserGroupRecord.class, mappedBy = "group")
//    @SortComparator(UserGroupRecordComparatorByUser.class)
    private List<UserGroupRecord> users = new ArrayList<>();
    ;

    @OneToMany(targetEntity = GroupActivityRecord.class, mappedBy = "group")
    private List<GroupActivityRecord> activities = new ArrayList<>();
    ;

    @OneToMany(targetEntity = GroupMessageRecord.class, mappedBy = "group")
    @OrderBy("createdDate DESC")
    private List<GroupMessageRecord> receivedMessages = new ArrayList<>();
    ;

    @OneToMany(targetEntity = UserActivityRecord.class, mappedBy = "group")
    private List<UserActivityRecord> userActivities = new ArrayList<>();
    ;

    @OneToMany(targetEntity = UserJoinGroupRequestRecord.class, mappedBy = "group")
    @OrderBy("createdDate ASC")
    private List<UserJoinGroupRequestRecord> userJoinGroupRequests = new ArrayList<>();
    ;


    public GroupRecord() {
    }

    public GroupRecord(String name, Date createdDate, Date updatedDate, UserRecord owner) {
        this.name = name;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.owner = owner;
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

    public List<UserGroupRecord> getUsers() {
        //TODO make the annotation work and skip this
        Collections.sort(users, new UserGroupRecordComparatorByUser());
        return users;
    }

    public void setUsers(List<UserGroupRecord> users) {
        this.users = users;
    }

    public List<GroupActivityRecord> getActivities() {
        return activities;
    }

    public void setActivities(List<GroupActivityRecord> activities) {
        this.activities = activities;
    }

    public List<GroupMessageRecord> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<GroupMessageRecord> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public List<UserActivityRecord> getUserActivities() {
        return userActivities;
    }

    public void setUserActivities(List<UserActivityRecord> userActivities) {
        this.userActivities = userActivities;
    }

    public List<UserJoinGroupRequestRecord> getUserJoinGroupRequests() {
        return userJoinGroupRequests;
    }

    public void setUserJoinGroupRequests(List<UserJoinGroupRequestRecord> userJoinGroupRequests) {
        this.userJoinGroupRequests = userJoinGroupRequests;
    }


}
