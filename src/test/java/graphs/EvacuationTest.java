package graphs;

import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.javagrader.TestResultStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Grade
public class EvacuationTest {



    @Test
    @Grade(value = 1)
    @GradeFeedback(message="Your algorithm does not return the correct result on the given example. Debug it locally", on=TestResultStatus.FAIL)
    public void testSimpleUniWeights1() {
        //  0 -- 1 -- 2 -- 4
        //       \   /
        //        \ /
        //         3
        int[][] graph = {
                {0, 1, 0, 0, 0},
                {1, 0, 1, 1, 0},
                {0, 1, 0, 1, 1},
                {0, 1, 1, 0, 0},
                {0, 0, 1, 0, 0}
        };

        int[] exits1 = {0, 3};
        int[] result1 = Evacuation.findShortestPaths(graph, exits1);
        assertTrue(Arrays.equals(new int[]{-1, 0, 3, -1, 2}, result1) ||
                Arrays.equals(new int[]{-1, 3, 3, -1, 2}, result1));

        int[] exits2 = {0, 4};
        int[] result2 = Evacuation.findShortestPaths(graph, exits2);
        assertTrue(Arrays.equals(new int[]{-1, 0, 4, 1, -1}, result2) ||
                Arrays.equals(new int[]{-1, 0, 4, 2, -1}, result2));

        int[] exits3 = {3};
        int[] result3 = Evacuation.findShortestPaths(graph, exits3);
        assertArrayEquals(new int[]{1, 3, 3, -1, 2}, result3);

    }

    @Test
    @Grade(value = 1)
    @GradeFeedback(message="Your algorithm does not return the correct result on the given example. Debug it locally", on=TestResultStatus.FAIL)
    public void testSimpleUniWeights2() {
        // 3 -- 4
        // | \  |
        // |  \ |
        // 1    2
        //  \  /
        //   0
        int[][] graph = {
                {0, 1, 1, 0, 0},
                {1, 0, 0, 1, 0},
                {1, 0, 0, 1, 1},
                {0, 1, 1, 0, 1},
                {0, 0, 1, 1, 0}
        };

        int[] exits1 = {2};
        int[] result1 = Evacuation.findShortestPaths(graph, exits1);
        assertTrue(Arrays.equals(new int[]{2, 0, -1, 2, 2}, result1) ||
                Arrays.equals(new int[]{2, 3, -1, 2, 2}, result1));

        int[] exits2 = {3};
        int[] result2 = Evacuation.findShortestPaths(graph, exits2);
        assertTrue(Arrays.equals(new int[]{1, 3, 3, -1, 3}, result2) ||
                Arrays.equals(new int[]{2, 3, 3, -1, 3}, result2));

        int[] exits3 = {0,2};
        int[] result3 = Evacuation.findShortestPaths(graph, exits3);
        assertArrayEquals(new int[]{-1, 0, -1, 2, 2}, result3);
    }

    @Test
    @Grade(value = 1)
    @GradeFeedback(message="Your algorithm does not return the correct result on the given example. Debug it locally", on=TestResultStatus.FAIL)
    public void testSimpleVariousWeights3() {
        // 0 -- 1-- 4
        // |      /
        // 2    /
        // |   /
        // | /
        // 3

        int[][] graph = {
                {0, 1, 1, 0, 0},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 1, 0},
                {0, 0, 1, 0, 8},
                {0, 1, 0, 8, 0}
        };

