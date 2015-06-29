package org.collaborative.cycling.services.track.graph;

import org.collaborative.cycling.models.RoadType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Graph {

    public static final Map<String, List<Double>> nodesLocation = new HashMap<String, List<Double>>();

    public static void main(String[] args) {
    }

    public static void loadGraph(String path, String trackId, RoadType roadType) {

//        System.out.println("Processing file: " + path);

        Set<Node> nodes = new HashSet<>();

        File file = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(file), 1048576)) {
            for (String line; (line = br.readLine()) != null; ) {
                // process the line.
                String[] pointsLocation = line.split(" ");

                assert pointsLocation.length == 4;
                Node node1 = Node.getNode(Double.parseDouble(pointsLocation[0]), Double.parseDouble(pointsLocation[1]), roadType);
                Node node2 = Node.getNode(Double.parseDouble(pointsLocation[2]), Double.parseDouble(pointsLocation[3]), roadType);

                node1.addNeighbor(node2);
                node2.addNeighbor(node1);

                nodes.add(node1);
                nodes.add(node2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.out.println(nodes + ",");
    }
}
