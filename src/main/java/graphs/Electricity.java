package graphs;

import java.util.*;

/**
 * Author: Alexis Englebert
 * Context: You are operating a power plant in the new city of Louvain-La-Neuve,
 * but lack plans for the city's electrical network.
 * Your goal is to minimize the cost of electrical wires ensuring the city is connected with just one wire.
 *
 * The method 'minimumSpanningTreeCost' is designed to find the minimum cost to connect all cities in a given electrical network.
 * The network is represented as a graph where the nodes are the buildings, the edges are the possible connections
 * and their associated cost.
 *
 * Example:
 * Given a network with three buildings (nodes) and the cost of wires (edges) between them:
 * 0 - 1 (5), 1 - 2 (10), 0 - 2 (20)
 * The minimum cost to connect all the buildings is 15 (5 + 10).
 *
 * Note: The method assumes that the input graph is connected and the input is valid.
 */
public class Electricity {

    /**
     * @param n       The number of buildings (nodes) in the network.
     * @param edges   A 2D array where each row represents an edge in the form [building1, building2, cost].
     *                The edges are undirected so (building2, building1, cost) is equivalent to (building1, building2, cost).
     * @return       The minimum cost to connect all cities.
     */
    public static int minimumSpanningCost(int n, int [][] edges) {
        //TODO
        // STUDENT return -1;
        // BEGIN STRIP
        int minCost = 0;
        UnionFind uf = new UnionFind(n);

        Arrays.sort(edges, (a, b) -> a[2] - b[2]);

        for (int [] edge : edges) {
            int u = uf.find(edge[0]);
            int v = uf.find(edge[1]);

            if( u != v ) {
                uf.union(u, v);
                minCost += edge[2];
            }
        }
        return minCost;
        // END STRIP
    }

    // BEGIN STRIP
    static class UnionFind {
        int parent[];
        int size[];
        UnionFind(int n) {
            parent = new int[n];
            size = new int[n];

            for(int i = 0; i < n; i++ ) {
                parent[i] = i;
                size[i] = 1;
            }

        }

        int find(int a) {
             return parent[a] == a ? a : (parent[a] = find(parent[a]));
        }

        void union(int a, int b) {
            a = find(a);
            b = find(b);

            if(size[b] > size[a]) {
                int temp = a;
                a = b;
                b = temp;
            }

            parent[b] = a;
            size[a] += size[b];
        }
    }
    // END STRIP
}
