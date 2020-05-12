import graph.Graph;
import graph.GraphUtils;
import org.junit.Test;

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
}
