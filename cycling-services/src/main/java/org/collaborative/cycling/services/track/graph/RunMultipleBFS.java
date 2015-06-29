package org.collaborative.cycling.services.track.graph;



import org.collaborative.cycling.models.RoadType;

import java.util.*;

public class RunMultipleBFS {

    public static List<Node> getTrack(List<Node> startingPoints, Set<RoadType> roadTypes) {
        List<Domain> domains = assignDomainsToStartingPoints(startingPoints);

        while (!allDomainsConnected(domains) && canExpandDomains(domains)) {
            expandDomains(domains, roadTypes);
        }

        if (!allDomainsConnected(domains)) {
            System.out.println("No path found!");
            return startingPoints;
        }

        return getPathBetweenDomains(domains);
    }

    private static List<Node> getPathBetweenDomains(List<Domain> domains) {
        List<Map.Entry<Domain, Domain>> domainsConnections = getDomainsConnections(domains);
        Random random = new Random();
        List<Node> nodes = new ArrayList<>();

        for (Map.Entry<Domain, Domain> domaninConnection : domainsConnections) {
            Domain domain1 = domaninConnection.getKey();
            Domain domain2 = domaninConnection.getValue();

            List<Node> intersectionNodes = domain1.getIntersectionNodesByDomain().get(domain2);
            int intersectionNodeIndex = random.nextInt(intersectionNodes.size());
            Node intersectionNode = intersectionNodes.get(intersectionNodeIndex);

            nodes.addAll(getPathBetween(domain1, domain2, intersectionNode));
        }

        return nodes;
    }

    private static List<Map.Entry<Domain, Domain>> getDomainsConnections(List<Domain> domains) {
        List<Map.Entry<Domain, Domain>> domainsConnections = new ArrayList<>();
//        Map<Domain, Domain> domainsConnectionsMap = new HashMap<>();
        List<Map.Entry<Domain, Domain>> dfsParentsByDomain = getDFSParents(domains);

        for (Map.Entry<Domain, Domain> domainAndParent : dfsParentsByDomain) {
            Domain domain = domainAndParent.getKey();
            Domain parent = domainAndParent.getValue();

            if (parent == null) {
                continue;
            }

            //TODO comment this ?
//            if ((domainsConnectionsMap.containsKey(domain) && domainsConnectionsMap.get(domain).equals(parent)) ||
//                    domainsConnectionsMap.containsKey(parent) && domainsConnectionsMap.get(parent).equals(domain)) {
//                continue;
//            }

//            domainsConnectionsMap.put(parent, domain);
            domainsConnections.add(new AbstractMap.SimpleEntry<Domain, Domain>(parent, domain));
        }

        return domainsConnections;
    }

    private static List<Map.Entry<Domain, Domain>> getDFSParents(List<Domain> domains) {
        List<Map.Entry<Domain, Domain>> dfsParents = new ArrayList<>();

        if (domains.size() == 0) {
            return dfsParents;
        }

        Domain domain = getDomainWithFewestNeighbors(domains);
        Stack<Domain> dfsStack = new Stack<>();
        Set<Domain> visited = new HashSet<>();
        dfsStack.add(domain);
        visited.add(domain);
        dfsParents.add(new AbstractMap.SimpleEntry<Domain, Domain>(domain, null));

        while(!dfsStack.isEmpty() && dfsParents.size() != domains.size()) {
            domain = dfsStack.pop();

            List<Domain> intersectingDomains = new ArrayList<>(domain.getIntersectingDomains());
            final Domain finalDomain = domain;
            Collections.sort(intersectingDomains, new Comparator<Domain>() {
                @Override
                public int compare(Domain o1, Domain o2) {
                    double diff = o1.getStartingPoint().distance(finalDomain.getStartingPoint())
                            - o2.getStartingPoint().distance(finalDomain.getStartingPoint());
                    if (diff < 0) {
                        return 1;
                    }
                    if (diff > 0) {
                        return -1;
                    }
                    return 0;
                }
            });

            for (Domain intersectingDomain : intersectingDomains) {
                if (!visited.contains(intersectingDomain)) {
                    dfsStack.add(intersectingDomain);
                    visited.add(intersectingDomain);
                    dfsParents.add(new AbstractMap.SimpleEntry<Domain, Domain>(intersectingDomain, domain));
                }
            }
        }

        return dfsParents;
    }

