package searching;

import searching.BinarySearchTreeIterator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradingRunnerWithParametersFactory;
import org.junit.runners.Parameterized;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.Iterator;
// BEGIN STRIP
import java.util.Collection;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.HashSet;

import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.stream.Collectors;
// END STRIP

@RunWith(Enclosed.class)
public class BinarySearchTreeIteratorTest {

    public static class TestNotParameterized {
        @Test
        @Grade(value=1, cpuTimeout = 1000)
        @GradeFeedback(message="Sorry, something is wrong with your algorithm. Debug first on this small example", onFail=true)
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

        @Test(expected=NoSuchElementException.class)
        @Grade(value=1, cpuTimeout = 1000)
        @GradeFeedback(message="", onFail=true)
        public void testEmptyTree() {
            BinarySearchTreeIterator<Integer> tree = new BinarySearchTreeIterator<>();
            Iterator<Integer> iterator = tree.iterator();
            assertFalse(iterator.hasNext());
            iterator.next();
        }

        @Test(expected=ConcurrentModificationException.class)
        @Grade(value=1, cpuTimeout = 1000)
        @GradeFeedback(message="", onFail=true)
        public void testModificationHasNext() {
            BinarySearchTreeIterator<Integer> tree = new BinarySearchTreeIterator<>();
            tree.put(3);
            tree.put(4);
            tree.put(5);
            Iterator<Integer> iterator = tree.iterator();
            int x = iterator.next();
            tree.put(7);
            iterator.hasNext();
        }

        @Test(expected=ConcurrentModificationException.class)
        @Grade(value=1, cpuTimeout = 1000)
        @GradeFeedback(message="", onFail=true)
        public void testModificationNext() {
            BinarySearchTreeIterator<Integer> tree = new BinarySearchTreeIterator<>();
            tree.put(3);
            tree.put(4);
            tree.put(5);
            Iterator<Integer> iterator = tree.iterator();
            int x = iterator.next();
            tree.put(7);
            iterator.next();
        }

        // END STRIP
    }

    // BEGIN STRIP
    @RunWith(Parameterized.class)
    @Parameterized.UseParametersRunnerFactory(GradingRunnerWithParametersFactory.class)
    public static class TestParameterized {
        
        @Parameterized.Parameters(name="{0}")
        public static Collection data() {
            return Stream.of(new File("data/searching.BinarySearchTree").listFiles())
                .filter(file -> !file.isDirectory())
                .map(file -> new Object [] { file.getName(), new Instance(file.getPath()) })
                .collect(Collectors.toList());
        }

        final Instance instance;

        public TestParameterized(String name, Instance instance) {
            this.instance = instance;
        }

        @Test
        @Grade(value = 1, cpuTimeout = 1000)
        @GradeFeedback(message = "Sorry, something is wrong with your algorithm. Hint: debug on the small example", onFail=true)
        @GradeFeedback(message = "Check the complexity of your algorithm", onTimeout=true)
        public void test()  throws Exception {
            Iterator<Integer> iterator = instance.tree.iterator();
            for (int i = 0; i < instance.expected.length; i++) {
                assertTrue(iterator.hasNext());
                assertEquals((Integer) instance.expected[i], iterator.next());
            }
            assertTrue(!iterator.hasNext());
        }
    }

    static class Instance {
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
