package sorting;

import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Grade
public class TernaryHeapTest {

    @Test
    @Grade(value = 7, cpuTimeout = 1)
    @GradeFeedback(message = "Use your debugger or print to debug your code")
    public void testDebugBase() {
        TernaryHeap heap = new TernaryHeap(10);
        assertEquals(0, heap.size());

        for (int i = 0; i < 10; i++) {
            heap.insert(i);
        }

        // Test if the correct size is given
        assertEquals(10, heap.size());


        // Test if the content is the same
        testTernaryHeapProperty(heap.content, heap.size());

        // Test if the max is returned
        assertEquals(9, heap.getMax());

        // Test remove the maximum
        assertEquals(9, heap.delMax());


        testTernaryHeapProperty(heap.content, heap.size());
    }

    @Test
    @Grade(value = 7, cpuTimeout = 1)
    @GradeFeedback(message = "Your heap should be able to handle duplicates")
    public void testDuplicate() {
        TernaryHeap heap = new TernaryHeap(10);


        assertEquals(0, heap.size());

        heap.insert(1);   // <1 >
        heap.insert(-1);  // <1, -1 >
        heap.insert(1);   // <1, -1, 1 >

        assertEquals(3, heap.size());
        assertEquals(1, heap.getMax());

        heap.insert(21);  // <21, 1, 1, -1 >
        heap.insert(22);  // <22, 21, 1, 1, -1 >

        assertEquals(5, heap.size());
        assertEquals(22, heap.getMax());

        // Ensure that the content is the same
        assertCheckSum(44, heap.content, heap.size());
        testTernaryHeapProperty(heap.content, heap.size());

        assertEquals(22, heap.delMax());    // <21, 1, 1, -1 >

        assertEquals(4, heap.size());
        assertEquals(21, heap.getMax());

        assertEquals(21, heap.delMax());    // <1, 1, -1 >

        // Ensure that the content is the same
        assertCheckSum(1, heap.content, heap.size());
        testTernaryHeapProperty(heap.content, heap.size());

        assertEquals(3, heap.size());
        assertEquals(1, heap.getMax());

        assertEquals(1, heap.delMax()); // <1, -1 >

        assertEquals(2, heap.size());
        assertEquals(1, heap.getMax());

        assertEquals(1, heap.delMax()); // <-1 >

        assertEquals(1, heap.size());
        assertEquals(-1, heap.getMax());

        heap.insert(3);

        assertEquals(2, heap.size());
        assertEquals(3, heap.getMax());
    }


    public static void testTernaryHeapProperty(int[] content, int size) {
        for (int i = 0; i < size / 3; i++) {
            if (3 * i + 1 < size)
                assertTrue(content[i] >= content[3 * i + 1]);
            if (3 * i + 2 < size)
                assertTrue(content[i] >= content[3 * i + 2]);
            if (3 * i + 3 < size)
                assertTrue(content[i] >= content[3 * i + 3]);
        }
    }

