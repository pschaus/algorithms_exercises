package graphs;

import com.github.guillaumederval.javagrading.Grade;
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
public class BreadthFirstShortestPathsTest {


    public static class TestNotParameterized {
        @Test
        @Grade(value = 20)
        public void testSimple() {
            String message = "Test [0-1, 0-2, 0-3, 0-4] with [1] as sources";
            BreadthFirstShortestPaths.Graph graph = new BreadthFirstShortestPaths.Graph(5);

            graph.addEdge(0, 1);
            graph.addEdge(0, 2);
            graph.addEdge(0, 3);
            graph.addEdge(0, 4);

            BreadthFirstShortestPaths bfs = new BreadthFirstShortestPaths(graph, Arrays.asList(1));

            assertEquals(message, 0, bfs.distTo(1));
            assertEquals(message, 1, bfs.distTo(0));
            assertEquals(message, 2, bfs.distTo(2));
            assertEquals(message, 2, bfs.distTo(3));
            assertEquals(message, 2, bfs.distTo(4));
        }

        @Test
        @Grade(value = 20)
        public void testDisconnected() {
            String message = "Test [0-1, 1-2, 3-4] with [1] as sources";
            BreadthFirstShortestPaths.Graph graph = new BreadthFirstShortestPaths.Graph(5);

            graph.addEdge(0, 1);
            graph.addEdge(1, 2);
            graph.addEdge(3, 4);

            BreadthFirstShortestPaths bfs = new BreadthFirstShortestPaths(graph, Arrays.asList(1));
            assertEquals(message, 1, bfs.distTo(0));
            assertEquals(message, 1, bfs.distTo(2));

            assertEquals(message, Integer.MAX_VALUE, bfs.distTo(3));
            assertEquals(message, Integer.MAX_VALUE, bfs.distTo(4));
        }

        @Test
        @Grade(value = 20)
        public void testLoop() {
            String message = "Test [0-1, 1-2, 2-3, 3-4, 4-5, 5-0] with [0] as sources";
            BreadthFirstShortestPaths.Graph graph = new BreadthFirstShortestPaths.Graph(6);

            graph.addEdge(0, 1);
            graph.addEdge(1, 2);
            graph.addEdge(2, 3);
            graph.addEdge(3, 4);
            graph.addEdge(4, 5);
            graph.addEdge(5, 0);

            BreadthFirstShortestPaths bfs = new BreadthFirstShortestPaths(graph, Arrays.asList(0));
            assertEquals(message, 0, bfs.distTo(0));
            assertEquals(message, 1, bfs.distTo(1));
            assertEquals(message, 2, bfs.distTo(2));
            assertEquals(message, 3, bfs.distTo(3));
            assertEquals(message, 2, bfs.distTo(4));
            assertEquals(message, 1, bfs.distTo(5));
        }

        @Test
        @Grade(value = 20)
        public void testMultipleSources() {
            String message = "Test [0-1, 1-2, 2-3, 3-4, 4-5] with [1, 5] as sources";
            BreadthFirstShortestPaths.Graph graph = new BreadthFirstShortestPaths.Graph(6);

            graph.addEdge(0, 1);
            graph.addEdge(1, 2);
            graph.addEdge(2, 3);
            graph.addEdge(3, 4);
            graph.addEdge(4, 5);

            BreadthFirstShortestPaths bfs = new BreadthFirstShortestPaths(graph, Arrays.asList(1, 5));
            assertEquals(message, 1, bfs.distTo(0));
            assertEquals(message, 0, bfs.distTo(1));
            assertEquals(message, 1, bfs.distTo(2));
            assertEquals(message, 2, bfs.distTo(3));
            assertEquals(message, 1, bfs.distTo(4));
            assertEquals(message, 0, bfs.distTo(5));
        }

        @Test
        @Grade(value = 20)
        public void testMultipleSourcesDisconnected() {
            String message = "Test [0-1, 1-2, 3-4, 4-5] with [0, 2] as sources";
            BreadthFirstShortestPaths.Graph graph = new BreadthFirstShortestPaths.Graph(6);

            graph.addEdge(0, 1);
            graph.addEdge(1, 2);
            graph.addEdge(3, 4);
            graph.addEdge(4, 5);

            BreadthFirstShortestPaths bfs = new BreadthFirstShortestPaths(graph, Arrays.asList(0, 2));
            assertEquals(message, 0, bfs.distTo(0));
            assertEquals(message, 1, bfs.distTo(1));
            assertEquals(message, 0, bfs.distTo(2));
            assertEquals(message, Integer.MAX_VALUE, bfs.distTo(3));
            assertEquals(message, Integer.MAX_VALUE, bfs.distTo(4));
            assertEquals(message, Integer.MAX_VALUE, bfs.distTo(5));

            message = "Test [0-1, 1-2, 3-4, 4-5] with [0, 3] as sources";
            graph = new BreadthFirstShortestPaths.Graph(6);

            graph.addEdge(0, 1);
            graph.addEdge(1, 2);
            graph.addEdge(3, 4);
            graph.addEdge(4, 5);

            bfs = new BreadthFirstShortestPaths(graph, Arrays.asList(0, 3));
            assertEquals(message, 0, bfs.distTo(0));
            assertEquals(message, 1, bfs.distTo(1));
            assertEquals(message, 2, bfs.distTo(2));
            assertEquals(message, 0, bfs.distTo(3));
            assertEquals(message, 1, bfs.distTo(4));
            assertEquals(message, 2, bfs.distTo(5));
        }

    }


