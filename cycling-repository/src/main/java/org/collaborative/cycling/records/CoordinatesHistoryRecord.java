package org.collaborative.cycling.records;

import org.collaborative.cycling.models.Coordinates;

import javax.persistence.*;

@Entity
@Table(name = "coordinates_history")
public class CoordinatesHistoryRecord {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @Column(name = "coordinates")
    private Coordinates coordinates;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "activity_id")
    private Long activity_id;

    public CoordinatesHistoryRecord() {
    }

    public CoordinatesHistoryRecord(Coordinates coordinates, Long user_id, Long activity_id) {
        this.coordinates = coordinates;
        this.user_id = user_id;
        this.activity_id = activity_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(Long activity_id) {
        this.activity_id = activity_id;
    }
}
