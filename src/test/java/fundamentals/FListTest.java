package fundamentals;

import org.javagrader.Grade;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


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
    public void testLength(){
        FList<Integer> l = FList.nil();
        assertEquals(0, l.length()); // empty list

        // build list with 10 elements, increase length at each step
        for (int i = 0; i < 10; i++) {
            l = l.cons(i);
            assertEquals(i + 1, l.length());
        }

        // remove elements, decreasing length at each step
        for (int i = 9; i >= 0; i--) {
            l = l.tail();
            assertEquals(i, l.length());
        }

        // the list is empty at the end
        assertTrue(l.isEmpty());
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

    @Test
    @Grade(value = 1)
    public void testIterator2() {
        FList<Integer> list = FList.nil();
        // add 4 elements
        for (int i = 0; i < 4; i++) {
            list = list.cons(i);
        }

        Iterator<Integer> ite = list.iterator();
        // here's the 4 elements
        assertTrue(ite.hasNext());
        assertEquals(Integer.valueOf(3), ite.next());
        assertEquals(Integer.valueOf(2), ite.next());
        assertEquals(Integer.valueOf(1), ite.next());
        assertEquals(Integer.valueOf(0), ite.next());
        // no more elements
        assertFalse(ite.hasNext());

        // throws IllegalArgumentException (head() on Nil)
        assertThrows(IllegalArgumentException.class, () -> ite.next());
    }

}
