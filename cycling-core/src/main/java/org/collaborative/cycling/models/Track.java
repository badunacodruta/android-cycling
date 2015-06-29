package org.collaborative.cycling.models;

import org.collaborative.cycling.Utilities;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Track implements Serializable {
    public List<Coordinates> coordinates;
    public Set<RoadType> roadTypes;

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

    public Set<RoadType> getRoadTypes() {
        return roadTypes;
    }

    public void setRoadTypes(String roadTypes) {
        if (roadTypes == null || roadTypes.isEmpty()) {
            this.roadTypes = new HashSet<>();
            return;
        }
        this.roadTypes = Utilities.deserialize(roadTypes, TrackRoadTypes.class);
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

class TrackRoadTypes extends HashSet<RoadType> {

}