package sorting;

import org.javagrader.Grade;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Grade
public class SortLinkedListTest {

    private static SortLinkedList build(int... values) {
        SortLinkedList list = new SortLinkedList();
        for (int i = values.length - 1; i >= 0; i--) list.addFirst(values[i]);
        return list;
    }

    private static int[] toArray(SortLinkedList list) {
        List<Integer> res = new ArrayList<>();
        for (int v : list) res.add(v);
        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    void testEmpty() {
        SortLinkedList list = new SortLinkedList();
        list.sort();
        assertEquals(0, toArray(list).length);
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    void testSingle() {
        SortLinkedList list = build(7);
        list.sort();
        assertArrayEquals(new int[]{7}, toArray(list));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    void testTwoElements() {
        SortLinkedList list = build(2, 1);
        list.sort();
        assertArrayEquals(new int[]{1, 2}, toArray(list));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    void testSmall() {
        SortLinkedList list = build(5, 3, 8, 1, 9, 2, 7);
        list.sort();
        assertArrayEquals(new int[]{1, 2, 3, 5, 7, 8, 9}, toArray(list));
        assertEquals(7, list.size());
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    void testAlreadySortedAndReversed() {
        SortLinkedList sorted = build(1, 2, 3, 4, 5, 6);
        sorted.sort();
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, toArray(sorted));

        SortLinkedList reversed = build(6, 5, 4, 3, 2, 1);
        reversed.sort();
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, toArray(reversed));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    void testDuplicatesAndNegatives() {
        SortLinkedList list = build(3, -1, 3, 0, -1, 2, 3, Integer.MIN_VALUE, Integer.MAX_VALUE);
        list.sort();
        assertArrayEquals(new int[]{Integer.MIN_VALUE, -1, -1, 0, 2, 3, 3, 3, Integer.MAX_VALUE}, toArray(list));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    void testMergeDirectly() {
        SortLinkedList l = new SortLinkedList();
        SortLinkedList.Node a = new SortLinkedList.Node(1, new SortLinkedList.Node(4, new SortLinkedList.Node(6, null)));
        SortLinkedList.Node b = new SortLinkedList.Node(2, new SortLinkedList.Node(3, null));
        SortLinkedList.Node m = l.merge(a, b);
        int[] expected = {1, 2, 3, 4, 6};
        for (int e : expected) {
            assertNotNull(m);
            assertEquals(e, m.value);
            m = m.next;
        }
        assertNull(m);
        assertNull(l.merge(null, null));
        assertEquals(5, l.merge(null, new SortLinkedList.Node(5, null)).value);
        assertEquals(5, l.merge(new SortLinkedList.Node(5, null), null).value);
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    void testNodesAreRelinkedNotCreated() {
        SortLinkedList list = build(3, 1, 2);
        List<SortLinkedList.Node> before = new ArrayList<>();
        for (SortLinkedList.Node n = list.head; n != null; n = n.next) before.add(n);
        list.sort();
        List<SortLinkedList.Node> after = new ArrayList<>();
        for (SortLinkedList.Node n = list.head; n != null; n = n.next) after.add(n);
        assertEquals(3, after.size());
        for (SortLinkedList.Node n : after) {
            assertTrue(before.stream().anyMatch(o -> o == n), "no new node must be created");
        }
        // no cycle and last node terminates
        assertNull(after.get(2).next);
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    void testRandom() {
        Random rand = new Random(42);
        for (int t = 0; t < 50; t++) {
            int n = rand.nextInt(200);
            int[] values = new int[n];
            for (int i = 0; i < n; i++) values[i] = rand.nextInt(50) - 25;
            SortLinkedList list = build(values);
            list.sort();
            int[] expected = values.clone();
            Arrays.sort(expected);
            assertArrayEquals(expected, toArray(list));
            assertEquals(n, list.size());
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 2)
    void testLargeNoStackOverflow() {
        int n = 200_000;
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < n; i++) values.add(i);
        Collections.shuffle(values, new Random(1));
        SortLinkedList list = new SortLinkedList();
        for (int v : values) list.addFirst(v);
        list.sort();
        int prev = -1;
        int count = 0;
        for (int v : list) {
            assertTrue(v > prev);
            prev = v;
            count++;
        }
        assertEquals(n, count);
    }
}
