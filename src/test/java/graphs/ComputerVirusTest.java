package graphs;

import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.javagrader.TestResultStatus;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Grade
public class ComputerVirusTest {


    @Test
    @Grade(value = 1)
    @Order(1)
    @GradeFeedback(message="Your algorithm does not return the correct result on the given example. Debug it locally", on=TestResultStatus.FAIL)
    void testSimpleNetwork() {
        Map<String, List<String>> network = new HashMap<>();

        network.put("A", Arrays.asList("B", "C"));
        network.put("B", Arrays.asList("D"));
        network.put("C", Arrays.asList("D"));
        network.put("D", Collections.emptyList());

        Map<String, Integer> incubation = new HashMap<>();
        incubation.put("A", 2);
        incubation.put("B", 3);
        incubation.put("C", 1);
        incubation.put("D", 4);

        Map<String, Integer> result = ComputerVirus.computeInfectionTimes(network, incubation, "A");

        assertEquals(2, result.get("A"));             // Start, incubation time of A is 2
        assertEquals(5, result.get("B"));             // A infects B after 2, B has incubation time of 3, so B infected at 2+3=5
        assertEquals(3, result.get("C"));             // A infects C after 2, C has incubation time of 1, so C infected at 2+1=3
        assertEquals(7, result.get("D"));             // C infects D at 3, D has incubation time of 4, so D infected at 3+4=7

    }

    @Test
    @Grade(value = 1)
    @Order(1)
    @GradeFeedback(message="Your algorithm does not return the correct result on the given example with a cycle. Debug it locally", on=TestResultStatus.FAIL)
    void testCycle() {
        Map<String, List<String>> network = new HashMap<>();

        network.put("A", Arrays.asList("B"));
        network.put("B", Arrays.asList("C"));
        network.put("C", Arrays.asList("A")); // cycle

        Map<String, Integer> incubation = new HashMap<>();
        incubation.put("A", 1);
        incubation.put("B", 1);
        incubation.put("C", 1);

        Map<String, Integer> result = ComputerVirus.computeInfectionTimes(network, incubation, "A");

        assertEquals(1, result.get("A"));
        assertEquals(2, result.get("B"));
        assertEquals(3, result.get("C"));
    }

    @Test
    @Grade(value = 1)
    @Order(1)
    @GradeFeedback(message="Your algorithm does not return the correct result with disconnected graph. Debug it locally", on=TestResultStatus.FAIL)
    void testDisconnectedNode() {
        Map<String, List<String>> network = new HashMap<>();
        network.put("A", Arrays.asList("B"));
        network.put("B", Collections.emptyList());
        network.put("C", Collections.emptyList()); // disconnected node

        Map<String, Integer> incubation = new HashMap<>();
        incubation.put("A", 1);
        incubation.put("B", 1);
        incubation.put("C", 1); // C is never infected

        Map<String, Integer> result = ComputerVirus.computeInfectionTimes(network, incubation, "A");

        assertTrue(result.containsKey("A"));
        assertTrue(result.containsKey("B"));
        assertFalse(result.containsKey("C")); // never infected
    }

    @Test
    @Grade(value = 1)
    @Order(1)
    @GradeFeedback(message="Your algorithm does not return the correct result with disconnected graph. Debug it locally", on=TestResultStatus.FAIL)
    void testMoreComplexGraph() {
        // A -> B -> C
        //           ^
        //           |
        //           D

        Map<String, List<String>> network = new HashMap<>();
        network.put("A", Arrays.asList("B"));
        network.put("B", Arrays.asList("C"));
        network.put("C", Collections.emptyList()); // disconnected node
        network.put("D", Arrays.asList("C"));

        Map<String, Integer> incubation = new HashMap<>();
        incubation.put("A", 1);
        incubation.put("B", 2);
        incubation.put("C", 3);
        incubation.put("D", 4); // D is never infected

        Map<String, Integer> result1 = ComputerVirus.computeInfectionTimes(network, incubation, "A");

        assertTrue(result1.containsKey("A"));
        assertTrue(result1.containsKey("B"));
        assertTrue(result1.containsKey("C"));
        assertFalse(result1.containsKey("D"));
        assertEquals(1,result1.get("A"));
        assertEquals(3,result1.get("B"));
        assertEquals(6,result1.get("C"));

        Map<String, Integer> result2 = ComputerVirus.computeInfectionTimes(network, incubation, "B");

        assertFalse(result2.containsKey("A"));
        assertTrue(result2.containsKey("B"));
        assertTrue(result2.containsKey("C"));
        assertFalse(result2.containsKey("D"));
        assertEquals(2,result2.get("B"));
        assertEquals(5,result2.get("C"));

    }


