package graph;

import java.util.*;

public class GraphUtils {

    public static void bfs(Graph graph, Vertex start) {

        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex is not a part of the graph");
        }
        Set<Vertex> visited = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        while (queue.size() > 0) {
            Vertex v = queue.poll();
            v.print();
            Set<Vertex> adjVertices = graph.getAdjacentVertices(v);
            for (Vertex adjV : adjVertices) {
                if (!visited.contains(adjV)) {
                    visited.add(adjV);
                    queue.add(adjV);
                }
            }
        }
        System.out.println();
    }

    // need to know all vertices
    public static void bfsWithUnlinked(Graph graph) {

        Set<Vertex> vertices = graph.getVertices();
        Set<Vertex> remaining = new HashSet<>(vertices);
        Set<Vertex> visited = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();

        while (remaining.size() > 0) {
            Vertex start = remaining.iterator().next();
            queue.add(start);
            visited.add(start);
            while (queue.size() > 0) {
                Vertex v = queue.poll();
                v.print();
                Set<Vertex> adjVertices = graph.getAdjacentVertices(v);
                for (Vertex adjV : adjVertices) {
                    if (!visited.contains(adjV)) {
                        visited.add(adjV);
                        queue.add(adjV);
                    }
                }
            }
            remaining.removeAll(visited);
        }
        System.out.println();
    }

    public static void dfsRecursive(Graph graph, Vertex start) {

        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex is not a part of the graph");
        }
        Set<Vertex> visited = new HashSet<>();
        dfsHelper(graph, start, visited);
        System.out.println();
    }

    public static void dfsRecursiveWithUnlinked(Graph graph) {

        Set<Vertex> vertices = graph.getVertices();
        Set<Vertex> visited = new HashSet<>();
        Set<Vertex> remaining = new HashSet<>(vertices);
        while (visited.size() < vertices.size()) {
            Vertex start = remaining.iterator().next();
            dfsHelper(graph, start, visited);
            remaining.removeAll(visited);
        }
        System.out.println();
    }

    private static void dfsHelper(Graph graph, Vertex v, Set<Vertex> visited) {

        if (!visited.contains(v)) {
            v.print();
            visited.add(v);
            for (Vertex adjV : graph.getAdjacentVertices(v)) {
                dfsHelper(graph, adjV, visited);
            }
        }
    }

    public static void dfsStack(Graph graph, Vertex start) {

        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex is not a part of the graph");
        }
        Set<Vertex> visited = new HashSet<>();
        Stack<Vertex> stack = new Stack<>();
        visited.add(start);
        stack.add(start);
        while (stack.size() > 0) {
            Vertex v = stack.pop();
            v.print();
            Set<Vertex> adjVertices = graph.getAdjacentVertices(v);
            for (Vertex adjV : adjVertices) {
                if (!visited.contains(adjV)) {
                    visited.add(adjV);
                    stack.push(adjV);
                }
            }
        }
        System.out.println();
    }
}
