package graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
// END STRIP

/**
 * Santa’s sleigh relies on a magical communication network of relay stations and bidirectional magic pathways. This
 * network ensures seamless coordination between the North Pole and Santa's team during the Christmas gift deliveries.
 * However, the network is fragile: certain pathways are critical, and their removal could disrupt the entire system by
 * splitting the network into disconnected parts.
 *
 * Santa needs your help to identify all these critical pathways so that the elves can reinforce them before the big
 * night.
 *
 * Given the network of relay stations and pathways, determine all the critical pathways whose removal would increase
 * the number of connected components in the network. At the start, the network is composed of a single connected
 * component.
 *
 * Input:
 *     * A graph represented as an adjacency list. The adjacency list is stored as an array of HashSet where each set
 *       contains the ids of the nodes that are adjacent to the node.
 *
 * Output:
 *     * A list of pairs (u,v) representing the critical pathways in the network. Since the graph is undirected, each
 *       edge appears twice in the adjacency list. However, you only need to return each edge once.
 *
 * For example:
 *     * Input: adj = [
 *                        (1, 2),
 *                        (0, 2, 3),
 *                        (0, 1, 5),
 *                        (1, 4),
 *                        (3),
 *                        (2)
 *                    ]
 *     * Output: [(2, 5), (1, 3), (3, 4)]
 *     * Explanation:
 *
 *         adj represents the graph:
 *
 *                   2 ----- 5
 *                 /  \
 *               /     \
 *              0 ----- 1 ----- 3 ----- 4
 *
 *         In this graph if the edge (2, 5) is removed then some nodes (5) are not connected to the remaining of the
 *         graph. It is also the same for the edges (1, 3) and (3, 4).
 *
 * Expected Time-Complexity: O(N^3) (N being the number of nodes)
 *
 * Hint:
 *     * It would be inefficient to take into account all the edges in the graph. Since a spanning tree must cover all
 *       the nodes in a graph, every critical edge in the original graph will be part of any spanning tree. If we only
 *       consider these edges, deleting them one by one from the original graph could make it possible to determine
 *       which ones are critical.
 */
public class CriticalPathways {

    // BEGIN STRIP
    private static int[][] computeSpanningTree(HashSet<Integer>[] adj) {
        if (adj.length == 0) {
            return new int[0][0];
        }

        Stack<Integer> stack = new Stack<>();
        Stack<int[]> path = new Stack<>();

        stack.push(0);
        path.push(new int[]{0, 0});

        HashSet<Integer> visited = new HashSet<>();
        ArrayList<int[]> edges = new ArrayList<>();
        while (!stack.isEmpty()) {
            int node = stack.pop();
            int[] edge = path.pop();
            if (!visited.contains(node)) {
                visited.add(node);
                edges.add(edge);
                for (Integer adjNode : adj[node]) {
                    if (!visited.contains(adjNode)) {
                        stack.push(adjNode);
                        path.push(new int[]{node, adjNode});
                    }
                }
            }
        }
        int[][] res = new int[edges.size()-1][2];
        for (int i = 1; i < edges.size(); i++) {
            res[i-1][0] = edges.get(i)[0];
            res[i-1][1] = edges.get(i)[1];
        }
        return res;
    }

    private static int countComponent(HashSet<Integer>[] adj) {
        int count = 0;
        HashSet<Integer> visited = new HashSet<>();
        while (visited.size() < adj.length) {
            Stack<Integer> stack = new Stack<>();

            // Find start of DFS
            for (int i = 0; i < adj.length; i++) {
                if (!visited.contains(i)) {
                    stack.push(i);
                    break;
                }
            }

            // Perform DFS
            while (!stack.isEmpty()) {
                int node = stack.pop();
                if (!visited.contains(node)) {
                    visited.add(node);
                    for (Integer adjNode : adj[node]) {
                        if (!visited.contains(adjNode)) {
                            stack.push(adjNode);
                        }
                    }
                }
            }
            count++;
        }
        return count;
    }
    // END STRIP

    /**
     * Determines all the critical pathways whose removal would increase the number of components in the network.
     *
     * @param adj A graph represented as an adjacency list. The adjacency list is stored as an array of HashSet where
     *            each set contains the ids of the nodes that are adjacent to the node.
     *
     * @return A list of pairs (u,v) representing the critical pathways in the network. Since the graph is undirected,
     *         each edge appears twice in the adjacency list. However, you only need to return each edge once.
     */
    public static int[][] findCriticalPathways(HashSet<Integer>[] adj) {
        // TODO
        // BEGIN STRIP
        int[][] spanning_tree = computeSpanningTree(adj);

        if (spanning_tree.length == 0) {
            return new int[0][2];
        }

        ArrayList<int[]> critical_edges = new ArrayList<>();
        // For each edges in spanning tree, try to remove it and check number of components
        for (int[] edge : spanning_tree) {

            // Remove edge
            adj[edge[0]].remove(edge[1]);
            adj[edge[1]].remove(edge[0]);

            // Count number of components
            int count = countComponent(adj);
            if (count > 1) {
                critical_edges.add(edge);
            }

            // Add back the edge
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }

        int[][] res = new int[critical_edges.size()][2];
        for (int i = 0; i < res.length; i++) {
            res[i] = critical_edges.get(i);
        }

        return res;
        // END STRIP
        // STUDENT return null;
    }
}
