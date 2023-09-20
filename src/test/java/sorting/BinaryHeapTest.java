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

@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class BinaryHeapTest {


    @Test
    @Grade(value=1, cpuTimeout=1000)
    @GradeFeedback(message="The minimum element is not always at the root of the tree !")
    @Order(1)
    public void testExample() {
        BinaryHeap heap = new BinaryHeap(100);
        heap.push(5);
        assertEquals(5, heap.getRoot());
        heap.push(1);
        assertEquals(1, heap.getRoot());
        heap.push(2);
        assertEquals(1, heap.getRoot());
        heap.push(3);
        assertEquals(1, heap.getRoot());
        heap.push(0);
        assertEquals(0, heap.getRoot());
    }

    @Test
    @Grade(value=1, cpuTimeout=1000)
    @GradeFeedback(message="Do not forget to resize if needed")
    @Order(1)
    public void testExampleResize() {
        BinaryHeap heap = new BinaryHeap(10);
        for (int i = 0; i < 20; i++) {
            heap.push(i);
        }
    }
    
    // BEGIN STRIP
    
    static Stream<Instance> dataProvider() {
        return Stream.of(new File("data/sorting.BinaryHeap").listFiles())
            .filter(file -> !file.isDirectory())
            .map(file -> new Instance(file.getPath()));
    }

    @ParameterizedTest
    @Grade(value = 1)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm. Hint: debug on the small example")
    @MethodSource("dataProvider")
    @Order(1)
    public void testMin(Instance instance)  {
        BinaryHeap heap = new BinaryHeap(instance.size*2);
        int min = instance.input[0];
        for (int value : instance.input) {
            heap.push(value);
            if (value < min) {
                min = value;
            }
            assertEquals(min, heap.getRoot());
        }
    }

    @ParameterizedTest
    @Grade(value = 1)
    @GradeFeedback(message = "Do not forget to resize !")
    @MethodSource("dataProvider")
    @Order(1)
    public void testResize(Instance instance) {
        BinaryHeap heap = new BinaryHeap(instance.size / 2);
        for (int value : instance.input) {
            heap.push(value);
        }
    }

    @ParameterizedTest
    @Grade(value = 1, cpuTimeout = 700)
    @GradeFeedback(message = "Check the complexity of your algorithm")
    @MethodSource("dataProvider")
    @Order(2)
    public void testComplexity(Instance instance) {
        BinaryHeap heap = new BinaryHeap(instance.size*2);
        for (int value : instance.input) {
            heap.push(value);
        }
    }

    static class Instance {
        int [] input;
        int size;

        public Instance(String file) {
            try {
                Scanner scan = new Scanner(new FileInputStream(file));
                int n = scan.nextInt();
                this.size = n;
                this.input = new int[n];
                for (int i = 0; i < n; i++) {
                    this.input[i] = scan.nextInt();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    // END STRIP
}
