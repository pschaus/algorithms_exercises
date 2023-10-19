package searching;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
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
public class ArrayBSTTest {

    @Test
    @Grade(value = 1, cpuTimeout = 2000)
    @GradeFeedback(message = "Sorry, something is wrong with your get algorithm, debug first this small example")
    @Order(0)
    public void debugTestGetChar() {
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

        ArrayBST<Integer,Character> bst = new ArrayBST<>();

        bst.values = new ArrayList<>(Arrays.asList('A', 'C', 'B', 'E', 'D'));
        bst.keys   = new ArrayList<>(Arrays.asList(12, 15, 5, 8, 1));
        bst.idxLeftNode  = new ArrayList<>(Arrays.asList(2, bst.NONE, 4, bst.NONE, bst.NONE));
        bst.idxRightNode = new ArrayList<>(Arrays.asList(1, bst.NONE, 3, bst.NONE, bst.NONE));

        assertEquals(bst.get(12), Character.valueOf('A'));
        assertEquals(bst.get(8),  Character.valueOf('E'));
        assertNull(bst.get(20));
        assertEquals(bst.get(15), Character.valueOf('C'));
        assertEquals(bst.get(1),  Character.valueOf('D'));
    }



    @Test
    @Grade(value = 1, cpuTimeout = 2000)
    @Order(0)
    @GradeFeedback(message = "Sorry, something is wrong with your put algorithm, debug first this small example")
    public void debugTestPutInteger() {
        /*
         *  The tree below is the result of putting keyn,value pairs (12,A),(15,B),(5,C),(8,D),(1,E) (in this order)
         *
         *                12(A)
         *                / \
         *               /   \
         *             5(C)  15(B)
         *             / \
         *          1(E)  8(D)
         *
         *   The state of internal array list after those put operations is
         *    values:        A, B, C, D, E
         *    keys:        12, 15, 5, 8, 1
         *    idxLeftNode:  2, -1, 4,-1,-1
         *    idxRightNode: 1, -1, 3,-1,-1
         */
        ArrayBST<Integer,Character> bst = new ArrayBST<>();

        assertTrue(bst.put(12,'A'));
        assertTrue(bst.put(15,'B'));
        assertTrue(bst.put(5,'C'));
        assertTrue(bst.put(8,'D'));
        assertTrue(bst.put(1,'E'));

        assertFalse(bst.put(8,'d')); // replace value D of key 8 by d

        assertTrue(isBST(bst));
        assertArrayEquals(bst.keys.toArray(),new Integer[]{12,15,5,8,1});
        assertArrayEquals(bst.values.toArray(),new Character[]{'A','B','C','d','E'});
        assertArrayEquals(bst.idxLeftNode.toArray(),new Integer[]{2, bst.NONE,4, bst.NONE, bst.NONE});
        assertArrayEquals(bst.idxRightNode.toArray(),new Integer[]{1, bst.NONE,3, bst.NONE, bst.NONE});
        assertArrayEquals(collectIncreasing(bst).toArray(), new Integer[]{1,5,8,12,15});
    }


    // BEGIN STRIP
    @Test
    @Grade(value = 2, cpuTimeout = 2000)
    @GradeFeedback(message = "Sorry, something is wrong with your get algorithm (correctness)")
    @Order(1)
    public void correctnessGetChar() {
        /*
         *  Encoding of the tree below key(value)
         *
         *                12(A)
         *                / \
         *               /   \
         *             5(B) 15(C)
         *             /      \
         *           1(D)     18(E)
         *
         *   The state of internal array list after those put operations is
         *
         *    keys:        12, 15, 5,18, 1
         *    values:       A,  C, B, E, D
         *    idxLeftNode:  2, -1, 4,-1,-1
         *    idxRightNode: 1,  3,-1,-1,-1
         */

        ArrayBST<Integer,Character> bst = new ArrayBST<Integer,Character>();

        bst.values = new ArrayList<>(Arrays.asList('A', 'C', 'B', 'E', 'D'));
        bst.keys   = new ArrayList<>(Arrays.asList(12, 15, 5, 8, 1));
        bst.idxLeftNode  = new ArrayList<>(Arrays.asList(2, bst.NONE, 4, bst.NONE, bst.NONE));
        bst.idxRightNode = new ArrayList<>(Arrays.asList(1, bst.NONE, 3, bst.NONE, bst.NONE));

        assertEquals(bst.get(12), Character.valueOf('A'));
        assertEquals(bst.get(8), Character.valueOf('E'));
        assertNull(bst.get(20));
        assertEquals(bst.get(15), Character.valueOf('C'));
        assertEquals(bst.get(1), Character.valueOf('D'));
    }