    public static void assertCheckSum(int expected, int [] content, int size) {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum = sum + content[i];
        }
        assertEquals(expected, sum);
    }


    // BEGIN STRIP



    @Test
    @Grade(value = 7, cpuTimeout = 1)
    @GradeFeedback(message = "Ensure that the elements are inserted the heap.")
    public void testInsertApi() {
        TernaryHeap heap = new TernaryHeap(10);
        for (int i = 0; i < 10; i++) {
            heap.insert(i);
            assertEquals(i, heap.getMax());
            assertEquals(i + 1, heap.size());
        }
    }

    @Test
    @Grade(value = 68, cpuTimeout = 1)
    @GradeFeedback(message = "Ensure that the elements are inserted the heap and the property is correct.")
    public void testInsertHeapProperty() {
        int n = 13+(9*3);
        java.util.Random r = new java.util.Random(1);
        TernaryHeap heap = new TernaryHeap(n);
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int v = r.nextInt(n);
            max = Math.max(max, v);
            heap.insert(v);
            int res = heap.getMax();
            assertEquals(max, heap.getMax());
        }
        testTernaryHeapProperty(heap.content, heap.size());
    }

    @Test
    @Grade(value = 7, cpuTimeout = 1)
    @GradeFeedback(message = "Your delMax method in not working as expected. Ensure that the heap structure is maintained after each delete.")
    public void testDelMaxApi() {
        TernaryHeap heap = new TernaryHeap(10);

        for (int i = 0; i < 8; i++) {
            heap.insert(i % 4);
        }
        assertEquals(8, heap.size());

        // Test if removing the max gives 3 twice
        assertEquals(3, heap.delMax());
        assertEquals(3, heap.delMax());

        assertEquals(6, heap.size());

        // Add two more values
        heap.insert(8);
        heap.insert(9);


        assertEquals(9, heap.delMax());
        assertEquals(8, heap.delMax());

        assertEquals(2, heap.delMax());

        assertEquals(5, heap.size());

    }


    @Test
    @Grade(value = 7, cpuTimeout = 1)
    @GradeFeedback(message = "Try modifying the input of base test to see if your algorithm still works")
    public void testDiversifiedSmall() {
        TernaryHeap heap = new TernaryHeap(10);

        for (int i = 0; i < 7; i++) {
            heap.insert(i % 4);
        }

        // Ensure content is the same
        assertCheckSum(9, heap.content, heap.size());
        testTernaryHeapProperty(heap.content, heap.size());

        assertEquals(3, heap.getMax());
        assertEquals(7, heap.size());
        heap.insert(8);
        heap.insert(8);
        heap.insert(9);
        assertEquals(10, heap.size());

        // Ensure content is the same
        assertCheckSum(34, heap.content, heap.size());
        testTernaryHeapProperty(heap.content, heap.size());

        assertEquals(9, heap.delMax());

        assertCheckSum(25, heap.content, heap.size());
        testTernaryHeapProperty(heap.content, heap.size());

        assertEquals(8, heap.delMax());

        assertCheckSum(17, heap.content, heap.size());
        testTernaryHeapProperty(heap.content, heap.size());

        assertEquals(8, heap.delMax());
        assertEquals(3, heap.getMax());
        assertEquals(7, heap.size());

        assertCheckSum(9, heap.content, heap.size());
        testTernaryHeapProperty(heap.content, heap.size());
    }


    @Test
    @Grade(value = 7, cpuTimeout = 1)
    @GradeFeedback(message = "Try modifying the input of base test to see if your algorithm still works")
    public void testDiversified() {
        TernaryHeap heap = new TernaryHeap(100);

        for (int i = 0; i < 90; i++) {
            heap.insert(i % 21);
        }

        assertCheckSum(855, heap.content, heap.size());
        testTernaryHeapProperty(heap.content, heap.size());
        assertEquals(20, heap.getMax());
        assertEquals(90, heap.size());

        heap.insert(42);
        heap.insert(42);
        heap.insert(43);
        assertEquals(93, heap.size());

        assertCheckSum(982, heap.content, heap.size());
        testTernaryHeapProperty(heap.content, heap.size());

        assertEquals(43, heap.delMax());
        assertEquals(42, heap.delMax());
        assertEquals(42, heap.delMax());
        assertEquals(20, heap.getMax());
        assertEquals(90, heap.size());

        assertCheckSum(855, heap.content, heap.size());
        testTernaryHeapProperty(heap.content, heap.size());
    }



    @Grade(value = 9, cpuTimeout = 1)
    @GradeFeedback(message = "O(log(n)) is not the same as O(n)")
    public void testComplexityInsertAndDelete() {
        int n = 10000;
        TernaryHeap heap = new TernaryHeap(n);
        for (int i = 0; i < n; i++) {
            heap.insert(i);
        }
        for (int i = n - 1; i >= 1; i--) {
            assertEquals(i, heap.delMax());
            assertEquals(i - 1, heap.getMax());
        }
    }


    @Test
    @Grade(value = 9, cpuTimeout = 1)
    @GradeFeedback(message = "O(log(n)) is not the same as O(n)")
    public void testComplexityInsert() {
        int n = 10000;
        TernaryHeap heap = new TernaryHeap(n);
        for (int i = 43; i < n; i += 42) {
            heap.insert(i);
            assertEquals(i, heap.getMax());
            heap.insert(42);
            assertEquals(i, heap.getMax());
        }
    }

    // END STRIP


}
