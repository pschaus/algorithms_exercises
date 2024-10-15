package sorting;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.*;

@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class MedianHeapTest {

    @Test
    @Grade(value=1, cpuTimeout=1000)
    @GradeFeedback(message="The median element is not always at the root of the tree !")
    @Order(1)
    public void testExample() {
        MedianHeap heap = new MedianHeap(100);
        heap.insertion(5);
        assertEquals(5, heap.findMedian());
        heap.insertion(1);
        // assertEquals(1, heap.findMedian());
        heap.insertion(2);
        assertEquals(2, heap.findMedian());
        heap.insertion(3);
        // assertEquals(2, heap.findMedian());
        heap.insertion(-2);
        assertEquals(2, heap.deleteMedian());
        assertEquals(3, heap.deleteMedian());
        assertEquals(1, heap.deleteMedian());
        assertEquals(5, heap.deleteMedian());
        assertEquals(-2, heap.deleteMedian());
    }


    // BEGIN STRIP
    @Test
    @Grade(value=1, cpuTimeout=1)
    @GradeFeedback(message="Your structure is to slow, check your complexity")
    @Order(2)
    public void testComplexityInsertionAndFind() {
        int seed = 658846465;
        Random random = new Random(seed);
        int testNbr = 5;
        for (int test = 0; test < testNbr; test++) {
            MedianHeap heap = new MedianHeap(10);
            int strongerValuePushed = -30;
            int numberValue = 10000; // number of values that will be inserted in the Heap


            List<Integer> pushedValues = new LinkedList<>();
            List<Integer> indices = new ArrayList<>(numberValue);
            for (int i = 0; i < numberValue; i++) {
                strongerValuePushed = strongerValuePushed + random.nextInt(15);
                pushedValues.add(strongerValuePushed);
                indices.add(i);
            }

            Collections.shuffle(indices);
            for (Integer index : indices) {
                heap.insertion(pushedValues.get(index));
            }
            if (pushedValues.size() % 2 == 1) {
                assertEquals(pushedValues.get((pushedValues.size() - 1) / 2), heap.findMedian());
                pushedValues.remove((pushedValues.size() - 1) / 2);
            } else {
                assertEquals(pushedValues.get(pushedValues.size() / 2), heap.findMedian());
                pushedValues.remove(pushedValues.size() / 2);
            }
        }
    }

    @Test
    @Grade(value=1, cpuTimeout=5)
    @GradeFeedback(message="Your structure is to slow, check your complexity")
    @Order(2)
    public void testComplexityInsertionAndDelete() {
        int seed = 658846465;
        Random random = new Random(seed);
        int testNbr = 5;
        for (int test = 0; test < testNbr; test++) {
            MedianHeap heap = new MedianHeap(10);
            int strongerValuePushed = -30;
            int numberValue = 10000; // number of values that will be inserted in the Heap


            List<Integer> pushedValues = new LinkedList<>();
            List<Integer> indices = new ArrayList<>(numberValue);
            for (int i = 0; i < numberValue; i++) {
                strongerValuePushed = strongerValuePushed + random.nextInt(15);
                pushedValues.add(strongerValuePushed);
                indices.add(i);
            }

            Collections.shuffle(indices);
            for (Integer index : indices) {
                heap.insertion(pushedValues.get(index));
            }
            while (!pushedValues.isEmpty()) {
                if (pushedValues.size() % 2 == 1) {
                    assertEquals(pushedValues.get((pushedValues.size() - 1) / 2), heap.deleteMedian());
                    pushedValues.remove((pushedValues.size() - 1) / 2);
                } else {
                    assertEquals(pushedValues.get(pushedValues.size() / 2), heap.deleteMedian());
                    pushedValues.remove(pushedValues.size() / 2);
                }
            }
        }
    }
    // END STRIP
}