    @RunWith(Parameterized.class)
    public static class TestParameterizedSimple {
        @Parameterized.Parameters(name = "{0}")
        public static Collection data() {
            LinkedList<Object[]> coll = new LinkedList<>();
            for (int i = 0; i < 15; i++) {
                String name = "data/graphs.BreadthFirstShortestPaths/in_1_" + i;
                coll.add(new Object[]{name, new Instance(name)});
            }
            return coll;
        }

        public TestParameterizedSimple(String name, Instance instance) {
            this.instance = instance;
        }


        @Test
        @Grade(value = 1, cpuTimeout = 1000)
        public void test() throws Exception {
            BreadthFirstShortestPaths bfs = new BreadthFirstShortestPaths(instance.graph, instance.sources);
            for (int i = 0; i < instance.destinations.size(); i++) {
                int dest = instance.destinations.get(i);
                int sol = instance.solutions.get(i);
                assertEquals(bfs.distTo(dest), sol);
            }

        }

        final Instance instance;


        static class Instance {

            BreadthFirstShortestPaths.Graph graph;
            int from;
            List<Integer> sources;
            List<Integer> destinations;
            List<Integer> solutions;

            public Instance(String file) {

                try {
                    Scanner dis = new Scanner(new FileInputStream(file));
                    String line = dis.nextLine();
                    String[] base_info = line.split(" ");
                    graph = new BreadthFirstShortestPaths.Graph(Integer.parseInt(base_info[0]));
                    int E = Integer.parseInt(base_info[1]);
                    for (int i = 0; i < 2 * E; i++) {
                        line = dis.nextLine();
                        String[] edge = line.split(" ");
                        graph.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
                    }
                    int nSources = dis.nextInt();
                    sources = new ArrayList<>();
                    for (int i = 0; i < nSources; i++) {
                        sources.add(dis.nextInt());
                    }
                    int nDest = dis.nextInt();
                    destinations = new ArrayList<>();
                    for (int i = 0; i < nDest; i++) {
                        destinations.add(dis.nextInt());
                    }
                    solutions = new ArrayList<>();
                    int nSolutions = dis.nextInt();
                    for (int i = 0; i < nSolutions; i++) {
                        solutions.add(dis.nextInt());
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @RunWith(Parameterized.class)
    public static class TestParameterizedLarge {
        @Parameterized.Parameters(name = "{0}")
        public static Collection data() {
            LinkedList<Object[]> coll = new LinkedList<>();
            for (int i = 0; i < 4; i++) {
                String name = "data/graphs.BreadthFirstShortestPaths/in_2_" + i;
                coll.add(new Object[]{name, new Instance(name)});
            }
            return coll;
        }

        public TestParameterizedLarge(String name, Instance instance) {
            this.instance = instance;
        }


        @Test
        @Grade(value = 1, cpuTimeout = 1000)
        public void test() throws Exception {
            BreadthFirstShortestPaths bfs = new BreadthFirstShortestPaths(instance.graph, instance.sources);
            for (int i = 0; i < instance.destinations.size(); i++) {
                int dest = instance.destinations.get(i);
                int sol = instance.solutions.get(i);
                assertEquals(bfs.distTo(dest), sol);
            }

        }

        final Instance instance;


        static class Instance {

            BreadthFirstShortestPaths.Graph graph;
            List<Integer> sources;
            List<Integer> destinations;
            List<Integer> solutions;

            public Instance(String file) {

                try {
                    Scanner dis = new Scanner(new FileInputStream(file));
                    String line = dis.nextLine();
                    String[] base_info = line.split(" ");
                    graph = new BreadthFirstShortestPaths.Graph(Integer.parseInt(base_info[0]));
                    int E = Integer.parseInt(base_info[1]);
                    for (int i = 0; i < 2 * E; i++) {
                        line = dis.nextLine();
                        String[] edge = line.split(" ");
                        graph.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
                    }
                    int nSources = dis.nextInt();
                    sources = new ArrayList<>();
                    for (int i = 0; i < nSources; i++) {
                        sources.add(dis.nextInt());
                    }
                    int nDest = dis.nextInt();
                    destinations = new ArrayList<>();
                    for (int i = 0; i < nDest; i++) {
                        destinations.add(dis.nextInt());
                    }
                    solutions = new ArrayList<>();
                    int nSolutions = dis.nextInt();
                    for (int i = 0; i < nSolutions; i++) {
                        solutions.add(dis.nextInt());
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}


