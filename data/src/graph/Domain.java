package graph;

import java.util.*;

public class Domain {
    private final int id;
    private final Node startingPoint;
    private PriorityQueue<Node> border;
    private Map<Domain, List<Node>> intersectionNodesByDomain;

    public Domain(int id, final Node startingPoint) {
        this.id = id;
        this.startingPoint = startingPoint;

        border = new PriorityQueue<>(300, new Comparator<Node>() {

            @Override
            public int compare(Node o1, Node o2) {
                double diff = o1.distance(Domain.this.startingPoint) - o2.distance(Domain.this.startingPoint);
                if (diff < 0) {
                    return -1;
                }
                if (diff > 0) {
                    return 1;
                }
                return 0;
            }
        });
        border.add(this.startingPoint);

        this.startingPoint.addParent(null, this);

        intersectionNodesByDomain = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public Node getNextNode() {
        return border.poll();
    }

    public boolean canBeExpanded() {
        return !border.isEmpty();
    }

    public void addToBorder(Node node) {
        border.add(node);
    }

    public void addIntersectionPoint(Node intersectionPoint, Domain domain) {
        if (!intersectionNodesByDomain.containsKey(domain)) {
            intersectionNodesByDomain.put(domain, new ArrayList<Node>());
        }

        intersectionNodesByDomain.get(domain).add(intersectionPoint);
    }

    public Set<Domain> getIntersectingDomains() {
        return intersectionNodesByDomain.keySet();
    }

    public Map<Domain, List<Node>> getIntersectionNodesByDomain() {
        return intersectionNodesByDomain;
    }

    public PriorityQueue<Node> getBorder() {
        return border;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Domain domain = (Domain) o;

        return id == domain.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
