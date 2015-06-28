package org.collaborative.cycling.services.track.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OptimizeGraph {

    public static void run() {
        boolean removed = true;

        while (removed) {
            System.out.println("Size1: " + Node.allNodes.size());

            removed = false;

            Iterator<Node> iterator = Node.allNodes.values().iterator();
            while (iterator.hasNext()) {
                Node node = iterator.next();
                if (node.getNeighbors().size() == 2) {
                    //middle node can be deleted removed from its neighbors
                    removeMiddleNode(node);
                    removed = true;
                }

                if (node.getNeighbors().size() == 0) {
                    iterator.remove();
                }
            }
        }
    }

    private static void removeMiddleNode(Node node) {
        List<Node> neighbors = new ArrayList<>(node.getNeighbors());
        Node neighbor1 = neighbors.get(0);
        Node neighbor2 = neighbors.get(1);

        removeNeighbor(neighbor1, node);
        removeNeighbor(neighbor2, node);
        node.getNeighbors().clear();

        neighbor1.addNeighbor(neighbor2);
        neighbor2.addNeighbor(neighbor1);
    }

    private static void removeNeighbor(Node node, Node neighbor) {
        Iterator<Node> iterator = node.getNeighbors().iterator();
        while (iterator.hasNext()) {
            Node n = iterator.next();
            if (n.equals(neighbor)) {
                iterator.remove();
            }
        }

    }
}
