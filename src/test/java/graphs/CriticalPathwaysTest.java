package graphs;

import org.javagrader.Grade;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.HashSet;
import java.util.Arrays;

// BEGIN STRIP
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.management.ManagementFactory;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Named.named;
// END STRIP

@Grade
public class CriticalPathwaysTest {

    private static void sortResult(int[][] result) {
        for (int i = 0; i < result.length; i++) {
            Arrays.sort(result[i]);
        }
        Arrays.sort(result, (a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            } else {
                return Integer.compare(a[1], b[1]);
            }
        });
    }

    @Test
    @Grade(value = 1)
    public void testSimple() {
        HashSet<Integer>[] adj = new HashSet[6];
        for (int i = 0; i < 6; i++) {
            adj[i] = new HashSet<>();
        }
        adj[0].add(1);
        adj[0].add(2);
        adj[1].add(0);
        adj[1].add(3);
        adj[1].add(2);
        adj[2].add(0);
        adj[2].add(1);
        adj[2].add(5);
        adj[3].add(1);
        adj[3].add(4);
        adj[4].add(3);
        adj[5].add(2);

        int[][] criticals = CriticalPathways.findCriticalPathways(adj);

        // Sort output
        sortResult(criticals);
        int[][] solution = {
                {1, 3},
                {2, 5},
                {3, 4}
        };
        assertArrayEquals(solution, criticals);
    }

    // BEGIN STRIP
    @Test
    @Grade(value = 1)
    public void testNoCriticalEdges() {
        HashSet<Integer>[] adj = new HashSet[3];
        for (int i = 0; i < 3; i++) {
            adj[i] = new HashSet<>();
        }
        adj[0].add(1);
        adj[0].add(2);
        adj[1].add(0);
        adj[1].add(2);
        adj[2].add(0);
        adj[2].add(1);

        int[][] criticals = CriticalPathways.findCriticalPathways(adj);

        // Sort output
        sortResult(criticals);
        int[][] solution = {};
        assertArrayEquals(solution, criticals);
    }

    @Test
    @Grade(value = 1)
    public void testEmpty() {
        HashSet<Integer>[] adj = new HashSet[]{};
        int[][] criticals = CriticalPathways.findCriticalPathways(adj);
        assertArrayEquals(new int[][]{}, criticals);
    }

    @Test
    @Grade(value = 1)
    public void testAllCriticalPathways() {
        HashSet<Integer>[] adj = new HashSet[6];
        for (int i = 0; i < 6; i++) {
            adj[i] = new HashSet<>();
        }
        for (int i = 0; i < 5; i++) {
            adj[i].add(i+1);
            adj[i+1].add(i);
        }
        int[][] criticals = CriticalPathways.findCriticalPathways(adj);
        sortResult(criticals);
        int[][] solution = new int[5][2];
        for (int i = 0; i < 5; i++) {
            solution[i] = new int[]{i, i+1};
        }
        assertArrayEquals(solution, criticals);
    }

    public static Instance[] instances;

    @BeforeAll
    public static void setUpClass() {
        Random rand = new Random(88541);
        instances = new Instance[10];
        for (int i = 0; i < 10; i++) {
            instances[i] = generateRandomInstance(400, rand);
        }
    }

    private static Instance generateRandomInstance(int n, Random rand) {
        int n_critical_edges = rand.nextInt(n/100) + 1;
        HashSet<Integer> edges_start_set = new HashSet<>();
        while (edges_start_set.size() < n_critical_edges) {
            int edge = rand.nextInt(n-1);
            edges_start_set.add(edge);
        }
        ArrayList<Integer> edges_start = new ArrayList<>(edges_start_set);
        Collections.sort(edges_start);

        HashSet<Integer>[] adj = new HashSet[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new HashSet<>();
        }

        int start = 0;
        for (int end : edges_start) {
            for (int i = start; i < end+1; i++) {
                for (int j = i + 1; j < end+1; j++) {
                    adj[i].add(j);
                    adj[j].add(i);
                }
            }
            adj[end+1].add(end);
            adj[end].add(end+1);
            start = end+1;
        }
        for (int i = start; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                adj[i].add(j);
                adj[j].add(i);
            }
        }
        int[][] solution = SolutionCriticalPathways.findCriticalPathways(adj);
        sortResult(solution);
        return new Instance(adj, solution);
    }

    public static Stream<Arguments> instanceProvider() {
        return IntStream.range(0, instances.length).mapToObj(i -> Arguments.of(named("Instance " + i, instances[i])));
    }

    @ParameterizedTest
    @Grade(value = 1, cpuTimeout = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm. Hint: debug on a small example or try to reduce the complexity of your algorithm")
    @MethodSource("instanceProvider")
    public void test(Instance instance) {

        long nano_start = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();

        int[][] results = CriticalPathways.findCriticalPathways(instance.adj);

        long nano_end = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
        assertTrue(nano_end - nano_start < 1_000_000_000, "Execution more than 1 CPU second");

        sortResult(results);
        assertArrayEquals(instance.solution, results);
    }

    public static class Instance {
        public HashSet<Integer>[] adj;
        public int[][] solution;

        public Instance(HashSet<Integer>[] adj, int[][] solution) {
            this.adj = adj;
            this.solution = solution;
        }
    }

    private static class SolutionCriticalPathways {

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

        public static int[][] findCriticalPathways(HashSet<Integer>[] adj) {
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
        }
    }
    // END STRIP
}
