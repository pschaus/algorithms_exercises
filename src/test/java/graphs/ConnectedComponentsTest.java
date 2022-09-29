package graphs;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class ConnectedComponentsTest {

    @RunWith(Parameterized.class)
    public static class TestCorrectness {

        final Instance instance;

        @Parameterized.Parameters(name = "{0}")
        public static Collection data() {
            LinkedList<Object[]> coll = new LinkedList<>();
            for (int i = 0; i < 7; i++) {
                String name = "data/graphs.ConnectedComponents/in_corr_" + i;
                coll.add(new Object[]{name, new Instance(name)});
            }
            return coll;
        }

        public TestCorrectness(String name, Instance instance) {
            this.instance = instance;
        }

        @Test
        public void test() throws Exception {
            int student_solution = ConnectedComponents.numberOfConnectedComponents(instance.graph);
            assertEquals(student_solution, instance.solutions);
        }


    }

    @RunWith(Parameterized.class)
    public static class TestCycle {

        final Instance instance;

        @Parameterized.Parameters(name = "{0}")
        public static Collection data() {
            LinkedList<Object[]> coll = new LinkedList<>();
            for (int i = 0; i < 2; i++) {
                String name = "data/graphs.ConnectedComponents/in_cycl_" + i;
                coll.add(new Object[]{name, new Instance(name)});
            }
            return coll;
        }

        public TestCycle(String name, Instance instance) {
            this.instance = instance;
        }

        @Test
        public void test() throws Exception {
            int student_solution = ConnectedComponents.numberOfConnectedComponents(instance.graph);
            assertEquals(student_solution, instance.solutions);
        }


    }


    @RunWith(Parameterized.class)
    public static class TestComplexity {

        final Instance instance;

        @Parameterized.Parameters(name = "{0}")
        public static Collection data() {
            LinkedList<Object[]> coll = new LinkedList<>();
            for (int i = 0; i < 2; i++) {
                String name = "data/graphs.ConnectedComponents/in_compl_" + i;
                coll.add(new Object[]{name, new Instance(name)});
            }
            return coll;
        }

        public TestComplexity(String name, Instance instance) {
            this.instance = instance;
        }

        @Test(timeout = 3000)
        public void test() throws Exception {
            int student_solution = ConnectedComponents.numberOfConnectedComponents(instance.graph);
            assertEquals(student_solution, instance.solutions);
        }


    }


    static class Instance {

        ConnectedComponents.Graph graph;

        int solutions;

        public Instance(String file) {

            try {
                Scanner dis = new Scanner(new FileInputStream(file));
                String line = dis.nextLine();
                String[] base_info = line.split(" ");
                graph = new ConnectedComponents.Graph(Integer.parseInt(base_info[0]));
                int E = Integer.parseInt(base_info[1]);
                for (int i = 0; i < 2 * E; i++) {
                    line = dis.nextLine();
                    String[] edge = line.split(" ");
                    graph.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
                }
                solutions = dis.nextInt();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
