package graph;

import java.util.*;

public class Graph {

    // from a vertex A to a vertex B may have multiple edges
    private Map<Vertex, Map<Vertex, List<Integer>>> adjList;
    private List<Edge> edges;

    public Graph(List<Edge> edges) {
        this.adjList = new HashMap<>();
        this.edges = new ArrayList<>(edges);
        edges.stream().forEach(edge -> {
            this.addDirectedVertices(edge.getFrom(), edge.getTo(), edge.getWeight());
        });
    }

    public Graph() {
        this(Collections.emptyList());
    }

    // [[from, to, weight],[1, 2, 1], [2, 3, 1], [3, 1, 2], [1, 1, 3]]
    // [[from, to], [1, 2], [2, 3], [3, 1]]
    public Graph(int[][] edgesArr) {

        this.adjList = new HashMap<>();
        this.edges = new ArrayList<>();
        boolean isWeighted = edgesArr[0].length == 3;
        Map<Integer, Vertex> vertices = new HashMap<>();
        for (int i = 0; i < edgesArr.length; i ++) {
            int[] edge = edgesArr[i];
            int weight = isWeighted ? edge[2] : 1;
            for (int j = 0; j < 2; j ++) {
                if (!vertices.containsKey(edge[j])) {
                    vertices.put(edge[j], new Vertex(edge[j]));
                }
            }
            Vertex from = vertices.get(edge[0]);
            Vertex to = vertices.get(edge[1]);
            edges.add(new Edge(from, to, weight));
        }
        edges.stream().forEach(edge -> {
            this.addDirectedVertices(edge.getFrom(), edge.getTo(), edge.getWeight());
        });
    }

    private void addDirectedVertices(Vertex from, Vertex to, Integer weight) {

        weight = weight == null ? 1 : weight;
        if (adjList.containsKey(from)) {
            if (adjList.get(from).containsKey(to)) {
                adjList.get(from).get(to).add(weight);
            } else if (to != null) {
                adjList.get(from).put(to, Collections.singletonList(weight));
            }
        } else {
            adjList.put(from, new HashMap<>());
            if (to != null) {
                adjList.get(from).put(to, Collections.singletonList(weight));
            }
        }
        if (!adjList.containsKey(to)) {
            adjList.put(to, new HashMap<>());
        }
    }

    public Vertex getHead() {

        if (adjList.size() == 0) {
            return null;
        }
        return adjList.keySet().iterator().next();
    }

    public Set<Vertex> getAdjacentVertices(Vertex v) {
        if (!adjList.keySet().contains(v)) {
            throw new IllegalArgumentException("Graph does not contain such input vertex");
        }
        return adjList.get(v).keySet();
    }

    public Map<Vertex, Map<Vertex, List<Integer>>> getAdjList() {
        return adjList;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Set<Vertex> getVertices() {
        return adjList.keySet();
    }

    public static Graph generateGraph1() {
        int[][] edgeArr = {{1, 2}, {2, 3}, {3, 4}, {4, 2}};
        return new Graph(edgeArr);
    }

    public static Graph generateUndirectedGraph1() {
        int[][] edgeArr = {{1, 2}, {2, 1}, {1, 3}, {3, 1}, {1, 4}, {4, 1}, {2, 3}, {3, 2}, {2, 4}, {4, 2}, {3, 5}, {5, 3},
                {4, 6}, {6, 4}, {5, 6}, {6, 5}, {5, 7}, {7, 5}, {6, 8}, {8, 6}, {7, 9}, {9, 7}, {10, 11}, {11, 10}};
        return new Graph(edgeArr);
    }

    public static Graph generateDirectedGraph1() {
        int[][] edgeArr = {{1, 2}, {2, 3}, {2, 6}, {3, 4}, {3, 5}, {4, 2}, {4, 4}, {4, 7}, {5, 3}, {6, 8}, {7, 5}, {7, 7},
                {8, 6}, {9, 4}, {9, 10}};
        return new Graph(edgeArr);
    }

    @Override
    public String toString() {
        if (this.adjList.size() == 0) {
            return "Empty Graph";
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Vertex, Map<Vertex, List<Integer>>> entry : adjList.entrySet()) {
            builder.append(entry.getKey().getValue() + ": ");
            for (Map.Entry<Vertex, List<Integer>> entry1 : entry.getValue().entrySet()) {
                for (Integer weight : entry1.getValue()) {
                    builder.append("(" + entry1.getKey().getValue() + ", " + weight + ")");
                }
                builder.append(", ");
            }
            builder.append("\n");
        }
        return builder.toString() + "\n";
    }
}
