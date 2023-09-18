package graphs;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

// BEGIN STRIP
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
// END STRIP

@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class ConnectedComponentsTest {

    @Test
    @Order(0)
    public void simpleTest(){
        ConnectedComponents.Graph graph = new ConnectedComponents.Graph(6);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);

        int cc = ConnectedComponents.numberOfConnectedComponents(graph);
        assertEquals(2, cc);
    }

    // BEGIN STRIP
    static Stream<Instance> providerCorrectness() {
        return IntStream.range(0, 7).mapToObj(i -> {
            return new Instance("data/graphs.ConnectedComponents/in_corr_" + i);
        });
    }
    
    @ParameterizedTest
    @MethodSource("providerCorrectness")
    @Order(1)
    public void TestCorrectness(Instance instance){
        int student_solution = ConnectedComponents.numberOfConnectedComponents(instance.graph);
        assertEquals(student_solution, instance.solutions);
    }
    
    static Stream<Instance> providerCycle() {
        return IntStream.range(0, 2).mapToObj(i -> {
            return new Instance("data/graphs.ConnectedComponents/in_cycl_" + i);
        });
    }
    
    @ParameterizedTest
    @MethodSource("providerCycle")
    @Order(1)
    public void testCycles(Instance instance) {
        int student_solution = ConnectedComponents.numberOfConnectedComponents(instance.graph);
        assertEquals(student_solution, instance.solutions);
    }
    
    static Stream<Instance> providerComplexity() {
        return IntStream.range(0, 2).mapToObj(i -> {
            return new Instance("data/graphs.ConnectedComponents/in_compl_" + i);
        });
    }
    
    @ParameterizedTest
    @MethodSource("providerComplexity")
    @Order(3)
    public void testComplexity(Instance instance) {
        ConnectedComponents.numberOfConnectedComponents(instance.graph);
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
            }
        }
    }
    // END STRIP
}
