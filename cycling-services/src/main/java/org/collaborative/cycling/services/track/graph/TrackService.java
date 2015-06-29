package org.collaborative.cycling.services.track.graph;

import org.collaborative.cycling.models.Coordinates;
import org.collaborative.cycling.models.Track;
import org.modelmapper.internal.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TrackService {

    private LoadGraph loadGraph;

    @Autowired
    public TrackService(LoadGraph loadGraph) {
        this.loadGraph = loadGraph;
    }

    public Track getTrack(Track startingPoints) {

//        double[][] userCheckpoints = {
//                {44.44189997711198, 26.052939891815186},
//                {44.43745702634121, 26.045215129852295},
//        };

//        double[][] userCheckpoints = {
//                {1,13},
//                {6,10},
//                {19,5},
//        };

        int startingPointsNr = startingPoints.coordinates.size();
        double[][] startingPointsCoordinates = new double[startingPointsNr][startingPointsNr];
        int index = 0;

        for (Coordinates coordinates : startingPoints.coordinates) {
            startingPointsCoordinates[index][0] = coordinates.getLatitude();
            startingPointsCoordinates[index][1] = coordinates.getLongitude();
            index++;
        }

        final Map<Node, Node> closestNodesToCheckpoints = ClosestNodeToCheckPoint.find(startingPointsCoordinates);

        List<Node> nodes = RunMultipleBFS.getTrack(Lists.from(closestNodesToCheckpoints.values().iterator()));

        List<Coordinates> coordinates = new ArrayList<>();
        for (Node node : nodes) {
            coordinates.add(new Coordinates(node.getX(), node.getY()));
        }

//        System.out.println(nodes);

        Track track = new Track();
        track.coordinates = coordinates;

        return track;
    }
}
