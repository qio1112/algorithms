package graph;

import java.util.*;
import java.util.stream.Collectors;

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

    public static boolean hasCycleDfs(Graph graph, boolean isDirected) {

        Set<Vertex> vertices = graph.getVertices();
        Set<Vertex> remaining = new HashSet<>(vertices);
        Set<Vertex> visited = new HashSet<>();
        Stack<Vertex> stack = new Stack<>();

        while (remaining.size() > 0) {
            Vertex start = remaining.iterator().next();
            if (isDirected) {
                if (hasCycleDirectedDfsHelper(graph, visited, stack, start)) {
                    System.out.println("Has cycle");
                    return true;
                }
            } else {
                if (hasCycleUndirectedDfsHelper(graph, visited, start, null)) {
                    System.out.println("Has cycle");
                    return true;
                }
            }
            remaining.removeAll(visited);
            visited = new HashSet<>();
        }
        System.out.println("No cycle");
        return false;
    }

    // the stack is the path of current recursion.
    // directed graph needs this path because a visited vertex may not be visitable from the current vertex
    // but in undirected graph, a visited vertex is always visitable from the current vertex
    private static boolean hasCycleDirectedDfsHelper(Graph graph, Set<Vertex> visited, Stack<Vertex> stack, Vertex v) {

        // if the same vertex is in the recursion path, that means there's a cycle
        if (stack.contains(v)) {
            return true;
        }

        // if the vertex is not int the stack but visited, that means there won't be any cycle including this vertex
        // because if there is, the cycle should be detected when the first time visiting this vertex
        if (visited.contains(v)) {
            return false;
        }

        visited.add(v);
        stack.push(v);
        Set<Vertex> adjVertices = graph.getAdjacentVertices(v);
        for (Vertex adjV : adjVertices) {
            if (hasCycleDirectedDfsHelper(graph, visited, stack, adjV)) {
                return true;
            }
        }
        stack.pop();
        return false;
    }

    // cycle detection helper for undirected graph
    // no need to generate a stack, a parent is enough for avoid the cycle between two linked vertices
    private static boolean hasCycleUndirectedDfsHelper(Graph graph, Set<Vertex> visited, Vertex v, Vertex parent) {

        if (visited.contains(v)) {
            return true;
        }

        visited.add(v);
        Set<Vertex> adjVertices = graph.getAdjacentVertices(v);
        for (Vertex adjV : adjVertices) {
            if (!adjV.equals(parent)) {
                if (hasCycleUndirectedDfsHelper(graph, visited, adjV, v)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Map<Vertex, Integer> getIndegree(Graph graph) {

        Set<Vertex> vertices = graph.getVertices();
        Map<Vertex, Integer> indegrees = vertices.stream()
                .collect(Collectors.toMap(v -> v, v -> 0));
        List<Edge> edges = graph.getEdges();
        edges.stream()
                .forEach(edge -> {
                    indegrees.put(edge.getTo(), indegrees.get(edge.getTo()) + 1);
                });
        return indegrees;
    }

    // use backtrack to get all topological sorts of a acyclic directed graph
//    public static List<List<Vertex>> getAllTopologicalSorts(Graph graph) {
//
//        if (hasCycleDfs(graph, true)) {
//            throw new IllegalArgumentException("Input graph must be acyclic");
//        }
//
//        List<List<Vertex>> result = new ArrayList<>();
//
//    }
}
