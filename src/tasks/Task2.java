package tasks;

import java.util.*;

public class Task2 {
    private static final Graph.Edge[] GRAPH = {
            new Graph.Edge("gdansk", "bydgoszcz", 1),
            new Graph.Edge("gdansk", "torun", 3),
            new Graph.Edge("bydgoszcz", "torun", 1),
            new Graph.Edge("bydgoszcz", "warszawa", 4),
            new Graph.Edge("torun", "warszawa", 1),

    };

    public static void main(String[] args) {
        Graph g = new Graph(GRAPH);
        g.distance("gdansk", "warszawa");
        System.out.println();
        g.distance("bydgoszcz", "warszawa");
    }
}

class Graph {
    private final Map<String, Vertex> graph; // mapping of vertex names to Vertex objects, built from a set of Edges

    /** One edge of the graph (only used by Graph constructor) */
    static class Edge {
        final String v1, v2;
        final int dist;
        Edge(String v1, String v2, int dist) {
            this.v1 = v1;
            this.v2 = v2;
            this.dist = dist;
        }
    }

    /** One vertex of the graph, complete with mappings to neighbouring vertices */
    public static class Vertex implements Comparable<Vertex>{
        final String name;
        int dist = Integer.MAX_VALUE; // MAX_VALUE assumed to be infinity
        Vertex previous = null;
        final Map<Vertex, Integer> neighbours = new HashMap<>();

        Vertex(String name){
            this.name = name;
        }

        private void printPath(){
            if (this == this.previous){
                System.out.printf("%s", this.name);
            }else if (this.previous == null){
                System.out.printf("%s(unreached)", this.name);
            }else{
                this.previous.printPath();
                System.out.printf(" -> %s(%d)", this.name, this.dist);
            }
        }

        private void printDist(){
            System.out.print(String.valueOf(this.dist));
        }

        public int compareTo(Vertex other){
            if (dist == other.dist)
                return name.compareTo(other.name);

            return Integer.compare(dist, other.dist);
        }

        @Override public String toString(){
            return "(" + name + ", " + dist + ")";
        }
    }

    /** Builds a graph from a set of edges */
    Graph(Edge[] edges) {
        graph = new HashMap<>(edges.length);

        //one pass to find all vertices
        for (Edge e : edges) {
            if (!graph.containsKey(e.v1)) graph.put(e.v1, new Vertex(e.v1));
            if (!graph.containsKey(e.v2)) graph.put(e.v2, new Vertex(e.v2));
        }

        //another pass to set neighbouring vertices
        for (Edge e : edges) {
            graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
            graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); // also do this for an undirected graph
        }
    }

    /** Runs dijkstra using a specified source vertex */
    private void dijkstra(String startName) {
        if (!graph.containsKey(startName)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startName);
            return;
        }
        final Vertex source = graph.get(startName);
        NavigableSet<Vertex> q = new TreeSet<>();

        // set-up vertices
        for (Vertex v : graph.values()) {
            v.previous = v == source ? source : null;
            v.dist = v == source ? 0 : Integer.MAX_VALUE;
            q.add(v);
        }

        dijkstra(q);
    }

    /** Implementation of dijkstra's algorithm using a binary heap. */
    private void dijkstra(final NavigableSet<Vertex> q) {
        Vertex u, v;
        while (!q.isEmpty()) {

            u = q.pollFirst(); // vertex with shortest distance (first iteration will return source)
            assert u != null;
            if (u.dist == Integer.MAX_VALUE) break; // we can ignore u (and any other remaining vertices) since they are unreachable

            //look at distances to each neighbour
            for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
                v = a.getKey(); //the neighbour in this iteration

                final int alternateDist = u.dist + a.getValue();
                if (alternateDist < v.dist) { // shorter path to neighbour found
                    q.remove(v);
                    v.dist = alternateDist;
                    v.previous = u;
                    q.add(v);
                }
            }
        }
    }

    /** Prints a path from the source to the specified vertex */
    private void printPath(String endName) {
        if (!graph.containsKey(endName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
            return;
        }

        graph.get(endName).printPath();
        System.out.println();
    }

    /** Prints the distance from start location to end */
    private void printDist(String endName) {
        if (!graph.containsKey(endName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
            return;
        }

        graph.get(endName).printDist();
        System.out.println();
    }
    /** Prints the path from the source to every vertex (output order is not guaranteed) */
    public void printAllPaths() {
        for (Vertex v : graph.values()) {
            v.printPath();
            System.out.println();
        }
    }

    /** Prints the route and distance from start location to end */
    public void distance(String startName, String endName) {
        dijkstra(startName);
        printPath(endName);
        System.out.print("Distance from start location to end: ");
        printDist(endName);
    }
}