    @Test
    @Grade(value = 2, cpuTimeout = 2000)
    @GradeFeedback(message = "Sorry, something is wrong with your put algorithm (correctness)")
    @Order(1)
    public void correctnessPutInteger() {

        ArrayBST<Integer,Integer> bst = new ArrayBST<>();
        assertEquals(4,bst.getClass().getFields().length);

        int [] keys = new int[]   {3,6,2,8 ,10,20,-1,50,7};
        int [] values = new int[] {1,5,8,-8,12,25,-5,52,9};
        int [] notKeys = new int[]{5,11,21,9};

        for (int i = 0; i < keys.length; i++) {
            bst.put(keys[i],values[i]);
        }

        assertTrue(isBST(bst));

        for (int i = 0; i < keys.length; i++) {
            assertEquals(Integer.valueOf(values[i]),bst.get(keys[i]));
        }
        for (int notKey : notKeys) {
            assertNull(bst.get(notKey));
        }
    }




    @Test
    @Grade(value = 2, cpuTimeout = 2000)
    @Order(1)
    @GradeFeedback(message = "Sorry, something is wrong with your get algorithm; correctness and/or not in O(h) time complexity")
    public void testGetInteger() {
        ArrayBST<Integer,Integer> bst = new ArrayBST<>();
        bst.values = new ArrayList<>(Arrays.asList(18, 21, 9, 15, 16, 23, 2, 0, 19, 24, 9, 21, 6, 24, 17, 21, 23, 6, 19, 15, 9, 6, 11, 4, 16, 21, 16, 14, 5, 4, 14, 5, 18, 16, 0, 7, 14, 20, 14, 7, 3, 4, 23, 1, 22, 18, 15, 6, 11, 13, 25, 25, 25, 1, 23, 1, 25, 3, 12, 17, 16, 10, 15, 17, 2, 22, 17, 5, 1, 6, 15, 19, 14, 21, 15, 9, 9, 0, 18, 2, 17, 5, 17, 11, 7, 7, 7, 6, 5, 15, 3, 12, 2, 18));
        bst.keys   = new ArrayList<>(Arrays.asList(360, 29, 515, 491, 719, 77, 473, 95, 84, 241, 143, 524, 652, 503, 692, 745, 37, 602, 125, 938, 960, 555, 198, 74, 112, 319, 416, 207, 608, 387, 105, 408, 451, 358, 738, 457, 726, 297, 135, 267, 117, 905, 789, 901, 223, 668, 916, 957, 605, 536, 667, 331, 688, 730, 298, 797, 286, 290, 271, 986, 984, 792, 428, 49, 141, 325, 294, 517, 671, 577, 835, 913, 616, 440, 155, 508, 715, 633, 441, 98, 78, 67, 539, 702, 477, 978, 784, 589, 860, 872, 13, 953, 380, 734));
        bst.idxLeftNode  = new ArrayList<>(Arrays.asList(1, 90, 3, 6, 11, 16, 26, 8, 80, 10, 18, 67, 17, -1, 45, 34, -1, 21, 24, 41, 47, 49, 74, 63, 30, 37, 29, -1, 48, 92, 79, -1, 62, 51, 36, -1, -1, 39, -1, -1, -1, 42, 86, 55, -1, 50, 71, 91, -1, -1, -1, 65, 68, -1, -1, 61, 58, -1, -1, 60, 85, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 83, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1));
        bst.idxRightNode = new ArrayList<>(Arrays.asList(2, 5, 4, 13, 15, 7, 84, 9, -1, 25, 22, 12, 14, 75, 76, 19, 23, 28, 38, 20, 59, 69, 27, -1, 40, 33, 32, 44, 72, 31, -1, -1, 35, -1, -1, -1, 53, 54, 64, 56, -1, 46, 43, -1, -1, 52, -1, -1, -1, 82, -1, -1, -1, 93, -1, 70, 57, 66, -1, -1, -1, -1, 73, 81, -1, -1, -1, -1, -1, 87, 88, -1, 77, 78, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 89, -1, -1, -1, -1, -1));

        Random rnd = new Random(0);
        for (int i = 0; i < 2000; i++) {
            int idx = rnd.nextInt(bst.values.size());
            assertEquals(bst.get(bst.keys.get(idx)),bst.values.get(idx));
        }
        assertNull(bst.get(200));
//        testGetComplexity(); // will cause a timeout if not O(h)
    }


