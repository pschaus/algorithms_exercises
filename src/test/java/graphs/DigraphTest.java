package graphs;

import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class DigraphTest {

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


    @RunWith(Parameterized.class)
    public static class TestDigraphComplexity {
        final Instance instance;


        @Parameterized.Parameters(name = "{0}")
        public static Collection data() {
            LinkedList<Object[]> coll = new LinkedList<>();
            for (int i = 0; i < 4; i++) {
                String name = "data/graphs.Digraph/in_comp_dig_" + i;
                String reverseName = "data/graphs.Digraph/in_comp_rev_" + i;
                coll.add(new Object[]{name, new Instance(name, reverseName)});
            }
            return coll;
        }


        public TestDigraphComplexity(String name, Instance instance) {
            this.instance = instance;
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
            assertArrayEquals("same adjacent nodes", oneArray, twoArray);
        }

        @Test
        @Grade(value = 50)
        public void sameGraphTest() {
            Digraph studentGraph = new Digraph(instance.V);
            for (int i = 0; i < instance.V; i++) {
                List<Integer> successors = instance.expectedAdj.get(i);
                for (int j = 0; j < successors.size(); j++) {
                    studentGraph.addEdge(i, successors.get(j));
                }
            }
            assertEqualsGraph(studentGraph, false);
            assertEqualsGraph(studentGraph.reverse(), true);
        }

        public void assertEqualsGraph(Digraph digraph, boolean reverse) {
            assertEquals("same #nodes", digraph.V(), instance.V);
            assertEquals("same #edges", digraph.E(), instance.E);
            if (!reverse) {
                for (int i = 0; i < instance.V; i++) {
                    assertEqualsIterable(digraph.adj(i), instance.expectedAdj.get(i));
                }
            } else {
                for (int i = 0; i < instance.V; i++) {
                    assertEqualsIterable(digraph.adj(i), instance.expectedReverseAdj.get(i));
                }
            }

        }

    }

    @RunWith(Parameterized.class)
    public static class TestDigraphRandom {
        final Instance instance;


        @Parameterized.Parameters(name = "{0}")
        public static Collection data() {
            LinkedList<Object[]> coll = new LinkedList<>();
            for (int i = 0; i < 6; i++) {
                String name = "data/graphs.Digraph/in_rand_dig_" + i;
                String reverseName = "data/graphs.Digraph/in_rand_rev_" + i;
                coll.add(new Object[]{name, new Instance(name, reverseName)});
            }
            return coll;
        }


        public TestDigraphRandom(String name, Instance instance) {
            this.instance = instance;
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
            assertArrayEquals("same adjacent nodes", oneArray, twoArray);
        }

        @Test
        @Grade(value = 50)
        public void sameGraphTest() {
            Digraph studentGraph = new Digraph(instance.V);
            for (int i = 0; i < instance.V; i++) {
                List<Integer> successors = instance.expectedAdj.get(i);
                for (int j = 0; j < successors.size(); j++) {
                    studentGraph.addEdge(i, successors.get(j));
                }
            }
            assertEqualsGraph(studentGraph, false);
            assertEqualsGraph(studentGraph.reverse(), true);
        }

        public void assertEqualsGraph(Digraph digraph, boolean reverse) {
            assertEquals("same #nodes", digraph.V(), instance.V);
            assertEquals("same #edges", digraph.E(), instance.E);
            if (!reverse) {
                for (int i = 0; i < instance.V; i++) {
                    assertEqualsIterable(digraph.adj(i), instance.expectedAdj.get(i));
                }
            } else {
                for (int i = 0; i < instance.V; i++) {
                    assertEqualsIterable(digraph.adj(i), instance.expectedReverseAdj.get(i));
                }
            }
        }
    }
}
