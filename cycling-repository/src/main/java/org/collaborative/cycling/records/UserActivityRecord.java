package org.collaborative.cycling.records;

import org.collaborative.cycling.models.JoinedStatus;
import org.collaborative.cycling.models.ProgressStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_activity")
public class UserActivityRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person", referencedColumnName = "email", nullable = false)
    private UserRecord user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity", referencedColumnName = "id", nullable = false)
    private ActivityRecord activity;

    @Column(name = "progress_status", nullable = false)
    private ProgressStatus progressStatus = ProgressStatus.NOT_STARTED;

    @Column(name = "join_status", nullable = false)
    private JoinedStatus joinedStatus = JoinedStatus.PENDING;

    @Lob
    @Column(name = "coordinates")
    private byte[] coordinates = new byte[0];

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "deleted_date")
    private Date deletedDate;

    public UserActivityRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRecord getUser() {
        return user;
    }

    public void setUser(UserRecord user) {
        this.user = user;
    }

    public ActivityRecord getActivity() {
        return activity;
    }

    public void setActivity(ActivityRecord activity) {
        this.activity = activity;
    }

    public ProgressStatus getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(ProgressStatus progressStatus) {
        this.progressStatus = progressStatus;
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

    public String getCoordinates() {
        return new String(coordinates);
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates.getBytes();
    }

    public JoinedStatus getJoinedStatus() {
        return joinedStatus;
    }

    public void setJoinedStatus(JoinedStatus joinedStatus) {
        this.joinedStatus = joinedStatus;
    }
}
