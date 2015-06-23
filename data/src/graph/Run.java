package graph;

import graph.generate.ClosestNodeToCheckPoint;

import java.io.File;
import java.util.*;

public class Run {


    public static final String dirs[] = {
            "Ro-AtoB-Gravel.tracks.data",
            "Ro-AtoB-Paved.tracks.data",
            "Ro-AtoB-Unpaved.tracks.data",
            "Ro-MTB-Gravel.tracks.data",
            "Ro-MTB-Paved.tracks.data",
            "Ro-MTB-Unpaved.tracks.data",
            "Ro-Racing-Gravel.tracks.data",
            "Ro-Racing-Paved.tracks.data",
            "Ro-Racing-Unpaved.tracks.data"
    };
    public static final String BASE_PATH = "/Users/mciorobe/work/mihai/mihigh/cycling-app/bikemap/data/trackNeighbors/";

    public static void main(String[] args) {


        for (String dir : dirs) {
            dir = "Ro-AtoB-Gravel.tracks.data";
            System.out.println(dir);
            File folder = new File(BASE_PATH + dir);
            File[] allFiles = folder.listFiles();


            for (final File file : allFiles) {
                if (!file.getName().endsWith(".graph")) {
                    System.out.println("Skipping file:" + file.getName());
                    continue;
                }

                String trackId = file.getName().replace(".graph", "");
                Graph.loadGraph(file.getPath(), trackId);

            }

            break;

        }


        System.out.println("Size1: " + Node.allNodes.size());
//        OptimizeGraph.run();
//        System.out.println("Size2: " + Node.allNodes.size());

//        // lines
//        System.out.println("[");
//        String text = "";
//        int index = -1;
//        for (Node node : Node.allNodes.values()) {
//            ++index;
//            for (Node neighbor : node.getNeighbors()) {
//                text += "[\n";
//                text += "\t[" + node.getX() + ", " + node.getY() + "],\n";
//                text += "\t[" + neighbor.getX() + ", " + neighbor.getY() + "],\n";
//                text += "],\n";
//            }
//
//            if (index > 10000) {
//                System.out.println(text);
//                index = -1;
//                text = "";
//            }
//        }
//
//        System.out.println(text);
//        System.out.println("]");

        // points
//        System.out.println("[");
//        for (Node node : Node.allNodes.values()) {
//            System.out.println("[" + node.getX() + "," + node.getY() + "],");
//        }
//        System.out.println("],");


        double[][] userCheckpoints = {
                {44.51748555, 26.08395696},
                {44.5393453, 26.10407352},
                {44.62065445, 26.12432957},
                {44.7140499, 26.19286537}
        };
        final Map<Node, Node> closestNodesToCheckpoints = ClosestNodeToCheckPoint.find(userCheckpoints);


        // detect (1by1) paths


        HashMap<Node, Integer> border = new HashMap<Node, Integer>() {{
            int index = -1;
            for (Node node : closestNodesToCheckpoints.values()) {
                ++index;
                put(node, index);
            }

        }};

        HashMap<Node, Integer> visited = new HashMap<>();
        HashMap<Node, Node> parent = new HashMap<>();

        Set<Node> componentIntersection = new HashSet<>();

        int nrOfConexComponents = closestNodesToCheckpoints.size();

        while (border.size() != 0 && nrOfConexComponents != 1) {
            HashMap<Node, Integer> toVisit = new HashMap<>(border);
            border.clear();

            for (Node borderNode : toVisit.keySet()) {
                Integer borderComponentId = toVisit.get(borderNode);
                Integer visitedComponentId = visited.get(borderNode);

                if (visitedComponentId != null) {
                    if (!visitedComponentId.equals(borderComponentId)) {

                        // add intersection
                        componentIntersection.add(borderNode);
                        componentIntersection.addAll(borderNode.getNeighbors());

                        // merge the 2 components
                        replaceComponentId(visited, borderComponentId, visitedComponentId);
                        replaceComponentId(toVisit, borderComponentId, visitedComponentId);
                        replaceComponentId(border, borderComponentId, visitedComponentId);
                        --nrOfConexComponents;
                    }
                    continue;
                }

                visited.put(borderNode, borderComponentId);
                for (Node neighbor : borderNode.getNeighbors()) {
                    if (!visited.containsKey(neighbor)) {
                        border.put(neighbor, borderComponentId);
                        parent.put(neighbor, borderNode);
                    }
                }
            }
        }


        System.out.println(visited.size());
        System.out.println(border.size());
        System.out.println(parent.size());
        System.out.println(componentIntersection.size());
        System.out.println(nrOfConexComponents);

//        System.out.println(visited);

        // get the road
//        closestNodesToCheckpoints
        getRoad(closestNodesToCheckpoints.values(), componentIntersection, parent);


    }

    private static void getRoad(Collection<Node> closestNodesToCheckpoints, Set<Node> componentIntersection, HashMap<Node, Node> parent) {

//        Node firstNode = closestNodesToCheckpoints.iterator().next();
        Set<Node> path = new HashSet<>();

        for (Node node : componentIntersection) {
            while (node != null && !path.contains(node)) {
                path.add(node);
                node = parent.get(node);
            }
//            System.out.println(node);
//            System.out.println(path.size());
//            System.out.println(path);

        }

        System.out.println(path);

    }

    public static void replaceComponentId(HashMap<Node, Integer> map, Integer from, Integer to) {
        for (Node node : map.keySet()) {
            if (map.get(node).equals(from)) {
                map.put(node, to);
            }
        }

    }
}
