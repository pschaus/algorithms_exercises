package searching;

import utils.BinaryNode;
import utils.BSTNode;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradingRunnerWithParametersFactory;
import org.junit.runners.Parameterized;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.TreeSet;
// BEGIN STRIP
import java.util.Collection;
import java.util.LinkedList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.stream.Collectors;
// END STRIP

@RunWith(Enclosed.class)
public class BinarySearchTreeTest {

    public static class TestNotParameterized {

        @Test
        @Grade(value=1, cpuTimeout = 1000)
        @GradeFeedback(message="Sorry, something is wrong with your algorithm. Debug first on this small example", onFail=true)
        public void  testExample() {
            TreeSet<Integer> correct = new java.util.TreeSet<>();
            int values [] = new int []{12, 8, 18, 3, 11, 14, 20, 9, 15};
            int [] inputs = new int []{11, 14, 9, 4, 16, 10, 19, 21, 30, 40};

            BSTNode root = new BSTNode(values[0]);
            correct.add(values[0]);
            for (int i = 0; i < values.length; i++) {
                root.add(values[i]);
                correct.add(values[i]);
            }

            for (int i: inputs) {
                assertEquals(correct.ceiling(i), BinarySearchTree.ceil(root, i));
            }

        }
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
            assertEquals(instance.correct.ceiling(instance.query), BinarySearchTree.ceil(instance.root, instance.query));
        }
    }

    static class Instance {
        BSTNode root;
        TreeSet<Integer> correct;
        int query;

        public Instance(String file) {
            try {
                Scanner scan = new Scanner(new FileInputStream(file));
                int n = scan.nextInt();
                int [] input = new int[n];
                for (int i = 0; i < n; i++) {
                    input[i] = scan.nextInt();
                }
                this.query = scan.nextInt();
                this.root = new BSTNode(input[0]);
                this.correct = new TreeSet<>();
                this.correct.add(input[0]);
                for (int i = 1; i < input.length; i++) {
                    this.root.add(input[i]);
                    this.correct.add(input[i]);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // END STRIP
}
