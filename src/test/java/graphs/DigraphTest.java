package graphs;

import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;


import java.util.*;
// BEGIN STRIP
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.stream.IntStream;
import java.util.stream.Stream;
// END STRIP

@Grade
public class DigraphTest {

    @Test
    @Grade(value = 1)
    public void simpleTest(){
        Digraph graph = new Digraph(6);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);

        Digraph reverse = graph.reverse();
        assertEquals(6, graph.V());
        assertEquals(4, graph.E());
        assertEquals(6, reverse.V());
        assertEquals(4, reverse.E());
        assertEqualsIterable(Arrays.asList(1), graph.adj(0));
        assertEqualsIterable(Arrays.asList(2), graph.adj(1));
        assertEqualsIterable(Arrays.asList(), graph.adj(2));
        assertEqualsIterable(Arrays.asList(4), graph.adj(3));
        assertEqualsIterable(Arrays.asList(5), graph.adj(4));
        assertEqualsIterable(Arrays.asList(), graph.adj(5));
        assertEqualsIterable(Arrays.asList(), reverse.adj(0));
        assertEqualsIterable(Arrays.asList(0), reverse.adj(1));
        assertEqualsIterable(Arrays.asList(1), reverse.adj(2));
        assertEqualsIterable(Arrays.asList(), reverse.adj(3));
        assertEqualsIterable(Arrays.asList(3), reverse.adj(4));
        assertEqualsIterable(Arrays.asList(4), reverse.adj(5));
    }

    public void assertEqualsIterable(Iterable<Integer> one, Iterable<Integer> two) {
        ArrayList<Integer> oneList = new ArrayList<>();
        for (int i : one) {
            oneList.add(i);
        }
        ArrayList<Integer> twoList = new ArrayList<>();
        for (int i : two) {
            twoList.add(i);
        }
        Integer[] oneArray = oneList.toArray(new Integer[0]);
        Arrays.sort(oneArray);
        Integer[] twoArray = twoList.toArray(new Integer[0]);
        Arrays.sort(twoArray);
        assertArrayEquals(oneArray, twoArray);
    }

    // BEGIN STRIP
    static class Instance {

        int V;
        int E;
        List<List<Integer>> expectedAdj;
        List<List<Integer>> expectedReverseAdj;


        public Instance(String file, String reverseFile) {
            try {
                Scanner dis = new Scanner(new FileInputStream(file));
                V = dis.nextInt();
                E = dis.nextInt();
                expectedAdj = new ArrayList<>(V);
                for (int i = 0; i < V; i++) {
                    expectedAdj.add(new ArrayList<>());
                }
                for (int i = 0; i < V; i++) {
                    List<Integer> succ = expectedAdj.get(i);
                    dis.nextInt();
                    int size = dis.nextInt();
                    for (int j = 0; j < size; j++) {
                        succ.add(dis.nextInt());
                    }
                }
                dis.close();
                dis = new Scanner(new FileInputStream(reverseFile));
                expectedReverseAdj = new ArrayList<>(V);
                for (int i = 0; i < V; i++) {
                    expectedReverseAdj.add(new ArrayList<>());
                }
                dis.nextInt();
                dis.nextInt();
                for (int i = 0; i < V; i++) {
                    List<Integer> succ = expectedReverseAdj.get(i);
                    dis.nextInt();
                    int size = dis.nextInt();
                    for (int j = 0; j < size; j++) {
                        succ.add(dis.nextInt());
                    }
                }


            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    static Stream<Instance> digraphProvider() {
        return IntStream.range(0, 6).mapToObj(i -> {
            String name = "data/graphs.Digraph/in_rand_dig_" + i;
            String reverseName = "data/graphs.Digraph/in_rand_rev_" + i;
            return new Instance(name, reverseName);
        });
    }
    
    @ParameterizedTest
    @Grade(value = 1)
    @MethodSource("digraphProvider")
    @GradeFeedback(message="your method does not create a digraph with the correct number of edges/nodes")
    public void testRandomNumberNodesEdges(Instance instance) {
        Digraph studentGraph = new Digraph(instance.V);
        for (int i = 0; i < instance.V; i++) {
            List<Integer> successors = instance.expectedAdj.get(i);
            for (int j = 0; j < successors.size(); j++) {
                studentGraph.addEdge(i, successors.get(j));
            }
        }
        assertEquals(instance.V, studentGraph.V());
        assertEquals(instance.E, studentGraph.E());
    }

    @ParameterizedTest
    @Grade(value = 1)
    @MethodSource("digraphProvider")
    @GradeFeedback(message="Your method does not add the correct edges")
    public void testRandomEdges(Instance instance) {                
        Digraph studentGraph = new Digraph(instance.V);
        for (int i = 0; i < instance.V; i++) {
            List<Integer> successors = instance.expectedAdj.get(i);
            for (int j = 0; j < successors.size(); j++) {
                studentGraph.addEdge(i, successors.get(j));
            }
        }
        Digraph reverseStudent = studentGraph.reverse();
        for (int i = 0; i < instance.V; i++) {
            assertEqualsIterable(instance.expectedAdj.get(i), studentGraph.adj(i));
            assertEqualsIterable(instance.expectedReverseAdj.get(i), reverseStudent.adj(i));
        }
    }
    // END STRIP
}
