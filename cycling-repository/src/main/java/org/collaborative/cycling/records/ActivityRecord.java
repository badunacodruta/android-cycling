package org.collaborative.cycling.records;

import org.collaborative.cycling.models.ActivityAccessType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "activities",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"name", "owner"})
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

    @Column(name = "coordinates", nullable = false)
    private String coordinates;

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

    public ActivityRecord(String name, UserRecord owner, ActivityAccessType activityAccessType, String coordinates) {
        this.name = name;
        this.owner = owner;
        this.activityAccessType = activityAccessType;
        this.coordinates = coordinates;

        this.deleted = false;
        this.createdDate = new Date();
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
}