    private static Domain getDomainWithFewestNeighbors(List<Domain> domains) {
        if (domains.size() == 0) {
            return null;
        }
        Domain domainWithFewestNeighbors = domains.get(0);

        for (Domain domain : domains) {
            if (domain.getIntersectingDomains().size() < domainWithFewestNeighbors.getIntersectingDomains().size()) {
                domainWithFewestNeighbors = domain;
            }
        }

        return domainWithFewestNeighbors;
    }


    private static Domain getClosestIntersectingDomain(Domain domain) {
        Set<Domain> intersectingDomains = domain.getIntersectingDomains();
        Iterator<Domain> iterator = intersectingDomains.iterator();
        if (!iterator.hasNext()) {
            return null;
        }

        Domain closestDomain = iterator.next();
        while (iterator.hasNext()) {
            Domain nextDomain = iterator.next();

            if (nextDomain.getStartingPoint().distance(domain.getStartingPoint())
                    < closestDomain.getStartingPoint().distance(domain.getStartingPoint())) {
                closestDomain = nextDomain;
            }
        }

        return closestDomain;
    }

    private static List<Node> getPathBetween(Domain domain1, Domain domain2, Node intersectionPoint) {
        List<Node> path = getPathBetween(domain1, intersectionPoint);
        path.remove(intersectionPoint);
        path.addAll(getPathBetween(intersectionPoint, domain2));

        return path;
    }

    private static List<Node> getPathBetween(Domain domain, Node node) {
        List<Node> path = getPathBetween(node, domain);
        Collections.reverse(path);

        return path;
    }

    private static List<Node> getPathBetween(Node node, Domain domain) {
        assert node.isVisited(domain);

        List<Node> path = new ArrayList<>();
        path.add(node);

        Node parent = node.getParent(domain);
        while (parent != null) {
            path.add(parent);
            parent = parent.getParent(domain);
        }

        return path;
    }

    private static boolean allDomainsConnected(List<Domain> domains) {
        if (domains.isEmpty()) {
            return true;
        }

        int domainsSize = domains.size();
        Domain startingDomain = domains.get(0);
        Set<Domain> connectedDomains = new HashSet<>(domainsSize);
        connectedDomains.add(startingDomain);

        List<Domain> bfsQueue = new ArrayList<>(domainsSize);
        Set<Domain> visitedSet = new HashSet<>(domainsSize);
        bfsQueue.add(startingDomain);
        visitedSet.add(startingDomain);

        while (!bfsQueue.isEmpty()) {
            Domain domain = bfsQueue.remove(0);

            Set<Domain> intersectingDomains = domain.getIntersectingDomains();
            for (Domain intersectingDomain : intersectingDomains) {
                if (!visitedSet.contains(intersectingDomain)) {
                    bfsQueue.add(intersectingDomain);
                    visitedSet.add(intersectingDomain);
                }
            }

            connectedDomains.addAll(intersectingDomains);

            if (connectedDomains.size() == domainsSize) {
                return true;
            }
        }

        return connectedDomains.size() == domainsSize;
    }

    private static boolean canExpandDomains(List<Domain> domains) {
        for (Domain domain : domains) {
            if (domain.canBeExpanded()) {
                return true;
            }
        }

        return false;
    }

    private static void expandDomains(List<Domain> domains, Set<RoadType> roadTypes) {
        for (Domain domain : domains) {
            Node node = domain.getNextNode();
            if (node == null) {
                continue;
            }

            Set<Node> neighbors = new HashSet<>(node.getNeighbors());

            //TODO: better personalization
            for (Node neighbor : neighbors) {
                if (!neighbor.hasAtLeastOneRoadType(roadTypes)) {
                    neighbors.remove(neighbor);
                }
            }
            if (neighbors.isEmpty()) {
                neighbors = new HashSet<>(node.getNeighbors());
            }

            for (Node neighbor : neighbors) {


                Set<Domain> visitingDomains = neighbor.getVisitingDomains();

                if (visitingDomains.contains(domain)) {
                    continue;
                }

                for (Domain visitingDomain : visitingDomains) {
                    domain.addIntersectionPoint(neighbor, visitingDomain);
                    visitingDomain.addIntersectionPoint(neighbor, domain);

                    visitingDomain.removeFromBorder(neighbor);
                }

                if (visitingDomains.isEmpty()) {
                    domain.addToBorder(neighbor);
                }

                neighbor.addParent(node, domain);
            }
        }
    }

    private static List<Domain> assignDomainsToStartingPoints(Collection<Node> startingPoints) {
        List<Domain> domains = new ArrayList<>();
        int domainIndex = 0;

        for (Node startingPoint : startingPoints) {
            domains.add(new Domain(++domainIndex, startingPoint));
        }

        return domains;
    }
}
