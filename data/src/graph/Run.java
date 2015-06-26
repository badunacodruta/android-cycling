package graph;

import graph.generate.ClosestNodeToCheckPoint;

import java.io.File;
import java.util.*;

public class Run {


    public static final String dirs[] = {
//            "Ro-AtoB-Gravel.tracks.data",
//            "Ro-AtoB-Paved.tracks.data",
//            "Ro-AtoB-Unpaved.tracks.data",
//            "Ro-MTB-Gravel.tracks.data",
//            "Ro-MTB-Paved.tracks.data",
//            "Ro-MTB-Unpaved.tracks.data",
            "Ro-Racing-Gravel.tracks.data",
//            "Ro-Racing-Paved.tracks.data",
//            "Ro-Racing-Unpaved.tracks.data"
    };
    public static final String BASE_PATH = "/Users/mciorobe/work/mihai/mihigh/cycling-app/bikemap/data/trackNeighbors/";

    public static void main(String[] args) {


        for (String dir : dirs) {
//            System.out.println(dir);
            File folder = new File(BASE_PATH + dir);
            File[] allFiles = folder.listFiles();


            for (final File file : allFiles) {
                if (!file.getName().endsWith(".graph")) {
                    continue;
                }

                String trackId = file.getName().replace(".graph", "");
                Graph.loadGraph(file.getPath(), trackId);

            }


        }


        if (1==1)
            return;

        double[][] userCheckpoints = {
                {44.41962054167956, 26.04806900024414},
                {44.4186396836315, 26.042919158935547},
                {44.4178733768324, 26.034507751464844},
                {44.42210326507494, 26.03463649749756}
        };
        final Map<Node, Node> closestNodesToCheckpoints = ClosestNodeToCheckPoint.find(userCheckpoints);


        // Domain = index of node in user input
        // Key = "node"  Value = the node domain
        final Map<Node, Integer> domains = new HashMap<Node, Integer>() {{
            int index = -1;
            for (Node node : closestNodesToCheckpoints.values()) {
                put(node, ++index);
            }
        }};

        // Key = "domain_domain" Value = list of nodes
        Map<String, Set<Node>> domainIntersections = new HashMap<String, Set<Node>>() {{
            for (Integer domain1 : domains.values()) {
                for (Integer domain2 : domains.values()) {
                    put(domain1 + "_" + domain2, new HashSet<Node>());

                }
            }
        }};

        // Key = "domain"  Value = list of domains
        Map<Integer, Set<Integer>> domainNeighbors = new HashMap<Integer, Set<Integer>>() {{
            for (Integer domainId : domains.values()) {
                put(domainId, new HashSet<Integer>());
            }
        }};


        // Key = unvisited node  Value = parent node
        HashMap<Integer, PriorityQueue<List<Node>>> domainBorder = new HashMap<Integer, PriorityQueue<List<Node>>>() {{
            for (final Node node : closestNodesToCheckpoints.values()) {
                Integer domainId = domains.get(node);

                PriorityQueue<List<Node>> borderList = new PriorityQueue<>(300, new Comparator<List<Node>>() {
                    @Override
                    public int compare(List<Node> node1, List<Node> node2) {
                        double diff = node1.get(0).distance(node) - node2.get(0).distance(node);

                        if (diff == 0) {
                            return 0;
                        }

                        if (diff > 0) {
                            return 1;
                        }

                        return -1;
                    }
                });

                borderList.add(Arrays.asList(node, node));
                put(domainId, borderList);
            }
        }};


        HashMap<Node, Node> visited = new HashMap<>();

        int round = -1;
        System.out.println("var stepByStep = {");

        while (true) {
            int neighborsNr = 0;
            for (Set<Integer> neighborsList : domainNeighbors.values()) {
                neighborsNr += neighborsList.size();
            }
            if (neighborsNr == (closestNodesToCheckpoints.size() - 2) * 2 + 2) {
                break;
            }


            int borderSize = 0;
            for (Integer domainId : domainBorder.keySet()) {
                borderSize += domainBorder.get(domainId).size();
            }
            if (borderSize == 0) {
                break;
            }


            ++round;
            System.out.print(round + ": [");

            for (Integer domainId : domainBorder.keySet()) {
                PriorityQueue<List<Node>> border = domainBorder.get(domainId);
                List<Node> nodeAndParent = border.poll();
                System.out.print(nodeAndParent.get(0) + ", ");

                expandNode(domains, domainIntersections, domainNeighbors, border, visited,
                        nodeAndParent.get(0), nodeAndParent.get(1));
            }
            System.out.println("],");

        }
        System.out.println("}");


        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        getTrack(closestNodesToCheckpoints.values(), visited, domainNeighbors, domainIntersections);

//        HashMap<Integer, Set<Node>> nodesInDomain = new HashMap<>();
//
//
//        for (Node node : domains.keySet()) {
//            Integer domainId = domains.get(node);
//
//            Set<Node> nodes = nodesInDomain.get(domainId);
//            if (nodes == null) {
//                nodes = new HashSet<>();
//                nodesInDomain.put(domainId, nodes);
//            }
//
//            nodes.add(node);
//        }


//        System.out.println(nodesInDomain.toString().replace("=", ":"));

    }

