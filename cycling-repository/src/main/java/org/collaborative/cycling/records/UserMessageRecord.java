package org.collaborative.cycling.records;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_messages")
public class UserMessageRecord {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @Column(name = "message")
    private byte[] message = new byte[0];

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;


    @ManyToOne(optional = false)
    @JoinColumn
    private UserRecord sender;

    @ManyToOne(optional = false)
    @JoinColumn
    private UserRecord receiver;


    public UserMessageRecord() {
    }

    public UserMessageRecord(byte[] message, Date createdDate, UserRecord sender, UserRecord receiver) {
        this.message = message;
        this.createdDate = createdDate;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public UserRecord getSender() {
        return sender;
    }

    public void setSender(UserRecord sender) {
        this.sender = sender;
    }

    public UserRecord getReceiver() {
        return receiver;
    }

    public void setReceiver(UserRecord receiver) {
        this.receiver = receiver;
    }
}
