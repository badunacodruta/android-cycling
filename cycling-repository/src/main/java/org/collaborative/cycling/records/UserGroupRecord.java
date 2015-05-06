package org.collaborative.cycling.records;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_group")
public class UserGroupRecord {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @Column(name = "updated_date", nullable = false)
    private Date updatedDate;


    @ManyToOne(optional = false)
    @JoinColumn
    private UserRecord user;

    @ManyToOne(optional = false)
    @JoinColumn
    private GroupRecord group;


    public UserGroupRecord() {
    }

    public UserGroupRecord(Date createdDate, Date updatedDate, UserRecord user, GroupRecord group) {
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.user = user;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserRecord getUser() {
        return user;
    }

    public void setUser(UserRecord user) {
        this.user = user;
    }

    public GroupRecord getGroup() {
        return group;
    }

    public void setGroup(GroupRecord group) {
        this.group = group;
    }
}
