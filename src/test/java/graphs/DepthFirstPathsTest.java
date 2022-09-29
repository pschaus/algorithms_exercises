package graphs;

import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class DepthFirstPathsTest {

    @Test
    @Grade(value = 25)
    public void testSimple() {
        String message = "Test [0-1, 0-2, 0-3, 0-4] with 1 as source";
        DepthFirstPaths.Graph graph = new DepthFirstPaths.Graph(5);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(0, 4);

        DepthFirstPaths dfs = new DepthFirstPaths(graph, 1);

        assertTrue(message, dfs.hasPathTo(0));
        assertTrue(message, dfs.hasPathTo(1));
        assertTrue(message, dfs.hasPathTo(2));
        assertTrue(message, dfs.hasPathTo(3));
        assertTrue(message, dfs.hasPathTo(4));
    }

    @Test
    @Grade(value = 25)
    public void testDisconnected() {
        String message = "Test [0-1, 1-2, 3-4] with 1 as source";
        DepthFirstPaths.Graph graph = new DepthFirstPaths.Graph(5);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);

        DepthFirstPaths dfs = new DepthFirstPaths(graph, 1);

        assertTrue(message, dfs.hasPathTo(0));
        assertTrue(message, dfs.hasPathTo(2));
        assertFalse(message, dfs.hasPathTo(3));
        assertFalse(message, dfs.hasPathTo(4));
    }

    @Test
    @Grade(value = 25)
    public void testDiconnectedBis() {
        String message = "Test [0-1, 1-2, 3-4,4-5,5-6,5-7,7-8, 9-10,10-11,11-12] with 8 as source";
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

        assertFalse(message, dfs.hasPathTo(0));
        assertFalse(message, dfs.hasPathTo(1));
        assertFalse(message, dfs.hasPathTo(2));

        assertTrue(message, dfs.hasPathTo(3));
        assertTrue(message, dfs.hasPathTo(4));
        assertTrue(message, dfs.hasPathTo(5));
        assertTrue(message, dfs.hasPathTo(6));
        assertTrue(message, dfs.hasPathTo(7));

        assertFalse(message, dfs.hasPathTo(9));
        assertFalse(message, dfs.hasPathTo(10));
        assertFalse(message, dfs.hasPathTo(11));
        assertFalse(message, dfs.hasPathTo(12));
    }

    @Test
    @Grade(value = 25)
    public void testLoop() {
        String message = "Test [0-1, 1-2, 2-3, 3-4, 4-0] with 0 as source";
        DepthFirstPaths.Graph graph = new DepthFirstPaths.Graph(6);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 0);

        DepthFirstPaths dfs = new DepthFirstPaths(graph, 0);

        assertTrue(message, dfs.hasPathTo(4));
    }

}
