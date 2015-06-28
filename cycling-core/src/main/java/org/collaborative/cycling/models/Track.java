package org.collaborative.cycling.models;

import org.collaborative.cycling.Utilities;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Track implements Serializable {
    public List<Coordinates> coordinates;

    public Track() {
    }

    public String getCoordinates() {
        return Utilities.serialize(coordinates);
    }

    public void setCoordinates(String coordinates) throws IOException {
        if (coordinates == null || coordinates.isEmpty()) {
            this.coordinates = new ArrayList<>();
            return;
        }
        this.coordinates = Utilities.deserialize(coordinates, TrackCoordinates.class);
    }

    @Override
    public String toString() {
        return "Track{" +
                "coordinates=" + coordinates +
                '}';
    }
}

class TrackCoordinates extends ArrayList<Coordinates> {

}