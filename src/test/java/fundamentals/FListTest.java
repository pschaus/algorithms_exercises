package fundamentals;

import org.javagrader.Grade;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.*;


@Grade
public class FListTest {



    @Test
    @Grade(value = 1)
    public void testNil() {
        FList<Integer> list = FList.nil();
        assertThrows(IllegalArgumentException.class, () -> { list.head(); });
    }

    @Test
    @Grade(value = 1)
    public void testCons() {
        FList<Integer> list = FList.nil();

        for (int i = 0; i < 10; i++) {
            list = list.cons(i);
        }
        for (int i = 9; i >= 0; i--) {
            assertEquals(new Integer(i),list.head());
            list = list.tail();
        }
        assertTrue(list.isEmpty());
    }

    @Test
    @Grade(value = 1)
    public void testMap() {
        FList<Integer> list = FList.nil();

        for (int i = 0; i < 10; i++) {
            list = list.cons(i);
        }

        list = list.map(i -> i+1);
        // will print 10,9,8 .. 1
        for (int i = 10; i >= 1; i--) {
            assertEquals(new Integer(i), list.head());
            list = list.tail();
        }
        assertTrue(list.isEmpty());
    }

    @Test
    @Grade(value = 1)
    public void testFilter() {
        FList<Integer> list = FList.nil();

        for (int i = 0; i < 10; i++) {
            list = list.cons(i);
        }

        list = list.filter(i -> i%2 == 0);
        // will print 8,6,4,2,0
        for (int i = 8; i >= 0; i -= 2) {
            assertEquals(new Integer(i),list.head());
            list = list.tail();
        }
        assertTrue(list.isEmpty());

    }

    @Test
    @Grade
    public void testLength() {
        FList<Integer> list = FList.nil();
        assertEquals(0, list.length());

        for (int i = 0; i < 10; i++) {
            list = list.cons(i);
        }
        assertEquals(10, list.length());

        FList<Integer> list2 = list.filter(i -> i%2 == 0);
        assertEquals(5, list2.length());
    }

    @Test
    @Grade(value = 1)
    public void testIterator() {
        FList<Integer> list = FList.nil();

        for (int i = 0; i < 10; i++) {
            list = list.cons(i);
        }
        Iterator<Integer> ite = list.iterator();
        int i = 9;
        while (ite.hasNext()) {
            Integer v = ite.next();
            assertEquals(new Integer(i),v);
            i--;
        }

    }

}
