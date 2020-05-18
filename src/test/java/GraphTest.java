import graph.Graph;
import graph.GraphUtils;
import graph.Vertex;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;

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
}
