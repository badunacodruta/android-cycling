package graph.graph;

import graph.Graph;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LoadNodes implements Runnable {
    private final File file;

    public LoadNodes(String fileName) {
        file = new File(fileName);
    }

    @Override
    public void run() {


        try {
            String text = new Scanner(file, "UTF-8").useDelimiter("\\Z").next();

            String[] lines = text.split("\n");
            for (String line : lines) {
                String[] lineData = line.split(" ");

                int startIndex = lineData.length == 3 ? 0 : 1;
                List<Double> location = Arrays.asList(Double.parseDouble(lineData[startIndex + 1]), Double.parseDouble(lineData[startIndex + 2]));
                Graph.nodesLocation.put(lineData[startIndex], location);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(file.getName());

    }
}
