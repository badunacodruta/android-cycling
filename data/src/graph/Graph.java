package graph;

import graph.graph.LoadNodes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Graph {


    public static final Map<String, List<Double>> nodesLocation = new HashMap<String, List<Double>>();

    public static void main(String[] args) {
    }

    public static void loadGraph(String path, String trackId) {

//        System.out.println("Processing file: " + path);

        Set<Node> nodes = new HashSet<>();

        File file = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(file), 1048576)) {
            for (String line; (line = br.readLine()) != null; ) {
                // process the line.
                String[] pointsLocation = line.split(" ");

                assert pointsLocation.length == 4;
                Node node1 = Node.getNode(Double.parseDouble(pointsLocation[0]), Double.parseDouble(pointsLocation[1]));
                Node node2 = Node.getNode(Double.parseDouble(pointsLocation[2]), Double.parseDouble(pointsLocation[3]));

                node1.addNeighbor(node2);
                node2.addNeighbor(node1);

                nodes.add(node1);
                nodes.add(node2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(nodes + ",");
    }

    static void loadNodesLocations() {
        String path = "/Users/mciorobe/work/mihai/mihigh/cycling-app/bikemap/data/trackNeighbors/nodes/nodes.json";
        File file = new File(path);

        try (BufferedReader br = new BufferedReader(new FileReader(file), 1048576)) {

            assert br.readLine().contains("{");

            while (true) {
                String l1 = br.readLine();
                if (l1.contains("}")) {
                    break;
                }

                String l2 = br.readLine();
                String l3 = br.readLine();
                String l4 = br.readLine();

                String id = l1.split("\"")[1];
                assert id.contains("_");

                l2 = l2.replace(" ", "");
                l2 = l2.replace(",", "");
                l3 = l3.replace(" ", "");

                List<Double> location = Arrays.asList(Double.parseDouble(l2), Double.parseDouble(l3));
                Graph.nodesLocation.put(id, location);
            }

//            for (String line; (line = br.readLine()) != null; ) {
//                // process the line.
//
//            }
            // line is not visible here.
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void loadNodesLocationsMultiFile() {
        String path = "/Users/mciorobe/work/mihai/mihigh/cycling-app/bikemap/data/trackNeighbors/nodes/";

        File folder = new File(path);
        File[] allFiles = folder.listFiles();

//        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (File file : allFiles) {
            if (!file.getName().endsWith("final")) {
                continue;
            }

//            executor.execute(new LoadNodes(file.getAbsolutePath()));
            new LoadNodes(file.getAbsolutePath()).run();
        }
//        executor.shutdown();
//        while (!executor.isTerminated()) {
//        }


    }

    private static String[] getNodeLocation(String id) {
        List<Double> location = nodesLocation.get(id);
        if (location == null) {
            System.out.println(id);
        }
        return new String[]{location.get(0).toString(), location.get(1).toString()};
    }


}
