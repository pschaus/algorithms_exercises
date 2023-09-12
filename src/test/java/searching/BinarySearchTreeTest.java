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


import java.util.TreeSet;
// BEGIN STRIP

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Stream;
// END STRIP

@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class BinarySearchTreeTest {


    @Test
    @Grade(value=1, cpuTimeout = 1000)
    @GradeFeedback(message="Sorry, something is wrong with your algorithm. Debug first on this small example")
    @Order(1)
    public void  testExample() {
        TreeSet<Integer> correct = new java.util.TreeSet<>();
        int [] values = new int []{12, 8, 18, 3, 11, 14, 20, 9, 15};
        int [] inputs = new int []{11, 14, 9, 4, 16, 10, 19, 21, 30, 40};

        BinarySearchTree.BSTNode<Integer> root = new BinarySearchTree.BSTNode<>(values[0]);
        correct.add(values[0]);
        for (int i = 0; i < values.length; i++) {
            root.add(values[i]);
            correct.add(values[i]);
        }

        for (int i: inputs) {
            assertEquals(correct.ceiling(i), BinarySearchTree.ceil(root, i));
        }
    }
    
    // BEGIN STRIP
    
    static Stream<Instance> dataProvider() {
        return Stream.of(new File("data/searching.BinarySearchTree").listFiles())
            .filter(file -> !file.isDirectory())
            .map(file -> new Instance(file.getPath()));
    }

    @ParameterizedTest
    @Grade(value = 1, cpuTimeout = 1000)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm. Hint: debug on the small example")
    @MethodSource("dataProvider")
    @Order(2)
    public void testRandom(Instance instance)  throws Exception {
        assertEquals(instance.correct.ceiling(instance.query), BinarySearchTree.ceil(instance.root, instance.query));
    }

    @ParameterizedTest
    @Grade(value = 1, cpuTimeout = 1000)
    @GradeFeedback(message = "Check the complexity of your algorithm")
    @MethodSource("dataProvider")
    @Order(3)
    public void testComplexity(Instance instance)  throws Exception {
        assertEquals(instance.correct.ceiling(instance.query), BinarySearchTree.ceil(instance.root, instance.query));
    }

    static class Instance {
        BinarySearchTree.BSTNode<Integer> root;
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
                this.root = new BinarySearchTree.BSTNode<>(input[0]);
                this.correct = new TreeSet<>();
                this.correct.add(input[0]);
                for (int i = 1; i < input.length; i++) {
                    this.root.add(input[i]);
                    this.correct.add(input[i]);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    // END STRIP
}
