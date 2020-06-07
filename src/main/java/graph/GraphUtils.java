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
    public static List<List<Vertex>> getAllTopologicalSorts(Graph graph) {

        if (hasCycleDfs(graph, true)) {
            throw new IllegalArgumentException("Input graph must be acyclic");
        }

        List<List<Vertex>> result = new ArrayList<>();
        Map<Vertex, Integer> indegree = getIndegree(graph);
        Set<Vertex> visited = new HashSet<>();

        getAllTopologicalSortsHelper(graph, visited, result, new ArrayList<>(), indegree);
        System.out.println("Total " + result.size() + " sorts\n");
        return result;
    }

    private static void getAllTopologicalSortsHelper(Graph graph, Set<Vertex> visited,  List<List<Vertex>> result, List<Vertex> list, Map<Vertex, Integer> indegree) {

        if (list.size() == graph.getVertices().size()) {
            result.add(new ArrayList<>(list));
            System.out.println(String.join(",", list.stream().map(v -> v.toString()).collect(Collectors.toList())));
            return ;
        }

        for (Vertex v : graph.getVertices()) {
            if (indegree.get(v) == 0 && !visited.contains(v)) {
                list.add(v);
                visited.add(v);
                for (Vertex adjV : graph.getAdjacentVertices(v)) {
                    indegree.put(adjV, indegree.get(adjV) - 1);
                }
                getAllTopologicalSortsHelper(graph, visited, result, list, indegree);
                list.remove(list.size() - 1);
                visited.remove(v);
                for (Vertex adjV : graph.getAdjacentVertices(v)) {
                    indegree.put(adjV, indegree.get(adjV) + 1);
                }
            }
        }
    }

    /*
       acyclic directed graphs always have at least one vertex with indegree 0 and one vertex with outdegree 0
       Step-1: Compute in-degree (number of incoming edges) for each of the vertex present in the DAG and initialize the count of visited nodes as 0.
       Step-2: Pick all the vertices with in-degree as 0 and add them into a queue (Enqueue operation)
       Step-3: Remove a vertex from the queue (Dequeue operation) and then. Increment count of visited nodes by 1.
            Decrease in-degree by 1 for all its neighboring nodes.
            If in-degree of a neighboring nodes is reduced to zero, then add it to the queue.
       Step 4: Repeat Step 3 until the queue is empty.
       Step 5: If count of visited nodes is not equal to the number of nodes in the graph then the topological sort is not possible for the given graph.
     */
    public static List<Vertex> getTopologicalSortsKahn(Graph graph) {

        Map<Vertex, Integer> indegree = getIndegree(graph);
        List<Vertex> visited = new ArrayList<>();
        Queue<Vertex> queue = new LinkedList<>();
        for (Vertex v : graph.getVertices()) {
            if (indegree.get(v) == 0) {
                queue.add(v);
            }
        }
        while (queue.size() > 0) {
            Vertex v = queue.poll();
            visited.add(v);
            Set<Vertex> adjVertices = graph.getAdjacentVertices(v);
            for (Vertex adjV : adjVertices) {
                indegree.put(adjV, indegree.get(adjV) - 1);
                if (indegree.get(adjV) == 0) {
                    queue.add(adjV);
                }
            }
        }
        if (visited.size() != graph.getVertices().size()) {
            throw new IllegalArgumentException("Input graph must be acyclic");
        }
        return visited;
    }

    public static boolean hasCycleDirectedKahn(Graph graph) {

        try {
            getTopologicalSortsKahn(graph);
            System.out.println("No cycle");
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Has cycle");
            return true;
        }
    }

    // use dfs and backtrack
    public static int getNumOfWaysInUndirectedGraphDfs(Graph graph, Vertex source, Vertex dest) {

        if (!graph.getVertices().contains(source) || !graph.getVertices().contains(dest)) {
            throw new IllegalArgumentException("Input graph doesn't contain source or destination");
        }

        List<Vertex> visited = new ArrayList<>();
        visited.add(source);
        return getNumOfWaysInUndirectedGraphDfsHelper(graph, source, dest, visited);
    }

    private static int getNumOfWaysInUndirectedGraphDfsHelper(Graph graph, Vertex source, Vertex dest, List<Vertex> visited) {
        if (source.equals(dest)) {
            String path = visited.stream().map(v -> v.toString()).collect(Collectors.joining(","));
            System.out.println(path);
            return 1;
        }
        Set<Vertex> adjVertices = graph.getAdjacentVertices(source);
        int count = 0;
        for (Vertex adjV : adjVertices) {
            if (!visited.contains(adjV)) {
                visited.add(adjV);
                count += getNumOfWaysInUndirectedGraphDfsHelper(graph, adjV, dest, visited);
                visited.remove(visited.size() - 1); // backtrack
            }
        }
        return count;
    }

    // directed graph
    public static int getNumOfWaysWithKEdges(Graph graph, Vertex source, Vertex dest, int k) {
        // TODO - to be continued
        return 0;
    }

    // undirected graph
    // get all critical vertices in a graph
    private static int time = 0;
    public static Set<Vertex> getArticulationVertices(Graph graph) {

        Set<Vertex> vertices = graph.getVertices();
        Set<Vertex> visited = new HashSet<>();
        Map<Vertex, Vertex> parent = new HashMap<>(); // the parent (value) of each vertex (key) in the dfs tree
        Map<Vertex, Integer> discoveryTime = new HashMap<>(); // the earliest time of each vertex is discovered
        Map<Vertex, Integer> low = new HashMap<>(); // the time of the earliest discovered vertex which can be connected by the sub-tree of vertex (key)
        Set<Vertex> articulationVertices = new HashSet<>(); // result
        time = 0;

        for (Vertex v : vertices) {
            if (!visited.contains(v)) { // loop through all unconnected parts
                parent.put(v, null);
                getArticulationVerticesHelper(graph, v, visited, parent, discoveryTime, low, articulationVertices);
            }
        }
        return articulationVertices;
    }

    private static void getArticulationVerticesHelper(Graph graph, Vertex v, Set<Vertex> visited, Map<Vertex, Vertex> parent,
                                                     Map<Vertex, Integer> discoveryTime, Map<Vertex, Integer> low, Set<Vertex> result) {

        visited.add(v);
        discoveryTime.put(v, time); // time of discovery of the current vertex
        low.put(v, time); // set low to the same as discovery time, will update it if the sub-tree can connect to its ancestor
        time ++;

        Set<Vertex> adjVertices = graph.getAdjacentVertices(v);

        int numChildren = 0; // number of children in the dfs tree, used when the current vertex is a root
        for (Vertex adjV : adjVertices) {
            if (!visited.contains(adjV)) {
                numChildren ++;
                parent.put(adjV, v);
                getArticulationVerticesHelper(graph, adjV, visited, parent, discoveryTime, low, result);
                low.put(v, Math.min(low.get(v), low.get(adjV))); // update the low of current vertex if its sub-tree can reach some ancestor
                if (parent.get(v) == null && numChildren > 1) { // current vertex v is a root, and it has more than 1 children
                    result.add(v);
                } else if (parent.get(v) != null && low.get(adjV) >= discoveryTime.get(v)) {
                    // current vertex is not a root, and the lowest reachable vertex of its sub-tree has a smaller or equal discovery time than itself
                    result.add(v);
                }
            } else if (!adjV.equals(parent.get(v))) { // if visited, set the low of current vertex v to the smaller one between low(v) and the low of the adjV
                low.put(v, Math.min(low.get(v), low.get(adjV)));
            }
        }
    }

    // undirected graph
    // use dfs to get all critical edges in a graph
    public static Set<Edge> getBridges(Graph graph) {
        Set<Vertex> vertices = graph.getVertices();
        Set<Vertex> visited = new HashSet<>();
        Map<Vertex, Vertex> parent = new HashMap<>(); // the parent (value) of each vertex (key) in the dfs tree
        Map<Vertex, Integer> discoveryTime = new HashMap<>(); // the earliest time of each vertex is discovered
        Map<Vertex, Integer> low = new HashMap<>(); // the time of the earliest discovered vertex which can be connected by the sub-tree of vertex (key)
        Set<Edge> bridges = new HashSet<>(); // result
        time = 0;

        for (Vertex v : vertices) {
            if (!visited.contains(v)) { // loop through all unconnected parts
                parent.put(v, null);
                getBridgesHelper(graph, v, visited, parent, discoveryTime, low, bridges);
            }
        }
        return bridges;
    }

    private static void getBridgesHelper(Graph graph, Vertex v, Set<Vertex> visited, Map<Vertex, Vertex> parent,
                                         Map<Vertex, Integer> discoveryTime, Map<Vertex, Integer> low, Set<Edge> result) {
        visited.add(v);
        discoveryTime.put(v, time); // time of discovery of the current vertex
        low.put(v, time); // set low to the same as discovery time, will update it if the sub-tree can connect to its ancestor
        time ++;

        Set<Vertex> adjVertices = graph.getAdjacentVertices(v);

        for (Vertex adjV : adjVertices) {
            if (!visited.contains(adjV)) {
                parent.put(adjV, v);
                getBridgesHelper(graph, adjV, visited, parent, discoveryTime, low, result);
                low.put(v, Math.min(low.get(v), low.get(adjV))); // update the low of current vertex if its sub-tree can reach some ancestor

                // the lowest reachable vertex of sub-tree of the current vertex has a smaller discovery time than itself
                // REMIND, here it's > but not >=, because when it's =, there is a cycle in the subtree itself, in this case,
                // the vertex v is a articulation vertex, but the edge is not a bridge
                // check the undirectedGraph3, vertex 10, 11, 12
                if (low.get(adjV) > discoveryTime.get(v)) {
                    result.add(new Edge(v, adjV));
                }
            } else if (!adjV.equals(parent.get(v))) { // if visited, set the low of current vertex v to the smaller one between low(v) and the low of the adjV
                low.put(v, Math.min(low.get(v), low.get(adjV)));
            }
        }
    }
}
