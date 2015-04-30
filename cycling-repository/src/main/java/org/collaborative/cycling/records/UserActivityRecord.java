package org.collaborative.cycling.records;

import org.collaborative.cycling.models.ProgressStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_activity")
public class UserActivityRecord {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @Column(name = "coordinates")
    private byte[] coordinates = new byte[0];

    @Column(name = "progress_status", nullable = false)
    private ProgressStatus progressStatus = ProgressStatus.NOT_STARTED;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @Version
    @Column(name = "updated_date", nullable = false)
    private Date updatedDate;


    @ManyToOne(optional = false)
    @JoinColumn
    private UserRecord user;

    @ManyToOne(optional = false)
    @JoinColumn
    private ActivityRecord activity;

    @ManyToOne
    @JoinColumn
    private GroupRecord group;


}
