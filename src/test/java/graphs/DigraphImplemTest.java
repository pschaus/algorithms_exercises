package graphs;

import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.Digraph;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

class DigraphImplemCorrect implements Digraph {

    private List<Integer>[] outEdges;
    private int nE = 0;

    public DigraphImplemCorrect(int V) {
        outEdges = new List[V];
        for (int i = 0; i < V; i++) {
            outEdges[i] = new LinkedList<>();
        }
    }

    /**
     * The number of vertices
     */
    public int V() {
        return outEdges.length;
    }

    /**
     * The number of edges
     */
    public int E() {
        return nE;
    }

    /**
     * Add the edge v->w
     */
    public void addEdge(int v, int w) {
        outEdges[v].add(w);
        nE++;
    }

    /**
     * The nodes adjacent to edge v
     */
    public Iterable<Integer> adj(int v) {
        return outEdges[v];
    }

    /**
     * A copy of the digraph with all edges reversed
     */
    public Digraph reverse() {
        Digraph g = new DigraphImplemCorrect(V());
        for (int v = 0; v < outEdges.length; v++) {
            for (int w : adj(v)) {
                g.addEdge(w, v);
            }
        }
        return g;
    }


}


@RunWith(Enclosed.class)
public class DigraphImplemTest {

    @RunWith(Parameterized.class)
    public static class TestDigraphComplexity {
        private Digraph student;
        private Digraph expected;

        public TestDigraphComplexity(Digraph student, Digraph expected) {
            this.student = student;
            this.expected = expected;
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


        public void assertEqualsGraph(Digraph g1, Digraph g2) {
            assertEquals("same #nodes", g1.V(), g2.V());
            assertEquals("same #edges", g1.E(), g2.E());
            for (int i = 0; i < g1.V(); i++) {
                assertEqualsIterable(g1.adj(i), g2.adj(i));
            }
        }


        @Test(timeout = 500)
        @Grade(value = 25)
        public void sameRevert() {
            assertEqualsGraph(student.reverse(), expected.reverse());
        }


        @Parameterized.Parameters
        public static List<Object[]> data() throws IOException {
            List<Object[]> data = new ArrayList<>();

            int n = 10000;

            Digraph student1 = new DigraphImplem(n);
            Digraph correct1 = new DigraphImplemCorrect(n);

            for (int k = 0; k < n; k++) {

                student1.addEdge(k, (k + 1) % n);
                correct1.addEdge(k, (k + 1) % n);

            }
            data.add(new Object[]{student1, correct1});

            Digraph student2 = new DigraphImplem(n);
            Digraph correct2 = new DigraphImplemCorrect(n);

            for (int k = 1; k < n; k++) {

                student2.addEdge(0, k);
                correct2.addEdge(0, k);

            }
            data.add(new Object[]{student2, correct2});


            return data;
        }
    }


    @RunWith(Parameterized.class)
    public static class TestDigraphEval {
        private Digraph student;
        private Digraph expected;

        public TestDigraphEval(Digraph student, Digraph expected) {
            this.student = student;
            this.expected = expected;
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


        public void assertEqualsGraph(Digraph g1, Digraph g2) {
            assertEquals("same #nodes", g1.V(), g2.V());
            assertEquals("same #edges", g1.E(), g2.E());
            for (int i = 0; i < g1.V(); i++) {
                assertEqualsIterable(g1.adj(i), g2.adj(i));
            }
        }

        @Test
        @Grade(value = 0.5)
        public void sameGraph() {
            assertEqualsGraph(student, expected);
        }

        @Test
        @Grade(value = 0.5)
        public void sameRevert() {
            assertEqualsGraph(student.reverse(), expected.reverse());
        }

        @Parameterized.Parameters
        public static List<Object[]> data() throws IOException {
            List<Object[]> data = new ArrayList<>();
            Random r1 = new Random();
            Random r2 = new Random(r1.nextInt(4));

            for (int i = 0; i < 50; i++) {

                boolean[][] matrix = new boolean[10][10];

                Digraph student = new DigraphImplem(10);
                Digraph correct = new DigraphImplemCorrect(10);

                for (int k = 0; k < 20; k++) {
                    int v = r2.nextInt(10);
                    int w = r2.nextInt(10);
                    if (v != w && !matrix[v][w]) {
                        student.addEdge(v, w);
                        correct.addEdge(v, w);
                        matrix[v][w] = true;
                    }
                }
                data.add(new Object[]{student, correct});
            }
            return data;
        }
    }

}
