package utils;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<Integer>[] edges;

    public Graph(int E)
    {
        this.edges = (ArrayList<Integer>[]) new ArrayList[E];
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
