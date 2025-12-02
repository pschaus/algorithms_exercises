package fundamentals;

import org.javagrader.Grade;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@Grade
public class ResizingArrayQueueTest {

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    public void testBasicEnqueueDequeue() {
        ResizingArrayQueue<Integer> q = new ResizingArrayQueue<>(2);

        assertTrue(q.isEmpty());
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);

        assertEquals(3, q.size());
        assertEquals(Integer.valueOf(1), q.dequeue());
        assertEquals(Integer.valueOf(2), q.dequeue());
        assertEquals(Integer.valueOf(3), q.dequeue());
        assertTrue(q.isEmpty());
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    public void testWrapAround() {
        ResizingArrayQueue<Integer> q = new ResizingArrayQueue<>(2);

        for (int i = 0; i < 10; i++) q.enqueue(i);
        for (int i = 0; i < 5; i++) assertEquals(Integer.valueOf(i), q.dequeue());
        for (int i = 10; i < 15; i++) q.enqueue(i);

        for (int i = 5; i < 15; i++) assertEquals(Integer.valueOf(i), q.dequeue());
        assertTrue(q.isEmpty());
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    public void testResizeDown() {
        ResizingArrayQueue<Integer> q = new ResizingArrayQueue<>(2);

        for (int i = 0; i < 32; i++) q.enqueue(i);
        Object[] arr = q.q;
        int oldCap = arr.length;
        assertTrue(oldCap >= 32);

        while (q.size() > oldCap / 4 + 1) q.dequeue();
        q.dequeue(); // should shrink here

        arr = q.q;
        int len =  arr.length;
        assertEquals(oldCap / 2, len);
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    public void testResizeUp() {
        ResizingArrayQueue<Integer> q = new ResizingArrayQueue<>(2);

        q.enqueue(1);
        q.enqueue(2);
        Object[] arr = q.q;
        int len =  arr.length;
        assertEquals(2, len);

        q.enqueue(3);  // should trigger resize
        arr = q.q;
        len =  arr.length;
        assertEquals(4, len);

        q.enqueue(4);
        q.enqueue(5);  // trigger resize again
        arr = q.q;
        len =  arr.length;
        assertEquals(8, len);
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    public void testIteratorOrderAndWrap() {
        ResizingArrayQueue<Integer> q = new ResizingArrayQueue<>(4);

        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.dequeue(); // head moves
        q.dequeue(); // head moves
        q.enqueue(5);
        q.enqueue(6);

        int[] expected = {3, 4, 5, 6};
        int idx = 0;

        for (int x : q) {
            assertEquals(expected[idx++], x);
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    public void testIteratorFailFast() {
        ResizingArrayQueue<Integer> q = new ResizingArrayQueue<>(4);
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);

        Iterator<Integer> it = q.iterator();
        assertTrue(it.hasNext());
        assertEquals(1, it.next());

        q.enqueue(4); // structural modification => should break iterator
        assertThrows(ConcurrentModificationException.class, it::next);
    }
}
