package fundamentals;

import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;
import sorting.Union;

import java.util.*;

import static org.junit.Assert.*;

public class FListTest {



    @Test(expected = IllegalArgumentException.class)
    @Grade(value = 1)
    public void testNil() {
        FList<Integer> list = FList.nil();
        list.head();
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

}
