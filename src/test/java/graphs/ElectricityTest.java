package graphs;

import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.FileInputStream;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import graphs.Electricity.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ElectricityTest {
    @Test
    @Grade(value = 1)
    public void simpleTest(){
        List<Edge> graph = new ArrayList<>();
        graph.add(new Edge(1, 2, 5));
        graph.add(new Edge(2, 3, 10));
        graph.add(new Edge(1, 3, 20));

        Electricity student = new Electricity(graph);

        assertEquals(student.getMinCost(), 15);
    }


    @Test
    @Grade(value = 1)
    public void complexGraphTest(){
        List<Edge> graph = new ArrayList<>();
        graph.add(new Edge(1, 2, 10));
        graph.add(new Edge(2, 5, 8));
        graph.add(new Edge(5, 3, 1));
        graph.add(new Edge(3, 1, 7));
        graph.add(new Edge(3, 6, 4));
        graph.add(new Edge(3, 4, 8));
        graph.add(new Edge(4, 6, 9));
        graph.add(new Edge(6, 1, 1));

        Electricity student = new Electricity(graph);

        assertEquals(student.getMinCost(), 22);
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    public void ComplexityTest(){
        List<Edge> graph = new ArrayList<>();

        for(int i = 0; i < 10000000; i++) {
            graph.add(new Edge(i, i+1, 1));
        }

        Electricity student = new Electricity(graph);

        assertEquals(student.getMinCost(), 10000000);
    }

    @ParameterizedTest
    @Grade(value = 1, cpuTimeout = 1000)
    @Order(1)
    @MethodSource("dataProvider")
    @GradeFeedback(message = "Your method does not return the good value")
    public void randomGraphTest(ElectricityTest.Instance instance) {
        Electricity student = new Electricity(instance.graph);
        assertEquals(student.getMinCost(), instance.answer);
    }


    // Helpers for parameterized and complexity tests
    static class Instance {

        List<Edge> graph;
        int answer;

        public Instance(String file) {
            try {

                Scanner dis = new Scanner(new FileInputStream(file));
                int nEdges = dis.nextInt();

                graph = new ArrayList<>();

                for (int i = 0; i < nEdges; i++) {
                    int source = dis.nextInt();
                    int dest = dis.nextInt();
                    int weight = dis.nextInt();

                    graph.add(new Edge(source, dest, weight));
                }

                answer = dis.nextInt();


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public Instance(List<Edge> g, int a) {
            this.graph = g;
            this.answer = a;

        }

    }

    static Stream<ElectricityTest.Instance> dataProvider() {
        return IntStream.range(0, 20).mapToObj(i -> new ElectricityTest.Instance("data/graphs.Electricity/in_" + i));
    }
}
