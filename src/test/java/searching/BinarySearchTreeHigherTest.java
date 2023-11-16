package searching;

import org.javagrader.Allow;
import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

// BEGIN STRIP
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
// END STRIP


@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class BinarySearchTreeHigherTest {
    
	// BEGIN STRIP
    private static final Random random = new Random(699645);
	// END STRIP
    
    @Test
    @Grade(value=5)
    @Order(1)
    public void testExample() {
        BinarySearchTreeHigher<Integer, Integer> bst = new BinarySearchTreeHigher<Integer, Integer>();

        bst.put(12,12);
        bst.put(8,8);
        bst.put(3,3);
        bst.put(11,11);
        bst.put(9,9);
        bst.put(18,18);
        bst.put(14,14);
        bst.put(15,15);
        bst.put(20,20);

        assertEquals(3, bst.minKey());
        assertEquals(3, bst.higherKey(0));
        assertEquals(9, bst.higherKey(8));
        assertEquals(12, bst.higherKey(11));
        assertEquals(18, bst.higherKey(16));
        assertEquals(null, bst.higherKey(20));

    }

    // BEGIN STRIP
    static Stream<Instance> dataProvider() {
        return IntStream.range(0, 50).mapToObj(i -> {
            BinarySearchTreeHigher<Integer,Integer> tree = new BinarySearchTreeHigher<>();
            TreeSet<Integer> set = new TreeSet<>();
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for (int k = 0; k < 15; k++) {
                int v = random.nextInt(100);
                tree.put(v,v);
                set.add(v);
                min = Math.min(min, v);
                max = Math.max(max, v);
            }
            return new Instance(tree, set, min, max);
        });
    }

    @Grade(value=1)
    @ParameterizedTest
    @MethodSource("dataProvider")
    @Order(1)
    @Allow("all")
    public void testRandomMin(Instance instance) {
        assertEquals(instance.set.first(), instance.tree.minKey());
    }

    @Grade(value=1)
    @ParameterizedTest
    @MethodSource("dataProvider")
    @Order(1)
    @Allow("all")
    public void testRandomHigher(Instance instance) {
        for (int i = 0; i < 5; i++) {
            int v = random.nextInt((instance.max - instance.min) + 1) + instance.min;
            assertEquals(instance.set.higher(v), instance.tree.higherKey(v));
        }
    }

    static IntStream dataProviderComplexity() {
        return IntStream.range(0, 5);
    }
    
    @ParameterizedTest
    @Grade(value=1, cpuTimeout=100)
    @MethodSource("dataProviderComplexity")
    @Order(2)
    public void testMinKeyComplexity(Integer i) {
        BinarySearchTreeHigher<Integer,Integer> tree = new BinarySearchTreeHigher<>();
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int k = 0; k < 100000; k++) {
            int v = random.nextInt();
            tree.put(v,v);
            min = Math.min(min, v);
            max = Math.max(max, v);
        }
        tree.minKey();
    }

    @ParameterizedTest
    @Grade(value=1, cpuTimeout=100)
    @MethodSource("dataProviderComplexity")
    @Order(2)
    public void testhigherComplexity(Integer i) {
        BinarySearchTreeHigher<Integer,Integer> tree = new BinarySearchTreeHigher<>();
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int k = 0; k < 100000; k++) {
            int v = random.nextInt();
            tree.put(v,v);
            min = Math.min(min, v);
            max = Math.max(max, v);
        }
        tree.higherKey(min + (max - min)/2);
    }
    
    private static class Instance {
        BinarySearchTreeHigher<Integer, Integer> tree;
        TreeSet<Integer> set;
        int min;
        int max;

        public Instance(BinarySearchTreeHigher<Integer, Integer> tree, TreeSet<Integer> set, int min, int max) {
            this.tree = tree;
            this.set = set;
            this.min = min;
            this.max = max;
        }
    }
    // END STRIP
}
