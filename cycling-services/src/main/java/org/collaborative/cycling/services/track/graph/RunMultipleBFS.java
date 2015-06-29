package org.collaborative.cycling.services.track.graph;

import java.util.*;

public class RunMultipleBFS {
//
//    public static final String dirs[] = {
//            "test.data"
////            "Ro-AtoB-Gravel.tracks.data",
////            "Ro-AtoB-Paved.tracks.data",
////            "Ro-AtoB-Unpaved.tracks.data",
////            "Ro-MTB-Gravel.tracks.data",
////            "Ro-MTB-Paved.tracks.data",
////            "Ro-MTB-Unpaved.tracks.data",
////            "Ro-Racing-Gravel.tracks.data",
////            "Ro-Racing-Paved.tracks.data",
////            "Ro-Racing-Unpaved.tracks.data"
//    };
//    public static final String BASE_PATH = "/Users/baduna/personal/android-cycling/data/resources/";
//
//    public static void main(String[] args) {
//
//        for (String dir : dirs) {
////            System.out.println(dir);
//            File folder = new File(BASE_PATH + dir);
//            File[] allFiles = folder.listFiles();
//
//            for (final File file : allFiles) {
//                if (!file.getName().endsWith(".org.collaborative.cycling.services.track.graph")) {
//                    continue;
//                }
//
//                String trackId = file.getName().replace(".org.collaborative.cycling.services.track.graph", "");
//                Graph.loadGraph(file.getPath(), trackId);
//            }
//        }
//
//        System.out.println("var org.collaborative.cycling.services.track.graph = ");
////        System.out.println(Node.allNodes.values());
////
////        if (1==1) {
////            return;
////        }
//
//        double[][] userCheckpoints = {
//                {44.44189997711198, 26.052939891815186},
//                {44.43745702634121, 26.045215129852295},
//        };
//
////        double[][] userCheckpoints = {
////                {1,13},
////                {6,10},
////                {19,5},
////        };
//
//
//        final Map<Node, Node> closestNodesToCheckpoints = ClosestNodeToCheckPoint.find(userCheckpoints);
//
//        List<Node> track = getTrack(closestNodesToCheckpoints.values());
//
//        System.out.println(track);
//    }

    public static List<Node> getTrack(List<Node> startingPoints) {
        List<Domain> domains = assignDomainsToStartingPoints(startingPoints);

        while (!allDomainsConnected(domains) && canExpandDomains(domains)) {
            expandDomains(domains);
        }

        if (!allDomainsConnected(domains)) {
            System.out.println("No path found!");
            return startingPoints;
        }

        return getPathBetweenDomains(domains);
    }

    private static List<Node> getPathBetweenDomains(List<Domain> domains) {
        Map<Domain, Domain> domainsConnections = getDomainsConnections(domains);
        Random random = new Random();
        List<Node> nodes = new ArrayList<>();

        for (Map.Entry<Domain, Domain> domaninConnection : domainsConnections.entrySet()) {
            Domain domain1 = domaninConnection.getKey();
            Domain domain2 = domaninConnection.getValue();

            List<Node> intersectionNodes = domain1.getIntersectionNodesByDomain().get(domain2);
            int intersectionNodeIndex = random.nextInt(intersectionNodes.size());
            Node intersectionNode = intersectionNodes.get(intersectionNodeIndex);

            nodes.addAll(getPathBetween(domain1, domain2, intersectionNode));
        }

        return nodes;
    }

    private static Map<Domain, Domain> getDomainsConnections(List<Domain> domains) {
        Map<Domain, Domain> domainsConnections = new HashMap<>();
        Map<Domain, Domain> dfsParentsByDomain = getDFSParents(domains);

        for (Map.Entry<Domain, Domain> domainAndParent : dfsParentsByDomain.entrySet()) {
            Domain domain = domainAndParent.getKey();
            Domain parent = domainAndParent.getValue();

            if (parent == null) {
                continue;
            }

            if ((domainsConnections.containsKey(domain) && domainsConnections.get(domain).equals(parent)) ||
                    domainsConnections.containsKey(parent) && domainsConnections.get(parent).equals(domain)) {
                continue;
            }

            domainsConnections.put(parent, domain);
        }

        return domainsConnections;
    }

    private static Map<Domain, Domain> getDFSParents(List<Domain> domains) {
        Map<Domain, Domain> dfsParents = new HashMap<>();

        if (domains.size() == 0) {
            return dfsParents;
        }

        Domain domain = getDomainWithFewestNeighbors(domains);
        Stack<Domain> dfsStack = new Stack<>();
        Set<Domain> visited = new HashSet<>();
        dfsStack.add(domain);
        visited.add(domain);
        dfsParents.put(domain, null);

        while(!dfsStack.isEmpty() && dfsParents.keySet().size() != domains.size()) {
            domain = dfsStack.pop();

            //TODO order this by distance desc
            Set<Domain> intersectingDomains = domain.getIntersectingDomains();

            for (Domain intersectingDomain : intersectingDomains) {
                if (!visited.contains(intersectingDomain)) {
                    dfsStack.add(intersectingDomain);
                    visited.add(intersectingDomain);
                    dfsParents.put(intersectingDomain, domain);
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

    private static void expandDomains(List<Domain> domains) {
        for (Domain domain : domains) {
            Node node = domain.getNextNode();
            if (node == null) {
                continue;
            }

            Set<Node> neighbors = node.getNeighbors();

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