    @Test
    @Order(2)
    @Grade(value = 2, cpuTimeout = 1000)
    public void testGetComplexity() {
        ArrayBST<Integer,Integer> bst = new ArrayBST<>();
        int n = 100000;
        for (int i = 0; i < n; i++) {
            bst.values.add(i);
            bst.keys.add(i);
            bst.idxRightNode.add(i+1);
            bst.idxLeftNode.add(bst.NONE);
        }
        bst.idxRightNode.set(n-1, bst.NONE);

        // insert -1
        bst.idxLeftNode.set(0,n);
        bst.idxRightNode.add(bst.NONE);
        bst.idxLeftNode.add(bst.NONE);
        bst.keys.add(-1);
        bst.values.add(-1);

        for (int i = 0; i < 100000; i++) {
            bst.get(-1);
            assertEquals(Integer.valueOf(-1),bst.get(-1));
        }
    }


    @Test
    @Grade(value = 2, cpuTimeout = 1000)
    @Order(1)
    @GradeFeedback(message = "Sorry, something is wrong with your put/get algorithm: correctness and/or not in O(h) time complexity")
    public void testRandomPutInteger() {

        ArrayBST<Integer,Integer> bst = new ArrayBST<>();
        assertEquals(4,bst.getClass().getFields().length);

        TreeMap<Integer,Integer> rbt = new TreeMap<>(); // java red-black tree
        Random rnd = new Random(0);
        for (int i = 0; i < 100000; i++) {
            // put some random (key, value) in the two bst
            int key = rnd.nextInt(100000);
            int value = rnd.nextInt(100000);
            bst.put(key, value);
            rbt.put(key,value);
        }
        // verify that it is well a bst
        assertTrue(isBST(bst));
        // and that values are present/absent as expected
        for (int i = 0; i < 100000; i++) {
            int key = rnd.nextInt(100000);
            assertEquals(bst.get(key),rbt.get(key));
        }
    }
    // END STRIP


    // helper methods for the tests

    public static <Key extends Comparable<Key>,Value> boolean  isBST(ArrayBST<Key,Value> bst) {
        return isBST(bst,0, null, null);
    }

    private static <Key extends Comparable<Key>,Value> boolean isBST(ArrayBST<Key,Value> bst, int index, Key min, Key max) {
        if (index == bst.NONE) return true;
        if (min != null && bst.keys.get(index).compareTo(min) <= 0) return false;
        if (max != null && bst.keys.get(index).compareTo(max) >= 0) return false;
        return isBST(bst,bst.idxLeftNode.get(index), min, bst.keys.get(index)) && isBST(bst,bst.idxRightNode.get(index), bst.keys.get(index), max);
    }

    private static <Key extends Comparable<Key>,Value> List<Key>  collectIncreasing(ArrayBST<Key,Value> bst) {
        List<Key> result = new LinkedList<>();
        collectIncreasing(bst,0,result);
        return result;
    }

    private static <Key extends Comparable<Key>,Value> void  collectIncreasing(ArrayBST<Key,Value> bst, int index, List<Key> increasing) {
        if (bst.idxLeftNode.get(index) != bst.NONE) {
            collectIncreasing(bst,bst.idxLeftNode.get(index),increasing);
        }
        increasing.add(bst.keys.get(index));
        if (bst.idxRightNode.get(index) != bst.NONE) {
            collectIncreasing(bst,bst.idxRightNode.get(index),increasing);
        }
    }


}



