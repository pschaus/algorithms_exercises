package graphs;
// BEGIN STRIP
import java.util.PriorityQueue;
// END STRIP

/**
 * In case of an emergency at the Olympic games,
 * it’s crucial to ensure that all spectators can be evacuated efficiently.
 *
 * Each venue is represented as a node in an undirected graph.
 * The pathways between them are represented as weighted edges.
 * A set of exits are also represented as nodes in the graph.
 *
 * The goal is to determine the shortest path from each venue to the nearest exit.
 *
 * To enable people to find the shortest path from any venue to the nearest exit,
 * we are going to place one directional arrow at each venue indicating the
 * next venue to follow on the shorted to reach the nearest exit from that node.
 *
 * Hint: You can use Dijkstra's algorithm with minor adaptations (starting from the exits) to solve this problem.
 *
 * The expected time-complexity if O((V + E) log V) where V is the number nodes and E is the number of edges.
 *
 * Look at the test cases for more details on the input and output format as well as some examples.
 */
public class Evacuation {

    /**
     * @param graph the graph representing the venues (and exits), pathways
     *        The graph is represented as an adjacency matrix,
     *              where graph[i][j] is the weight of the edge between i and j.
     *              If there is no edge between i and j, graph[i][j] = 0.
     * @param exits the nodes of the graph representing the exits
     * @return an array of integers, where the i-th element is the index of the next venue to visit
     * to reach the nearest exit from the i-th venue. If the i-th venue is an exit, the value is -1.
     */
    public static int[] findShortestPaths(int[][] graph, int[] exits) {
        // BEGIN STRIP
        class Node implements Comparable<Node> {
            int vertex;
            int distance;

            Node(int vertex, int distance) {
                this.vertex = vertex;
                this.distance = distance;
            }

            public int compareTo(Node other) {
                return Integer.compare(this.distance, other.distance);
            }
        }

        int[] next = new int[graph.length];
        boolean[] visited = new boolean[graph.length];
        int[] distance = new int[graph.length];

        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (int i = 0; i < graph.length; i++) {
            next[i] = -2;
            distance[i] = Integer.MAX_VALUE;
        }

        for (int exit: exits) {
            pq.add(new Node(exit, 0));
            distance[exit] = 0;
            next[exit] = -1;
        }

        Node currentNode;
        while (!pq.isEmpty()) {
            currentNode = pq.poll();
            int i = currentNode.vertex;
            int d = currentNode.distance;
            if (!visited[i]) {
                visited[i] = true;
                for (int j = 0; j < graph[i].length; j++) {
                    if (graph[i][j] > 0 && distance[j] > d + graph[j][i]) {
                        distance[j] = d + graph[j][i];
                        next[j] = i;
                        pq.add(new Node(j,distance[j]));
                    }
                }
            }
        }

        return next;
        // END STRIP
        // STUDENT return null;
    }
}
