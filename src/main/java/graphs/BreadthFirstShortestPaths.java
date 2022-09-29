package graphs;

import java.util.ArrayList;
import java.util.List;
// BEGIN STRIP
import java.util.LinkedList;
// END STRIP

/**
 * Consider this class, BreadthFirstShortestPaths, which computes the shortest path between
 * multiple node sources and any node in an undirected graph using a BFS path.
 * The Graph class is already implemented and here it is:
 * <p>
 * public class Graph {
 * // @return the number of vertices
 * public int V() { }
 * <p>
 * // @return the number of edges
 * public int E() { }
 * <p>
 * // Add edge v-w to this graph
 * public void addEdge(int v, int w) { }
 * <p>
 * // @return the vertices adjacent to v
 * public Iterable<Integer> adj(int v) { }
 * <p>
 * // @return a string representation
 * public String toString() { }
 * }
 * <p>
 * You are asked to implement all the TODOs of the BreadthFirstShortestPaths class.
 */
public class BreadthFirstShortestPaths {

    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked; // marked[v] = is there an s-v path
    private int[] distTo;     // distTo[v] = number of edges shortest s-v path

    /**
     * Computes the shortest path between any
     * one of the sources and very other vertex
     *
     * @param G       the graph
     * @param sources the source vertices
     */
    public BreadthFirstShortestPaths(Graph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = INFINITY;
        }
        bfs(G, sources);
    }

    // Breadth-first search from multiple sources
    private void bfs(Graph G, Iterable<Integer> sources) {
        // TODO
        // BEGIN STRIP
        LinkedList<Integer> queue = new LinkedList<>();
        for (int s : sources) {
            marked[s] = true;
            queue.add(s);
            distTo[s] = 0;
        }
        while (!queue.isEmpty()) {
            int v = queue.remove();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    queue.add(w);
                }
            }
        }
        // END STRIP
    }

    /**
     * Is there a path between (at least one of) the sources and vertex v?
     *
     * @param v the vertex
     * @return true if there is a path, and false otherwise
     */
    public boolean hasPathTo(int v) {
        // TODO
        // STUDENT return false;
        // BEGIN STRIP
        return marked[v];
        // END STRIP
    }

    /**
     * Returns the number of edges in a shortest path
     * between one of the sources and vertex v?
     *
     * @param v the vertex
     * @return the number of edges in a shortest path
     */
    public int distTo(int v) {
        // TODO
        // STUDENT return -1;
        // BEGIN STRIP
        return distTo[v];
        // END STRIP
    }

    static class Graph {

        private List<Integer>[] edges;

        public Graph(int nbNodes)
        {
            this.edges = (ArrayList<Integer>[]) new ArrayList[nbNodes];
            for (int i = 0;i < edges.length;i++)
            {
                edges[i] = new ArrayList<>();
            }
        }

        /**
         * @return the number of vertices
         */
        public int V() {
            return edges.length;
        }

        /**
         * @return the number of edges
         */
        public int E() {
            int count = 0;
            for (List<Integer> bag : edges) {
                count += bag.size();
            }

            return count/2;
        }

        /**
         * Add edge v-w to this graph
         */
        public void addEdge(int v, int w) {
            edges[v].add(w);
            edges[w].add(v);
        }

        /**
         * @return the vertices adjacent to v
         */
        public Iterable<Integer> adj(int v) {
            return edges[v];
        }
    }
}
