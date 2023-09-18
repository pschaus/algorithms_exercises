package graphs;

// BEGIN STRIP
import java.util.ArrayList;
// END STRIP

/**
 * Implement the Digraph.java interface in
 * the Digraph.java class using an adjacency list
 * data structure to represent directed graphs.
 */
public class Digraph {

    // BEGIN STRIP
    private int V;
    private int E;
    private ArrayList<ArrayList> adj;

    // END STRIP

    public Digraph(int V) {
        // TODO
        // BEGIN STRIP
        this.V = V;
        this.E = 0;
        adj = new ArrayList<ArrayList>(V);
        for (int i = 0; i< V; i++){
            adj.add(new ArrayList<>());
        }
        // END STRIP
    }

    /**
     * The number of vertices
     */
    public int V() {
        // TODO
        // STUDENT return -1;
        // BEGIN STRIP
        return V;
        // END STRIP
    }

    /**
     * The number of edges
     */
    public int E() {
        // TODO
        // STUDENT return -1;
        // BEGIN STRIP
        return E;
        // END STRIP
    }

    /**
     * Add the edge v->w
     */
    public void addEdge(int v, int w) {
        // TODO
        // BEGIN STRIP
        ArrayList current_successors = this.adj.get(v);
        current_successors.add(w);
        E++;
        // END STRIP
    }

    /**
     * The nodes adjacent to node v
     * that is the nodes w such that there is an edge v->w
     */
    public Iterable<Integer> adj(int v) {
        // TODO
        // STUDENT return null;
        // BEGIN STRIP
        return (Iterable<Integer>) adj.get(v);
        // END STRIP
    }

    /**
     * A copy of the digraph with all edges reversed
     */
    public Digraph reverse() {
        // TODO
        // STUDENT return null;
        // BEGIN STRIP
        Digraph R = new Digraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                R.addEdge(w,v);

            }
        }
        return R;
        // END STRIP
    }

}
