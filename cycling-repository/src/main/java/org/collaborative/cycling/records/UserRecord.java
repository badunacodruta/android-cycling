package org.collaborative.cycling.records;

import org.collaborative.cycling.comparators.UserGroupRecordComparatorByGroup;
import org.collaborative.cycling.records.requests.GroupInviteUserRequestRecord;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class UserRecord {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    //TODO
    @Column(name = "image_url")
    private String imageUrl = "TODO";

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    //    @Version
    @Column(name = "updated_date", nullable = false)
    private Date updatedDate;


    @OneToMany(targetEntity = GroupRecord.class, mappedBy = "owner")
    private List<GroupRecord> createdGroups = new ArrayList<>();;

    @OneToMany(targetEntity = ActivityRecord.class, mappedBy = "owner")
    private List<ActivityRecord> createdActivities = new ArrayList<>();;

    @OneToMany(targetEntity = UserGroupRecord.class, mappedBy = "user")
//    @SortComparator(UserGroupRecordComparatorByGroup.class)
    private List<UserGroupRecord> groups = new ArrayList<>();

    @OneToMany(targetEntity = UserActivityRecord.class, mappedBy = "user")
    private List<UserActivityRecord> activities = new ArrayList<>();;

    @OneToMany(targetEntity = ErrorRecord.class, mappedBy = "owner")
    private List<ErrorRecord> errors = new ArrayList<>();;

    @OneToMany(targetEntity = GroupMessageRecord.class, mappedBy = "sender")
    private List<GroupMessageRecord> sentGroupMessages = new ArrayList<>();;

    @OneToMany(targetEntity = GroupInviteUserRequestRecord.class, mappedBy = "user")
    @OrderBy("createdDate ASC")
    private List<GroupInviteUserRequestRecord> receivedGroupInvitations = new ArrayList<>();;

    @OneToMany(targetEntity = UserMessageRecord.class, mappedBy = "receiver")
    @OrderBy("createdDate ASC")
    private List<UserMessageRecord> receivedUserMessages = new ArrayList<>();;


    public UserRecord() {
    }

    public UserRecord(String email, String imageUrl, Date createdDate, Date updatedDate) {
        this.email = email;
        this.imageUrl = imageUrl;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public List<GroupRecord> getCreatedGroups() {
        return createdGroups;
    }

    public void setCreatedGroups(List<GroupRecord> createdGroups) {
        this.createdGroups = createdGroups;
    }

    public List<ActivityRecord> getCreatedActivities() {
        return createdActivities;
    }

    public void setCreatedActivities(List<ActivityRecord> createdActivities) {
        this.createdActivities = createdActivities;
    }

    public List<UserGroupRecord> getGroups() {
        //TODO make the annotation work and skip this
        Collections.sort(groups, new UserGroupRecordComparatorByGroup());
        return groups;
    }

    public void setGroups(List<UserGroupRecord> groups) {
        this.groups = groups;
    }

    public List<UserActivityRecord> getActivities() {
        return activities;
    }

    public void setActivities(List<UserActivityRecord> activities) {
        this.activities = activities;
    }

    public List<ErrorRecord> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorRecord> errors) {
        this.errors = errors;
    }

    public List<GroupMessageRecord> getSentGroupMessages() {
        return sentGroupMessages;
    }

    public void setSentGroupMessages(List<GroupMessageRecord> sentGroupMessages) {
        this.sentGroupMessages = sentGroupMessages;
    }

    public List<GroupInviteUserRequestRecord> getReceivedGroupInvitations() {
        return receivedGroupInvitations;
    }

    public void setReceivedGroupInvitations(List<GroupInviteUserRequestRecord> receivedGroupInvitations) {
        this.receivedGroupInvitations = receivedGroupInvitations;
    }

    public List<UserMessageRecord> getReceivedUserMessages() {
        return receivedUserMessages;
    }

    public void setReceivedUserMessages(List<UserMessageRecord> receivedUserMessages) {
        this.receivedUserMessages = receivedUserMessages;
    }
}