    @Test
    @Grade(value = 1)
    @Order(1)
    @GradeFeedback(message="Your algorithm does not return the correct result on the given example with a cycle and a path. Debug it locally", on=TestResultStatus.FAIL)
    void testCycleWithPath() {
        Map<String, List<String>> network = new HashMap<>();

        network.put("A", Arrays.asList("B","D"));
        network.put("B", Arrays.asList("C"));
        network.put("C", Arrays.asList("A","D")); // cycle
        network.put("D", Arrays.asList("A"));// cycle

        Map<String, Integer> incubation = new HashMap<>();
        incubation.put("A", 1);
        incubation.put("B", 2);
        incubation.put("C", 3);
        incubation.put("D", 4);

        Map<String, Integer> result1 = ComputerVirus.computeInfectionTimes(network, incubation, "A");

        assertEquals(1, result1.get("A"));
        assertEquals(3, result1.get("B"));
        assertEquals(6, result1.get("C"));
        assertEquals(5, result1.get("D"));

        Map<String, Integer> result2 = ComputerVirus.computeInfectionTimes(network, incubation, "D");

        assertEquals(5, result2.get("A"));
        assertEquals(7, result2.get("B"));
        assertEquals(10, result2.get("C"));
        assertEquals(4, result2.get("D"));
    }

    @Test
    @Grade(value = 4,cpuTimeout = 1,threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @Order(2)
    @GradeFeedback(message="Your algorithm does not return the correct result or does not satisfy the time complexity on a large graph", on=TestResultStatus.FAIL)
    public void testTimeComplexity() {
        int n = 500;
        // create a grid of n x n nodes, each with an incubation time of 1
        Map<String, List<String>> network = new HashMap<>();
        Map<String, Integer> incubation = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String node = "Node_" + i + "_" + j;
                incubation.put(node, 1);
                List<String> neighbors = new ArrayList<>();
                if (i > 0) neighbors.add("Node_" + (i - 1) + "_" + j); // left neighbor
                if (i < n-1) neighbors.add("Node_" + (i + 1) + "_" + j); // right neighbor
                if (j > 0) neighbors.add("Node_" + i + "_" + (j - 1)); // top neighbor
                if (j < n-1) neighbors.add("Node_" + i + "_" + (j + 1)); // bottom neighbor
                network.put(node, neighbors);
            }
        }
        // start the virus at the top-left corner
        Map<String, Integer> result = ComputerVirus.computeInfectionTimes(network, incubation, "Node_0_0");
        // check that all nodes are infected
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String node = "Node_" + i + "_" + j;
                assertTrue(result.containsKey(node), "Node " + node + " should be infected");
                assertEquals(i + j + 1, result.get(node), "Node " + node + " should be infected at time " + (i + j + 1));
            }
        }
    }


    @Test
    @Grade(value = 4,cpuTimeout = 1,threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @Order(2)
    @GradeFeedback(message="Your algorithm does not return the correct result or does not satisfy the time complexity on a large graph", on=TestResultStatus.FAIL)
    public void testArithmetic() {
        // create a list of n nodes, each with an incubation time of n
        int n = 200;
        Map<String, List<String>> network = new HashMap<>();
        Map<String, Integer> incubation = new HashMap<>();


        for (int i = 0; i < n-1; i++) {
            String node = "Node_" + i;
            incubation.put(node, i);
            List<String>   neighbors = new ArrayList<>();
            neighbors.add("Node_" + (i+1));
            network.put(node, neighbors);
        }
        network.put("Node_" + (n-1), new ArrayList<>());
        incubation.put("Node_" + (n-1), n-1);

        Map<String, Integer> result = ComputerVirus.computeInfectionTimes(network, incubation, "Node_0");
        for (int i = 0; i < n; i++) {
            assertEquals(i * (i+1)/2, result.get("Node_"+i));
        }


    }

}
