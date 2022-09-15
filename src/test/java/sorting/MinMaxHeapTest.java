package sorting;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradingRunnerWithParametersFactory;
import org.junit.runners.Parameterized;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import java.lang.Math;

@RunWith(Enclosed.class)
public class MinMaxHeapTest {

    public static class TestNotParameterized {

        @Test
        @Grade(value=1, cpuTimeout=1000)
        @GradeFeedback(message="The minimum element is not always at the root of the tree !", onFail=true)
        public void testExample() {
            MinMaxHeap<Integer> heap = new MinMaxHeap<>(100);
            int min;
            int max;
            heap.insert(5);
            min = heap.min();
            max = heap.max();
            assertEquals(5, min);
            assertEquals(5, max);
            heap.insert(1);
            min = heap.min();
            max = heap.max();
            assertEquals(1, min);
            assertEquals(5, max);
            heap.insert(2);
            min = heap.min();
            max = heap.max();
            assertEquals(1, min);
            assertEquals(5, max);
            heap.insert(6);
            min = heap.min();
            max = heap.max();
            assertEquals(1, min);
            assertEquals(6, max);
            heap.insert(0);
            min = heap.min();
            max = heap.max();
            assertEquals(0, min);
            assertEquals(6, max);
        }
    }

    @RunWith(Parameterized.class)
    @Parameterized.UseParametersRunnerFactory(GradingRunnerWithParametersFactory.class)
    public static class TestParameterized {

        @Parameterized.Parameters(name="{0}")
        public static Collection data() {
            return Stream.of(new File("data/sorting.BinaryHeap").listFiles())
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
        public void testMin()  throws Exception {
            MinMaxHeap<Integer> heap = new MinMaxHeap<>(instance.size*2);
            int min = instance.input[0];
            int max = instance.input[0];
            for (int value : instance.input) {
                heap.insert(value);
                min = Math.min(min, value);
                max = Math.max(max, value);
                int hmin = heap.min();
                int hmax = heap.max();
                assertEquals(min, hmin);
                assertEquals(max, hmax);
            }
        }
    }

    @RunWith(Parameterized.class)
    @Parameterized.UseParametersRunnerFactory(GradingRunnerWithParametersFactory.class)
    public static class TestComplexity {

        @Parameterized.Parameters(name="{0}")
        public static Collection data() {
            return Stream.of(new File("data/sorting.BinaryHeap").listFiles())
                .filter(file -> !file.isDirectory() && file.getName().startsWith("in_10000"))
                .map(file -> new Object [] { file.getName(), new Instance(file.getPath()) })
                .collect(Collectors.toList());
        }

        final Instance instance;

        public TestComplexity(String name, Instance instance) {
            this.instance = instance;
        }

        @Test
        @Grade(value = 1, cpuTimeout = 700)
        @GradeFeedback(message = "Check the complexity of your algorithm", onTimeout=true)
        public void testSwim()  throws Exception {
            MinMaxHeap<Integer> heap = new MinMaxHeap<>(instance.size*2);
            for (int value : instance.input) {
                heap.insert(value);
            }
        }

        @Test
        @Grade(value=1, cpuTimeout=5)
        @GradeFeedback(message="Your min method is too slow", onTimeout=true)
        public void testMin() {
            instance.heap.min();
        }

        @Test
        @Grade(value=1, cpuTimeout=5)
        @GradeFeedback(message="Your max method is too slow", onTimeout=true)
        public void testMax() {
            instance.heap.max();
        }
    }

    static class Instance {
        int [] input;
        int size;
        MinMaxHeap<Integer> heap;

        public Instance(String file) {
            try {
                Scanner scan = new Scanner(new FileInputStream(file));
                int n = scan.nextInt();
                this.size = n;
                this.input = new int[n];
                this.heap = new MinMaxHeap<>(n);
                for (int i = 0; i < n; i++) {
                    this.input[i] = scan.nextInt();
                    heap.insert(this.input[i]);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
