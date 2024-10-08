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

    @Test
    @Grade(value=1, cpuTimeout=1000)
    @GradeFeedback(message="Do not forget to resize if needed")
    @Order(1)
    public void testExampleResize() {
        MedianHeap heap = new MedianHeap(10);
        for (int i = 0; i < 20; i++) {
            heap.insertion(i);
        }
    }

    // BEGIN STRIP
    @Test
    @Grade(value=1, cpuTimeout=700)
    @GradeFeedback(message="Your structure is to slow, check your complexity")
    @Order(2)
    public void testComplexity() {
        int seed = 658846465;
        Random random = new Random(seed);
        MedianHeap heap = new MedianHeap(10);
        int strongerValuePushed = -30;
        int numberValue = 10000;
        List<Integer> pushedValues = new LinkedList<>();
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < numberValue; i++) {
            strongerValuePushed = strongerValuePushed + random.nextInt(15);
            pushedValues.add(strongerValuePushed);
            indices.add(i);
        }

        Collections.shuffle(indices);
        for (Integer index: indices) {
            heap.insertion(pushedValues.get(index));
        }
        while (pushedValues.size() > 0) {
            if (pushedValues.size() % 2 == 1) {
                assertEquals(pushedValues.get((pushedValues.size() - 1) / 2), heap.deleteMedian());
                pushedValues.remove((pushedValues.size() - 1)/2);
            } else {
                assertEquals(pushedValues.get(pushedValues.size()/2), heap.deleteMedian());
                pushedValues.remove(pushedValues.size()/2);
            }
        }
    }
    // END STRIP
}
