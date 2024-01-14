package graphs;

import java.util.*;

/**
 * Author: Alexis Englebert
 * You are the chief operator of a new power plant in the new city of Louvain-La-Neuve.
 * Unfortunately, the engineers have not provided you with the plans for the city's electrical network.
 * All you know is the size of the electrical wires between each city.
 * You are well aware that the cost associated with the electrical wires is enormous.
 * Therefore, you want to minimize these costs throughout the entire electrical network. In a such way
 * that each city is connected by one wire.
 *
 *                                   ┌───────┐
 *                                   │       │
 *                                   │   1   │
 *                            ┌──────┤       ├─────┐
 *                            │      └───────┘     │
 *                       (5)  │                    │ (20)
 *                            │                    │
 *                            │                    │
 *                         ┌──┴────┐          ┌────┴──┐
 *                         │       │          │       │
 *                         │   2   ├──────────┤   3   │
 *                         │       │   (10)   │       │
 *                         └───────┘          └───────┘
 *
 * In the given network above the minimum cost is 15 (5 + 10)
 *
 */

public class Electricity {

    // BEGIN STRIP
    private int min_cost = 0;

    // END STRIP

    /**
     * @param network list of edges in the network
     */
    public Electricity(List<Edge> network) {
        //TODO

        // BEGIN STRIP
        UnionFind uf = new UnionFind(network.size() + 1);

        network.sort(Comparator.comparingInt(a -> a.weight));

        for (Edge e : network) {
            int u = uf.find(e.source);
            int v = uf.find(e.dest);

            if( u != v ) {
                uf.union(u, v);
                min_cost += e.weight;
            }
        }
        // END STRIP
    }

    /**
     * Returns the minimum cost in the network
     * @returns minimum cost in the network
     */
    int getMinCost() {
        // TODO
        // STUDENT return -1;
        // BEGIN STRIP
         return this.min_cost;
        // END STRIP
    }

    static class Edge {
        int source;
        int dest;
        int weight;

        Edge(int s, int d, int w) {
            this.source = s;
            this.dest = d;
            this.weight = w ;
        }

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
