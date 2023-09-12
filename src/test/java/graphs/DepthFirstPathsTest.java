package graphs;

import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Grade
public class DepthFirstPathsTest {

    @Test
    @Grade(value = 1)
    @GradeFeedback(message="Test [0-1, 0-2, 0-3, 0-4] with 1 as source")
    public void testSimple() {
        DepthFirstPaths.Graph graph = new DepthFirstPaths.Graph(5);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(0, 4);

        DepthFirstPaths dfs = new DepthFirstPaths(graph, 1);

        assertTrue(dfs.hasPathTo(0));
        assertTrue(dfs.hasPathTo(1));
        assertTrue(dfs.hasPathTo(2));
        assertTrue(dfs.hasPathTo(3));
        assertTrue(dfs.hasPathTo(4));
    }

    @Test
    @Grade(value = 1)
    @GradeFeedback(message="Test [0-1, 1-2, 3-4] with 1 as source")
    public void testDisconnected() {
        DepthFirstPaths.Graph graph = new DepthFirstPaths.Graph(5);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);

        DepthFirstPaths dfs = new DepthFirstPaths(graph, 1);

        assertTrue(dfs.hasPathTo(0));
        assertTrue(dfs.hasPathTo(2));
        assertFalse(dfs.hasPathTo(3));
        assertFalse(dfs.hasPathTo(4));
    }

    @Test
    @Grade(value = 1)
    @GradeFeedback(message="Test [0-1, 1-2, 3-4,4-5,5-6,5-7,7-8, 9-10,10-11,11-12] with 8 as source")
    public void testDiconnectedBis() {
        DepthFirstPaths.Graph graph = new DepthFirstPaths.Graph(13);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 6);
        graph.addEdge(5, 7);
        graph.addEdge(7, 8);
        graph.addEdge(9, 10);
        graph.addEdge(10, 11);
        graph.addEdge(11, 12);

        DepthFirstPaths dfs = new DepthFirstPaths(graph, 8);

        assertFalse(dfs.hasPathTo(0));
        assertFalse(dfs.hasPathTo(1));
        assertFalse(dfs.hasPathTo(2));

        assertTrue(dfs.hasPathTo(3));
        assertTrue(dfs.hasPathTo(4));
        assertTrue(dfs.hasPathTo(5));
        assertTrue(dfs.hasPathTo(6));
        assertTrue(dfs.hasPathTo(7));

        assertFalse(dfs.hasPathTo(9));
        assertFalse(dfs.hasPathTo(10));
        assertFalse(dfs.hasPathTo(11));
        assertFalse(dfs.hasPathTo(12));
    }

    @Test
    @Grade(value = 25)
    @GradeFeedback(message="Test [0-1, 1-2, 2-3, 3-4, 4-0] with 0 as source")
    public void testLoop() {
        DepthFirstPaths.Graph graph = new DepthFirstPaths.Graph(6);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 0);

        DepthFirstPaths dfs = new DepthFirstPaths(graph, 0);

        assertTrue(dfs.hasPathTo(4));
    }

}
