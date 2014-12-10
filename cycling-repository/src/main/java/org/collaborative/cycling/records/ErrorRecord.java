package org.collaborative.cycling.records;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "errors")
public class ErrorRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "owner")
  private String owner;

  @Lob
  @Column(name = "errorData")
  private byte[] errorData = new byte[0];

  @Column(name = "created_date", nullable = false)
  private Date createdDate;

  public ErrorRecord() {
  }

  public ErrorRecord(String owner, byte[] errorData, Date createdDate) {
    this.owner = owner;
    this.errorData = errorData;
    this.createdDate = createdDate;
  }


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
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
}