    private static void expandNode(Map<Node, Integer> domains,
                                   Map<String, Set<Node>> domainIntersections,
                                   Map<Integer, Set<Integer>> domainNeighbors,
                                   PriorityQueue<List<Node>> border,
                                   HashMap<Node, Node> visited,
                                   Node node,
                                   Node parent) {

        visited.put(node, parent);
        domains.put(node, domains.get(parent));

        for (Node neighbor : node.getNeighbors()) {

            if (visited.keySet().contains(neighbor)) {
                //check to see if 2 separate domains
                Integer expandingDomain = domains.get(node);
                Integer hitDomain = domains.get(neighbor);
                if (!expandingDomain.equals(hitDomain)
                        && domainNeighbors.get(expandingDomain).size() < 2
                        && domainNeighbors.get(hitDomain).size() < 2) {
                    String key = expandingDomain + "_" + hitDomain;
                    if (domainIntersections.get(key).size() == 0) {
                        domainIntersections.get(key).add(node);
                        domainIntersections.get(key).addAll(node.getNeighbors());
                    }

                    String reverseKey = hitDomain + "_" + expandingDomain;
                    if (domainIntersections.get(reverseKey).size() == 0) {
                        domainIntersections.get(reverseKey).add(node);
                        domainIntersections.get(reverseKey).addAll(node.getNeighbors());
                    }

                    domainNeighbors.get(expandingDomain).add(hitDomain);
                    domainNeighbors.get(hitDomain).add(expandingDomain);
                }
            } else {
                border.add(Arrays.asList(neighbor, node));
            }
        }
    }

    private static void getTrack(Collection<Node> closestNodesToCheckpoints, HashMap<Node, Node> parent, Map<Integer, Set<Integer>> domainNeighbors, Map<String, Set<Node>> domainIntersections) {
        Set<Node> path = new HashSet<>();


        Set<Node> border = new HashSet<>();
        for (Integer domain1 : domainNeighbors.keySet()) {
            Set<Integer> domain1neighbor = domainNeighbors.get(domain1);

            for (Integer domain2 : domain1neighbor) {
                Set<Node> intersectionNodes = domainIntersections.get(domain1 + "_" + domain2);
                border.addAll(intersectionNodes);
            }
        }


        while (!path.containsAll(closestNodesToCheckpoints)) {
            Set<Node> toVisit = new HashSet<>();
            toVisit.addAll(border);
            path.addAll(border);
            border.clear();

            for (Node node : toVisit) {
                border.add(parent.get(node));
            }
        }

//        System.out.println("FINAL PATH:");
        System.out.println("var userData = {0: ");
        System.out.println(path);
        System.out.println("}");

    }


}