        int[] exits1 = {4};
        int[] result1 = Evacuation.findShortestPaths(graph, exits1);
        assertArrayEquals(new int[]{1, 4, 0, 2, -1}, result1);

    }

    @Test
    @Grade(value = 1)
    @GradeFeedback(message="Your algorithm does not return the correct result on the given example. Debug it locally", on=TestResultStatus.FAIL)
    public void testSimpleVariousWeights1() {
        //  0 -- 1 -- 2 -- 4
        //       \   /
        //        \ /
        //         3
        int[][] graph = {
                {0, 20, 0, 0, 0},
                {20, 0, 1, 1, 0},
                {0, 1, 0, 1, 1},
                {0, 1, 1, 0, 0},
                {0, 0, 1, 0, 0}
        };

        int[] exits1 = {0, 3};
        int[] result1 = Evacuation.findShortestPaths(graph, exits1);
        assertArrayEquals(new int[]{-1, 3, 3, -1, 2}, result1);

        int[] exits2 = {0, 4};
        int[] result2 = Evacuation.findShortestPaths(graph, exits2);
        assertArrayEquals(new int[]{-1, 2, 4, 2, -1}, result2);

        int[] exits3 = {3};
        int[] result3 = Evacuation.findShortestPaths(graph, exits3);
        assertArrayEquals(new int[]{1, 3, 3, -1, 2}, result3);
    }


    @Test
    @Grade(value = 1)
    @GradeFeedback(message="Your algorithm does not return the correct result on the given example. Debug it locally", on=TestResultStatus.FAIL)
    public void testSimpleVariousWeights2() {
        // 0 -- 1 -- 2 -- 3 -- 4 -- 5 -- 6
        int[][] graph = {
                {0, 1, 0, 0, 0, 0, 0},
                {1, 0, 2, 0, 0, 0, 0},
                {0, 2, 0, 2, 0, 0, 0},
                {0, 0, 2, 0, 1, 0, 0},
                {0, 0, 0, 1, 0, 3, 0},
                {0, 0, 0, 0, 3, 0, 3},
                {0, 0, 0, 0, 0, 3, 0}
        };
        int[] exits1 = {0, 6};
        int[] result1 = Evacuation.findShortestPaths(graph, exits1);
        assertTrue(Arrays.equals(new int[]{-1, 0, 1, 2, 3, 6, -1}, result1) ||
                Arrays.equals(new int[]{-1, 0, 1, 2, 5, 6, -1}, result1));
    }

    @Test
    @Grade(value = 1)
    @GradeFeedback(message="Your algorithm does not return the correct result on the given example. Debug it locally", on=TestResultStatus.FAIL)
    public void testNonConnectedGraph() {
        // 0 -- 1      3 -- 4
        // |   /       |    |
        // | /         |    |
        // 2           5 -- 6
        int[][] graph = {
                {0, 1, 1, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 0},
                {0, 0, 0, 1, 0, 0, 1},
                {0, 0, 0, 1, 0, 0, 1},
                {0, 0, 0, 0, 1, 1, 0}
        };
        int[] exits1 = {0, 3};
        int[] result1 = Evacuation.findShortestPaths(graph, exits1);
        assertTrue(Arrays.equals(new int[]{-1, 0, 0, -1, 3, 3, 4}, result1) ||
                Arrays.equals(new int[]{-1, 0, 0, -1, 3, 3, 5}, result1));

        int[] exits2 = {2, 3, 4};
        int[] result2 = Evacuation.findShortestPaths(graph, exits2);
        assertArrayEquals(new int[]{2, 2, -1, -1, -1, 3, 4}, result2);
    }


    // BEGIN STRIP
    @Test
    @Grade(value = 15,cpuTimeout = 1,threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @GradeFeedback(message="Your algorithm does not return the correct result on random instances with one exit and unit weights", on=TestResultStatus.FAIL)
    public void testRandomOneExitUnitWeights() {
        Random random = new Random(569667);
        for (int test = 0; test < 5; test++) {
            // build a random tree topology with one exit, where the root of the tree is the exit
            List<Integer> tmp = Stream.iterate(0, n -> n + 1).limit(100)
                    .collect(Collectors.toList());
            Collections.shuffle(tmp, random);
            LinkedList<Integer> nodes = new LinkedList<>(tmp);
            assert !nodes.isEmpty();

            int[][] graph = new int[nodes.size()][nodes.size()];
            int[] realSolution = new int[nodes.size()];
            int[] exits = {nodes.poll()};
            realSolution[exits[0]] = -1;
            LinkedList<Integer> queue = new LinkedList<>();
            queue.add(exits[0]);

            int currentNode;
            int currentChild;
            int nbrChildren;
            while (!queue.isEmpty() && !nodes.isEmpty()) {
                currentNode = queue.poll();
                nbrChildren = 1 + random.nextInt(Math.min(2, nodes.size()));
                for (int i = 0; i < nbrChildren; i++) {
                    currentChild = nodes.poll();
                    realSolution[currentChild] = currentNode;
                    graph[currentNode][currentChild] = 1;
                    graph[currentChild][currentNode] = 1;
                    queue.add(currentChild);
                }
            }

            int[] result = Evacuation.findShortestPaths(graph, exits);
            assertArrayEquals(realSolution, result);
        }
    }


    // BEGIN STRIP
    @Test
    @Grade(value = 4,cpuTimeout = 1,threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @GradeFeedback(message="Your algorithm does not return the correct result on random instances with one exit", on=TestResultStatus.FAIL)
    public void testRandomOneExit() {
        Random random = new Random(569663);
        for (int test = 0; test < 5; test++) {
            // build a random tree topology with one exit, where the root of the tree is the exit
            List<Integer> tmp = Stream.iterate(0, n -> n + 1).limit(100)
                    .collect(Collectors.toList());
            Collections.shuffle(tmp, random);
            LinkedList<Integer> nodes = new LinkedList<>(tmp);
            assert !nodes.isEmpty();

            int[][] graph = new int[nodes.size()][nodes.size()];
            int[] realSolution = new int[nodes.size()];
            int[] exits = {nodes.poll()};
            realSolution[exits[0]] = -1;
            LinkedList<Integer> queue = new LinkedList<>();
            queue.add(exits[0]);

            int currentNode;
            int currentChild;
            int nbrChildren;
            while (!queue.isEmpty() && !nodes.isEmpty()) {
                currentNode = queue.poll();
                nbrChildren = 1 + random.nextInt(Math.min(2, nodes.size()));
                for (int i = 0; i < nbrChildren; i++) {
                    currentChild = nodes.poll();
                    realSolution[currentChild] = currentNode;
                    graph[currentNode][currentChild] = 1;
                    graph[currentChild][currentNode] = 1;
                    queue.add(currentChild);
                }
            }

            //Add a random number of edges to the topology
            //in order to have a graph that is not a tree
            int suppEdgesNbr = 20 + random.nextInt(200);
            for (int edge = 0; edge < suppEdgesNbr; edge++) {
                int i;
                int j;
                // select a random edge to be added to the graph
                do {
                    i = random.nextInt(100);
                    j = random.nextInt(100);
                } while (i == j || graph[i][j] != 0);
                // The maximal depth of the tree is 100, the number of node
                // hence edges with a cost >= 100 will never be on the shortest path to an exit
                graph[i][j] = 100;
                graph[j][i] = 100;
            }

            int[] result = Evacuation.findShortestPaths(graph, exits);
            assertArrayEquals(realSolution, result);
        }
    }

    @Test
    @Grade(value = 4,cpuTimeout = 1,threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @GradeFeedback(message="Your algorithm does not return the correct result on random instances with several exits", on=TestResultStatus.FAIL)
    public void testRandomSeveralExits() {
        Random random = new Random(569663);
        for (int test = 0; test < 5; test++) {
            // build a random topology with several exits :
            // there is one tree for each exit, where the root of each tree is an exit
            List<Integer> tmp = Stream.iterate(0, n -> n + 1).limit(100)
                    .collect(Collectors.toList());
            Collections.shuffle(tmp, random);
            LinkedList<Integer> nodes = new LinkedList<>(tmp);

            int[][] graph = new int[nodes.size()][nodes.size()];
            int[] realSolution = new int[nodes.size()];
            LinkedList<Integer> queue = new LinkedList<>();
            int[] exits = new int[random.nextInt(5)];
            for (int i = 0; i < exits.length; i++) {
                exits[i] = nodes.poll();
                realSolution[exits[i]] = -1;
                queue.add(exits[i]);
            }

            int currentNode;
            int currentChild;
            int nbrChildren;
            while (!queue.isEmpty() && !nodes.isEmpty()) {
                currentNode = queue.poll();
                nbrChildren = 1 + random.nextInt(Math.min(2, nodes.size()));
                for (int i = 0; i < nbrChildren; i++) {
                    currentChild = nodes.poll();
                    realSolution[currentChild] = currentNode;
                    graph[currentNode][currentChild] = 1;
                    graph[currentChild][currentNode] = 1;
                    queue.add(currentChild);
                }
            }
            //Add a random number of edges to the topology
            //in order to have a graph that is not a tree
            int suppEdgesNbr = 20 + random.nextInt(200);
            for (int edge = 0; edge < suppEdgesNbr; edge++) {
                int i;
                int j;
                // select a random edge to be added to the graph
                do {
                    i = random.nextInt(100);
                    j = random.nextInt(100);
                } while (i == j || graph[i][j] != 0);
                // The maximal depth of the tree is 100, the number of node
                // hence edges with a cost >= 100 will never be on the shortest path to an exit
                graph[i][j] = 100;
                graph[j][i] = 100;
            }

            int[] result = Evacuation.findShortestPaths(graph, exits);
            assertArrayEquals(realSolution, result);
        }
    }

    @Test
    @Grade(value = 4,cpuTimeout = 1,threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @GradeFeedback(message="Your algorithm does not return the correct result or does not satisfy the time complexity on a large graph", on=TestResultStatus.FAIL)
    public void testTimeComplexity() {
        // the only exit is n-1 and there is just one path 0->1->2->3...->n-1
        int n = 1000;
        int [] solution = new int[n];
        solution[n-1] = -1;
        for (int i = 0; i < n-1; i++) {
            solution[i] = i+1;
        }

        int [][] graph = new int[n][n];
        for (int i = 0; i < n-1; i++) {
            graph[i][i+1] = 1;
            graph[i+1][i] = 1;
        }
        // add a few random heavy edges
        Random random = new Random(569663);
        for (int i = 0; i < n*n; i++) {
            int w = 3 * n + 100 + random.nextInt(1000);
            int n1 = random.nextInt(n);
            int n2 = random.nextInt(n);
            if (Math.abs(n1 - n2) > 1) {
                graph[n1][n2] = w;
                graph[n2][n1] = w;
            }
        }
        int [] result = Evacuation.findShortestPaths(graph,new int[]{n-1});
        assertArrayEquals(solution, result);
    }

    // END STRIP
}
