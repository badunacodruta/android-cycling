package graph;

import java.util.*;

public class Node {

    public static final Map<String, Node> allNodes = new HashMap<String, Node>();

    private Set<Node> neighbors = new HashSet<Node>();


    private double x;
    private double y;

    public Node(double x, double y, boolean isTrackNode) {
        this.x = x;
        this.y = y;
        if (isTrackNode) {
            allNodes.put(getKey(x, y), this);
        }
    }

    public double distance(Node node) {
        return (x-node.x)*(x-node.x) + (y-node.y)*(y-node.y);
    }

    public void addNeighbor(Node node) {
        neighbors.add(node);
    }


    public static Node getNode(double x, double y) {
        Node node = allNodes.get(getKey(x, y));
        if (node != null) {
            return node;
        }

        return new Node(x, y, true);
    }

    private static String getKey(double x, double y) {
        return x + "_" + y;
    }

    public Set<Node> getNeighbors() {
        return neighbors;
    }

    public void clearNeighbors() {
        neighbors.clear();
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String toString() {
        return "[" + x + "," + y + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        if (Double.compare(node.x, x) != 0) return false;
        if (Double.compare(node.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
