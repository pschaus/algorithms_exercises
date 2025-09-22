package fundamentals;

import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.javagrader.TestResultStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Grade

public class LinkedChainTest {

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    void testInitialization() {
        LinkedChain chain = new LinkedChain(5);  // 0 <-> 4

        assertEquals(4, chain.successor[0]);
        assertEquals(0, chain.predecessor[4]);

        for (int i = 1; i < 4; i++) {
            assertEquals(i, chain.successor[i]); // Not in the chain
            assertEquals(i, chain.predecessor[i]); // Not in the chain
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    void testInsertAfter() {
        LinkedChain chain = new LinkedChain(5);  // 0 <-> 4

        // Insert 2 after 0
        chain.insertAfter(0, 2); // 0 <-> 2 <-> 4

        assertEquals(2, chain.successor[0]);
        assertEquals(4, chain.successor[2]);
        assertEquals(0, chain.predecessor[2]);
        assertEquals(2, chain.predecessor[4]);

        // Insert 1 after 0
        chain.insertAfter(0, 1); // 0 <-> 1 <-> 2 <-> 4

        assertEquals(1, chain.successor[0]);
        assertEquals(2, chain.successor[1]);
        assertEquals(0, chain.predecessor[1]);
        assertEquals(1, chain.predecessor[2]);
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    void testInsertBefore() {
        LinkedChain chain = new LinkedChain(5);  // 0 <-> 4

        // Insert 2 between 0 and 4
        chain.insertBefore(4, 2); // 0 <-> 2 <-> 4

        assertEquals(2, chain.successor[0]);
        assertEquals(4, chain.successor[2]);
        assertEquals(0, chain.predecessor[2]);
        assertEquals(2, chain.predecessor[4]);

        // Insert 1 between 0 and 2
        chain.insertBefore(2, 1); // 0 <-> 1 <-> 2 <-> 4

        assertEquals(1, chain.successor[0]);
        assertEquals(2, chain.successor[1]);
        assertEquals(0, chain.predecessor[1]);
        assertEquals(1, chain.predecessor[2]);
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    void testParameterChecksAndExceptions() {
        LinkedChain chain = new LinkedChain(5); // 0 <-> 4

        // Insert 2 before 4
        chain.insertBefore(4, 2); // 0 <-> 2 <-> 4

        // 0 is already in the chain so it cannot be inserted again
        assertThrows(IllegalArgumentException.class, () -> chain.insertBefore(4,0));

        // 5 cannot be inserted
        assertThrows(IllegalArgumentException.class, () -> chain.insertBefore(2,5));

        // 3 cannot be inserted before 1 as 1 is not in the chain
        assertThrows(IllegalArgumentException.class, () -> chain.insertBefore(3,1));

    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    void testIterator() {
        LinkedChain chain = new LinkedChain(5);


        chain.insertAfter(0, 1); // 0 <-> 1 <-> 4
        chain.insertAfter(1, 2); // 0 <-> 1 <-> 2 <-> 4

        Iterator<Integer> iterator = chain.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(0, iterator.next());
        assertEquals(1, iterator.next());
        assertEquals(2, iterator.next());
        assertEquals(4, iterator.next());
        assertFalse(iterator.hasNext());
    }



    @Test
    @Grade(value = 1, cpuTimeout = 1)
    void testIteratorNoSuchElementException() {
        LinkedChain chain = new LinkedChain(5);
        Iterator<Integer> iterator = chain.iterator();

        iterator.next(); // 0
        iterator.next(); // 4
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    @Grade(value = 4,cpuTimeout = 1,threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @GradeFeedback(message="Your algorithm does not return the correct result or does not satisfy the time complexity of O(1) insertions", on= TestResultStatus.FAIL)
    public void testTimeComplexityInsert() {
        // test a very large chain
        int n = 1000; // n x n square
        LinkedChain chain = new LinkedChain(n * n);
        // add the diagonal
        for (int i = 0; i < n - 2; i++) {
            chain.insertAfter(i * n + i, (i + 1) * n + (i + 1));
        }
        for (int i = 0; i < n - 1; i++) {
            assertTrue(chain.successor[i * n + i] == (i + 1) * n + (i + 1));
        }
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                if (i != j) {
                    assertTrue(chain.successor[i * n + j] == i * n + j);
                    assertTrue(chain.predecessor[i * n + j] == i * n + j);
                }
            }
            assertTrue(chain.predecessor[(i + 1) * n + (i + 1)] == i * n + i);
        }
    }


    @Test
    @Grade(value = 4,cpuTimeout = 1,threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @GradeFeedback(message="Your algorithm does not return the correct result or does not satisfy the time complexity of O(1) insertions", on= TestResultStatus.FAIL)
    public void testTimeComplexityIterator() {
        // test a very large chain
        int n = 1000; // n x n square
        LinkedChain chain = new LinkedChain(n * n);
        // add the diagonal
        for (int i = n - 1; i > 1; i--) {
            chain.insertBefore(i * n + i, (i - 1) * n + (i - 1));
        }
        Iterator<Integer> iterator = chain.iterator();
        for (int i = 0; i < n - 1; i++) {
            assertTrue(iterator.hasNext());
            assertEquals(i * n + i, iterator.next());
        }
    }

}