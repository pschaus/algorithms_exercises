package searching;

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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.util.Iterator;
// BEGIN STRIP
import java.util.Arrays;
import java.util.HashSet;

import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Stream;
// END STRIP

@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class BinarySearchTreeIteratorTest {

    @Test
    @Grade(value=1, cpuTimeout = 1000)
    @GradeFeedback(message="Sorry, something is wrong with your algorithm. Debug first on this small example")
    public void  testExample() {
        BinarySearchTreeIterator<Integer> tree = new BinarySearchTreeIterator<>();
        int [] values = new int []{12, 8, 18, 3, 11, 14, 20, 9, 15};
        for (int v : values) {
            tree.put(v);
        }
        Integer [] output = new Integer []{3, 8, 9, 11, 12, 14, 15, 18, 20};

        Iterator<Integer> iter = tree.iterator();
        for (int i = 0; i < output.length; i++) {
            assertTrue(iter.hasNext());
            assertEquals(output[i], iter.next());
        }
        assertTrue(!iter.hasNext());
    }

    // BEGIN STRIP

    @Test
    @Grade(value=1, cpuTimeout = 1000)
    @GradeFeedback(message="You do not raise a NoSuchElementException when the iterator is empty and we try to get the next element")
    @Order(1)
    public void testEmptyTree() {
        BinarySearchTreeIterator<Integer> tree = new BinarySearchTreeIterator<>();
        Iterator<Integer> iterator = tree.iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    @Test
    @Grade(value=1, cpuTimeout = 1000)
    @GradeFeedback(message="You do not raise a ConcurrentModificationException when the tree is modified during iteration and hasNext is called")
    @Order(1)
    public void testModificationHasNext() {
        BinarySearchTreeIterator<Integer> tree = new BinarySearchTreeIterator<>();
        tree.put(3);
        tree.put(4);
        tree.put(5);
        Iterator<Integer> iterator = tree.iterator();
        iterator.next();
        tree.put(7);
        assertThrows(ConcurrentModificationException.class, () -> iterator.hasNext());
    }

    @Test
    @Grade(value=1, cpuTimeout = 1000)
    @GradeFeedback(message="You do not raise a ConcurrentModificationException when the tree is modified during iteration and next is called")
    @Order(1)
    public void testModificationNext() {
        BinarySearchTreeIterator<Integer> tree = new BinarySearchTreeIterator<>();
        tree.put(3);
        tree.put(4);
        tree.put(5);
        Iterator<Integer> iterator = tree.iterator();
        iterator.next();
        tree.put(7);
        assertThrows(ConcurrentModificationException.class, () -> iterator.next());
    }
    
    static Stream<Instance> dataProvider() {
        return Stream.of(new File("data/searching.BinarySearchTree").listFiles())
            .filter(file -> !file.isDirectory())
            .map(file -> new Instance(file.getPath()));
    }
    
    @ParameterizedTest
    @Grade(value = 1, cpuTimeout = 1000)
    @MethodSource("dataProvider")
    public void testParameterized(Instance instance) {
        Iterator<Integer> iterator = instance.tree.iterator();
        for (int i = 0; i < instance.expected.length; i++) {
            assertTrue(iterator.hasNext());
            assertEquals((Integer) instance.expected[i], iterator.next());
        }
        assertTrue(!iterator.hasNext());
    }

    private static class Instance {
        BinarySearchTreeIterator<Integer> tree = new BinarySearchTreeIterator<>();
        int [] expected;

        public Instance(String file) {
            try {
                Scanner scan = new Scanner(new FileInputStream(file));
                HashSet<Integer> s = new HashSet<>();
                int n = scan.nextInt();
                this.tree = new BinarySearchTreeIterator<>();
                for (int i = 0; i < n; i++) {
                    int v = scan.nextInt();
                    tree.put(v);
                    s.add(v);
                }
                this.expected = new int[s.size()];
                Iterator<Integer> it = s.iterator();
                for (int i = 0; i < expected.length; i++) {
                    this.expected[i] = it.next();
                }
                Arrays.sort(this.expected);
                scan.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
	// END STRIP
}

