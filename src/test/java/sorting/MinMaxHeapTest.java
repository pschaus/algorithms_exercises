package sorting;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Stream;

import java.lang.Math;

@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class MinMaxHeapTest {

    @Test
    @Grade(value=1, cpuTimeout=1000)
    @GradeFeedback(message="The minimum element is not always at the root of the tree !")
    @Order(1)
    public void testExample() {
        MinMaxHeap<Integer> heap = new MinMaxHeap<>(100);
        int min;
        int max;
        heap.insert(5);
        min = heap.min();
        max = heap.max();
        assertEquals(5, min);
        assertEquals(5, max);
        heap.insert(1);
        min = heap.min();
        max = heap.max();
        assertEquals(1, min);
        assertEquals(5, max);
        heap.insert(2);
        min = heap.min();
        max = heap.max();
        assertEquals(1, min);
        assertEquals(5, max);
        heap.insert(6);
        min = heap.min();
        max = heap.max();
        assertEquals(1, min);
        assertEquals(6, max);
        heap.insert(0);
        min = heap.min();
        max = heap.max();
        assertEquals(0, min);
        assertEquals(6, max);
    }
    
    static Stream<Instance> dataProvider() {
        return Stream.of(new File("data/sorting.BinaryHeap").listFiles())
            .filter(file -> !file.isDirectory())
            .map(file -> new Instance(file.getPath()));
    }

    @ParameterizedTest
    @Grade(value = 1, cpuTimeout = 1000)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm. Hint: debug on the small example")
    @MethodSource("dataProvider")
    @Order(2)
    public void testMin(Instance instance)  {
        MinMaxHeap<Integer> heap = new MinMaxHeap<>(instance.size*2);
        int min = instance.input[0];
        for (int value : instance.input) {
            heap.insert(value);
            min = Math.min(min, value);
            int hmin = heap.min();
            assertEquals(min, hmin);
        }
    }

    @ParameterizedTest
    @Grade(value = 1, cpuTimeout = 1000)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm. Hint: debug on the small example")
    @MethodSource("dataProvider")
    @Order(2)
    public void testMax(Instance instance)  {
        MinMaxHeap<Integer> heap = new MinMaxHeap<>(instance.size*2);
        int max = instance.input[0];
        for (int value : instance.input) {
            heap.insert(value);
            max = Math.max(max, value);
            int hmax = heap.max();
            assertEquals(max, hmax);
        }
    }

    static Stream<Instance> dataProviderComplexity() {
        return Stream.of(new File("data/sorting.BinaryHeap").listFiles())
            .filter(file -> !file.isDirectory() && file.getName().startsWith("in_10000"))
            .map(file -> new Instance(file.getPath()));
    }

    @ParameterizedTest
    @Grade(value = 1, cpuTimeout = 700)
    @GradeFeedback(message = "Check the complexity of your swim method")
    @MethodSource("dataProviderComplexity")
    @Order(3)
    public void testSwimComplexity(Instance instance)  {
        MinMaxHeap<Integer> heap = new MinMaxHeap<>(instance.size*2);
        for (int value : instance.input) {
            heap.insert(value);
        }
    }

    @ParameterizedTest
    @Grade(value=1, cpuTimeout=5)
    @GradeFeedback(message="Your min method is too slow")
    @MethodSource("dataProviderComplexity")
    @Order(3)
    public void testMinComplexity(Instance instance) {
        instance.heap.min();
    }

    @ParameterizedTest
    @Grade(value=1, cpuTimeout=5)
    @GradeFeedback(message="Your max method is too slow")
    @MethodSource("dataProviderComplexity")
    @Order(3)
    public void testMaxComplexity(Instance instance) {
        instance.heap.max();
    }

    static class Instance {
        int [] input;
        int size;
        MinMaxHeap<Integer> heap;

        public Instance(String file) {
            try {
                Scanner scan = new Scanner(new FileInputStream(file));
                int n = scan.nextInt();
                this.size = n;
                this.input = new int[n];
                this.heap = new MinMaxHeap<>(n);
                for (int i = 0; i < n; i++) {
                    this.input[i] = scan.nextInt();
                    heap.insert(this.input[i]);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
