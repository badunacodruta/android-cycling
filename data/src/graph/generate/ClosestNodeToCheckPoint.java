package graph.generate;

import graph.Node;

import java.util.HashMap;
import java.util.Map;

public class ClosestNodeToCheckPoint {

    public static Map<Node, Node> find(final double[][] points) {
        Map<Node, Node> userCheckpoints = new HashMap<Node, Node>() {{
            for (double[] point : points) {
                put(new Node(point[0], point[1], false), new Node(0, 0, false));
            }
        }};


        for (Node node : Node.allNodes.values()) {
            for (Node userCheckPoint : userCheckpoints.keySet()) {
                Node closestNodeToCheckpoint = userCheckpoints.get(userCheckPoint);
                if (userCheckPoint.distance(closestNodeToCheckpoint) > userCheckPoint.distance(node)) {
                    userCheckpoints.put(userCheckPoint, node);
                }
            }
        }

        return userCheckpoints;

    }
}
