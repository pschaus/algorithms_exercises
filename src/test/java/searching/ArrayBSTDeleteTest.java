package searching;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.javagrader.TestResultStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class ArrayBSTDeleteTest {

    @Test
    @Order(0)
    @Grade(value = 1, cpuTimeout = 2000)
    @GradeFeedback(message = "Congrats!", on= TestResultStatus.SUCCESS)
    @GradeFeedback(message = "Something is wrong, understand the test code and debug locally", on=TestResultStatus.FAIL)
    @GradeFeedback(message = "Too slow, infinite loop ?", on=TestResultStatus.TIMEOUT)
    public void debugDelete() {
        /*
         *  Encoding of the tree below key(value)
         *
         *                12(A)
         *                / \
         *               /   \
         *             5(B) 15(C)
         *             / \
         *           1(D) 8(E)
         *
         *   The state of internal array list after those put operations is
         *
         *    keys:        12, 15, 5, 8, 1
         *    values:       A,  C, B, E, D
         *    idxLeftNode:  2, -1, 4,-1,-1
         *    idxRightNode: 1, -1, 3,-1,-1
         */
        ArrayBSTDelete<Integer,Character> bst = new ArrayBSTDelete<>();

        bst.put(12,'A');
        bst.put(15,'C');
        bst.put(5,'B');
        bst.put(8,'E');
        bst.put(1,'D');

        assertEquals(new ArrayList<>(Arrays.asList('A', 'C', 'B', 'E', 'D')), bst.values);
        assertEquals(new ArrayList<>(Arrays.asList(12, 15, 5, 8, 1)), bst.keys);
        assertEquals(new ArrayList<>(Arrays.asList(2, bst.NONE, 4, bst.NONE, bst.NONE)), bst.idxLeftNode);
        assertEquals(new ArrayList<>(Arrays.asList(1, bst.NONE, 3, bst.NONE, bst.NONE)), bst.idxRightNode);

        assertTrue(bst.delete(5)); // true since the key was present
        assertFalse(bst.delete(5));// false since the key is not present

        assertEquals('A',bst.get(12));
        assertNull(bst.get(5)); // 5 is deleted

        assertTrue(bst.put(5, 'F'));  // key inserted, was not present
        assertFalse(bst.put(5, 'F')); // key already present
        assertFalse(bst.put(5, 'G')); // key already present (but value erased)

        assertEquals('G',bst.get(5));
        assertEquals('E',bst.get(8));
        assertNull(bst.get(16)); // key not present

        assertTrue(bst.values.size() <= 5);

    }

    // BEGIN STRIP
    @Test
    @Order(0)
    @Grade(value = 1, cpuTimeout = 2500)
    @GradeFeedback(message = "Congrats!", on= TestResultStatus.SUCCESS)
    @GradeFeedback(message = "The size of key vector is growing too quickly, or you have a bug in get/put/delete", on=TestResultStatus.FAIL)
    @GradeFeedback(message = "Too slow: a get / put / delete should be in O(h) where h is the height", on=TestResultStatus.TIMEOUT)
    public void multipleDeleteAndPutAndGet() {
        Set<Integer> keys = new HashSet<>();
        ArrayBSTDelete<Integer, Integer> bst = new ArrayBSTDelete<>();
        TreeMap<Integer, Integer> rbt = new TreeMap<>(); // java red-black tree

        Random rnd = new Random(41);
        int maxSize = 10000;

        for (int i = 0; i < maxSize; i++) {
            // put some random (key, value) in the two bst
            int key = rnd.nextInt(maxSize);
            int value = rnd.nextInt(maxSize);
            bst.put(key, value);
            rbt.put(key, value);
            keys.add(key);

            // delete a random key
            key = rnd.nextInt(maxSize);
            if (rnd.nextBoolean()) {
                if (rbt.containsKey(key)) {
                    assertTrue(bst.delete(key));
                    rbt.remove(key);
                } else {
                    assertFalse(bst.delete(key));
                }
            }

            // do a random get
            key = rnd.nextInt(maxSize);
            assertEquals(rbt.get(key),bst.get(key));
        }
        assertTrue(bst.keys.size() <= keys.size());
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    @Order(1)
    @GradeFeedback(message = "Congrats!", on= TestResultStatus.SUCCESS)
    @GradeFeedback(message = "Remember BST rules: keys on the left/right should be all </> by following the idxLeftNode/idxRightNode from the root", on=TestResultStatus.FAIL)
    @GradeFeedback(message = "Too slow: a get / put / delete should be in O(h) where h is the height", on=TestResultStatus.TIMEOUT)
    public void testWellFormedBSTAfterRandomPutDelete() {
        ArrayBSTDelete<Integer, Integer> bst = new ArrayBSTDelete<>();
        TreeMap<Integer, Integer> rbt = new TreeMap<>(); // java red-black tree
        Random rnd = new Random(41);
        int maxSize = 10000;

        for (int i = 0; i < maxSize; i++) {
            // put some random (key, value) in the two bst
            int key = rnd.nextInt(maxSize);
            int value = rnd.nextInt(maxSize);
            bst.put(key, value);
            rbt.put(key, value);

            // delete a random key
            key = rnd.nextInt(maxSize);
            if (rnd.nextBoolean()) {
                if (rbt.containsKey(key)) {
                    assertTrue(bst.delete(key));
                    rbt.remove(key);
                } else {
                    assertFalse(bst.delete(key));
                }
            }

            // do a random get
            key = rnd.nextInt(maxSize);
            assertEquals(rbt.get(key),bst.get(key));

        }

        // assertTrue(isBST(bst));

        // Getting keys in ordered way in arraylist from treemap
        List<Integer> rbtKeys = new ArrayList<>(rbt.keySet());

        // Getting keys in ordered way in arraylist from arraybst
        List<Integer> bstKeys = collectIncreasing(bst);

        // Comparing the two arraylists
        assertEquals(rbtKeys, bstKeys);

    }

    // helper methods for the tests

    public static <Key extends Comparable<Key>,Value> boolean  isBST(ArrayBSTDelete<Key,Value> bst) {
        return isBST(bst,0, null, null);
    }

    private static <Key extends Comparable<Key>,Value> boolean isBST(ArrayBSTDelete<Key,Value> bst, int index, Key min, Key max) {
        if (index == bst.NONE) return true;
        if (min != null && bst.keys.get(index).compareTo(min) <= 0) return false;
        if (max != null && bst.keys.get(index).compareTo(max) >= 0) return false;
        return isBST(bst,bst.idxLeftNode.get(index), min, bst.keys.get(index)) && isBST(bst,bst.idxRightNode.get(index), bst.keys.get(index), max);
    }

    private static <Key extends Comparable<Key>,Value> List<Key> collectIncreasing(ArrayBSTDelete<Key,Value> bst) {
        List<Key> result = new LinkedList<>();
        collectIncreasing(bst,0,result);
        return result;
    }

    private static <Key extends Comparable<Key>,Value> void  collectIncreasing(ArrayBSTDelete<Key,Value> bst, int index, List<Key> increasing) {
        if (bst.idxLeftNode.get(index) != bst.NONE) {
            collectIncreasing(bst,bst.idxLeftNode.get(index),increasing);
        }
        if (bst.get(bst.keys.get(index)) != null) increasing.add(bst.keys.get(index));
        if (bst.idxRightNode.get(index) != bst.NONE) {
            collectIncreasing(bst,bst.idxRightNode.get(index),increasing);
        }
    }
    // END STRIP

}
