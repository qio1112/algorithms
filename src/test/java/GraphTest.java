import graph.Edge;
import graph.Graph;
import graph.GraphUtils;
import graph.Vertex;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GraphTest {

    @Test
    public void testGenerateGraph() {
        System.out.println(Graph.generateGraph1());
        System.out.println(Graph.generateUndirectedGraph1());
        System.out.println(Graph.generateDirectedGraph1());
    }

    @Test
    public void testBfs() {
        GraphUtils.bfs(Graph.generateUndirectedGraph1(), Graph.generateUndirectedGraph1().getHead());
        GraphUtils.bfs(Graph.generateDirectedGraph1(),Graph.generateDirectedGraph1().getHead());
    }

    @Test
    public void testBfsWithUnlinked() {
        GraphUtils.bfsWithUnlinked(Graph.generateUndirectedGraph1());
        GraphUtils.bfsWithUnlinked(Graph.generateDirectedGraph1());
    }

    @Test
    public void testDfsRecursive() {
        GraphUtils.dfsRecursive(Graph.generateUndirectedGraph1(), Graph.generateUndirectedGraph1().getHead());
        GraphUtils.dfsRecursive(Graph.generateDirectedGraph1(), Graph.generateDirectedGraph1().getHead());
    }

    @Test
    public void testDfsRecursiveWithUnlinked() {
        GraphUtils.dfsRecursiveWithUnlinked(Graph.generateUndirectedGraph1());
        GraphUtils.dfsRecursiveWithUnlinked(Graph.generateDirectedGraph1());
    }

    @Test
    public void testDfsStacke() {
        GraphUtils.dfsStack(Graph.generateUndirectedGraph1(), Graph.generateUndirectedGraph1().getHead());
        GraphUtils.dfsStack(Graph.generateDirectedGraph1(), Graph.generateDirectedGraph1().getHead());
    }

    @Test
    public void testHasCycleDfs() {
        GraphUtils.hasCycleDfs(Graph.generateUndirectedGraph1(), false);
        GraphUtils.hasCycleDfs(Graph.generateUndirectedGraph2(), false);
        GraphUtils.hasCycleDfs(Graph.generateDirectedGraph1(), true);
        GraphUtils.hasCycleDfs(Graph.generateDirectedGraph2(), true);

        Graph dg2 = Graph.generateDirectedGraph2();
        dg2.addEdge(7, 7, null);
        System.out.println(dg2);
        GraphUtils.hasCycleDfs(dg2, true);

        GraphUtils.hasCycleDirectedKahn(Graph.generateDirectedGraph1());
        GraphUtils.hasCycleDirectedKahn(Graph.generateDirectedGraph2());
        GraphUtils.hasCycleDirectedKahn(Graph.generateDirectedGraph3());
    }

    @Test
    public void testIndegrees() {
        Map<Vertex, Integer> indegrees = GraphUtils.getIndegree(Graph.generateDirectedGraph1());
        Iterator<Vertex> itr = indegrees.keySet().iterator();
        while (itr.hasNext()) {
            Vertex v = itr.next();
            System.out.println(v + ": " + indegrees.get(v));
        }
    }

    @Test
    public void testGetAllTopologicalSorts() {
        GraphUtils.getAllTopologicalSorts(Graph.generateDirectedGraph3());
        GraphUtils.getTopologicalSortsKahn(Graph.generateDirectedGraph3());
    }

    @Test
    public void testGetNumOfWaysInUndirectedGraphDfs() {
        Graph g = Graph.generateUndirectedGraph1();
        Vertex source = new Vertex(1);
        Vertex dest1 = new Vertex(2);
        Vertex dest2 = new Vertex(5);
        Vertex dest3 = new Vertex(10);

        assertEquals(GraphUtils.getNumOfWaysInUndirectedGraphDfs(g, source, dest1), 5);
        System.out.println();
        assertEquals(GraphUtils.getNumOfWaysInUndirectedGraphDfs(g, source, dest2), 6);
        System.out.println();
        assertEquals(GraphUtils.getNumOfWaysInUndirectedGraphDfs(g, source, dest3), 0);
    }

    @Test
    public void testGetArticulationVertices() {
        Set<Vertex> result = GraphUtils.getArticulationVertices(Graph.generateUndirectedGraph3()); // 2,4,6,9,10
        Iterator<Vertex> itr = result.iterator();
        while (itr.hasNext()) {
            Vertex v = itr.next();
            System.out.println(v);
        }
    }

    @Test
    public void testGetBridges() {
        Set<Edge> result = GraphUtils.getBridges(Graph.generateUndirectedGraph4());
        Set<Edge> result2 = GraphUtils.getBridges(Graph.generateUndirectedGraph3());
        Iterator<Edge> itr = result.iterator();
        while (itr.hasNext()) {
            Edge e = itr.next();
            System.out.println(e);
        }
        System.out.println("=============");
        Iterator<Edge> itr2 = result2.iterator();
        while (itr2.hasNext()) {
            Edge e = itr2.next();
            System.out.println(e);
        }
    }
}
