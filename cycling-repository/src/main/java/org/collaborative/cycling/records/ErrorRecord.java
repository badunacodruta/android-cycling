package org.collaborative.cycling.records;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "errors")
public class ErrorRecord {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @Column(name = "errorData", nullable = false)
    private byte[] errorData = new byte[0];

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;


    @ManyToOne
    @JoinColumn
    private UserRecord owner;


    public ErrorRecord() {
    }

    public ErrorRecord(Long ownerId, byte[] errorData, Date createdDate) {
        this.errorData = errorData;
        this.createdDate = createdDate;

        if (ownerId != null) {
            if (this.owner == null) {
                this.owner = new UserRecord();
            }
            this.owner.setId(ownerId);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getErrorData() {
        return errorData;
    }

    public void setErrorData(byte[] errorData) {
        this.errorData = errorData;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public UserRecord getOwner() {
        return owner;
    }

    public void setOwner(UserRecord owner) {
        this.owner = owner;
    }
}
