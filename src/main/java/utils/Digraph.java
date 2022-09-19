package utils;

public interface Digraph {

    /**
     * The number of vertices
     */
    public int V();

    /**
     * The number of edges
     */
    public int E();

    /**
     * Add the edge v->w
     */
    public void addEdge(int v, int w);

    /**
     * The nodes adjacent to edge v
     */
    public Iterable<Integer> adj(int v);

    /**
     * A copy of the digraph with all edges reversed
     */
    public Digraph reverse();


}
