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

@RunWith(Enclosed.class)
public class BinaryHeapTest {

    public static class TestNotParameterized {

        @Test
        @Grade(value=1, cpuTimeout=1000)
        @GradeFeedback(message="The minimum element is not always at the root of the tree !", onFail=true)
        public void testExample() {
            BinaryHeap heap = new BinaryHeap(100);
            heap.push(5);
            assertEquals(5, heap.getRoot());
            heap.push(1);
            assertEquals(1, heap.getRoot());
            heap.push(2);
            assertEquals(1, heap.getRoot());
            heap.push(3);
            assertEquals(1, heap.getRoot());
            heap.push(0);
            assertEquals(0, heap.getRoot());
        }

        @Test
        @Grade(value=1, cpuTimeout=1000)
        @GradeFeedback(message="Do not forget to resize if needed", onFail=true)
        public void testExampleResize() {
            BinaryHeap heap = new BinaryHeap(10);
            for (int i = 0; i < 20; i++) {
                heap.push(i);
            }
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
            BinaryHeap heap = new BinaryHeap(instance.size*2);
            int min = instance.input[0];
            for (int value : instance.input) {
                heap.push(value);
                if (value < min) {
                    min = value;
                }
                assertEquals(min, heap.getRoot());
            }
        }

        @Test
        @Grade(value = 1, cpuTimeout = 1000)
        @GradeFeedback(message = "Do not forget to resize !", onFail=true)
        @GradeFeedback(message = "Check the complexity of your algorithm", onTimeout=true)
        public void testResize() throws Exception {
            BinaryHeap heap = new BinaryHeap(instance.size / 2);
            for (int value : instance.input) {
                heap.push(value);
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
        public void test()  throws Exception {
            BinaryHeap heap = new BinaryHeap(instance.size*2);
            for (int value : instance.input) {
                heap.push(value);
            }
        }
    }

    static class Instance {
        int [] input;
        int size;

        public Instance(String file) {
            try {
                Scanner scan = new Scanner(new FileInputStream(file));
                int n = scan.nextInt();
                this.size = n;
                this.input = new int[n];
                for (int i = 0; i < n; i++) {
                    this.input[i] = scan.nextInt();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
