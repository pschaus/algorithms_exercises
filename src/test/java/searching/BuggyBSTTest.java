package searching;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.javagrader.TestResultStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Grade
@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BuggyBSTTest {

    private static final Random random = new Random(569663);

    @Test
    @Grade(value = 1)
    @Order(1)
    @GradeFeedback(message = "Your algorithm does not correctly fix the tree on the given example. Debug it locally", on = TestResultStatus.FAIL)
    public void testSimple1() {

        /*
         *  This is a problematic BST
         *
         *                      (49)
         *                     /    \
         *                   (32)*   (55)
         *                  /    \
         *                 (6)   (27)
         *                /        \
         *              (1)        (33)
         *
         */
        Node root = new Node(49);
        root.left = new Node(32);
        root.right = new Node(55);
        root.left.left = new Node(6);
        root.left.right = new Node(27);
        root.left.left.left = new Node(1);
        root.left.right.right = new Node(33);

        BuggyBST.fix(root);
        // The fix it to replacing key node (32) by any key between 6 and 27
        assertTrue(root.left.key < 27);
        assertTrue(root.left.key > 6);

        // Notice that we assume that you only changed one key and
        // let the structure of the tree untouched
        // We do not verify it here for keeping this test simple and readable
    }


    @Test
    @Grade(value = 1)
    @Order(1)
    @GradeFeedback(message = "Your algorithm does not correctly fix the tree on the given example. Debug it locally", on = TestResultStatus.FAIL)
    public void testSimple2() {

        /*
         *  This is a problematic BST
         *
         *                      (49)
         *                     /    \
         *                   (12)   (55)
         *                  /    \
         *                 (6)   (27)
         *                /
         *              (7*)
         *
         */
        Node root = new Node(49);
        root.left = new Node(12);
        root.right = new Node(55);
        root.left.left = new Node(6);
        root.left.right = new Node(27);
        root.left.left.left = new Node(7);

        BuggyBST.fix(root);
        // The fix it either to replace the key 7 by a key less than 6
        // or to replace the key 6 by a key greater than 7 and less than 12
        assertTrue(root.left.left.left.key < 6 || (root.left.left.key > 7 && root.left.left.key < 12));
        // Notice that we assume that you only changed one key and
        // let the structure of the tree untouched
        // We do not verify it here for keeping this test simple and readable
    }


    // few helper methods to create a BST
    private Node put(Node node, Integer key) {
        if (node == null) return new Node(key);
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = put(node.left, key);
        else if (cmp > 0) node.right = put(node.right, key);
        return node;
    }

    private int makeRandomBST(Node root, int size) {
        int max = random.nextInt(size);
        root.key = max;
        for (int i = 0; i < size; i++) {
            int key = random.nextInt(size);
            if (key > max) max = key;
            put(root, key);
        }
        return max + random.nextInt(size);
    }

    public ArrayList<Integer> collectPreorder(Node node, ArrayList<Integer> keys) {
        if (node == null) return keys;
        keys.add(node.key);
        collectPreorder(node.left, keys);
        collectPreorder(node.right, keys);
        return keys;
    }

    public ArrayList<Integer> collectInorder(Node node, ArrayList<Integer> keys) {
        if (node == null) return keys;
        collectInorder(node.left, keys);
        keys.add(node.key);
        collectInorder(node.right, keys);
        return keys;
    }


    @Test
    @Grade(value = 3)
    @Order(2)
    @GradeFeedback(message = "Your algorithm does not work when the lowest key is wrong. Also be sure to only modify one node.", on = TestResultStatus.FAIL)
    public void testChangeLowestKey() {

        Node root = new Node(1);
        int randomKey = makeRandomBST(root, 100);

        Node minNode = getMin(root);
        minNode.key = randomKey;

        test(root);
    }


    @Test
    @Grade(value = 3)
    @Order(2)
    @GradeFeedback(message="Your algorithm does not work when the highest key is wrong. Be sure to only modify one node.", on= TestResultStatus.FAIL)
    public void testChangeHighestKey() {

        Node root = new Node(1);
        makeRandomBST(root, 100);
        Node maxNode = getMax(root);
        maxNode.key = Integer.MIN_VALUE;

        test(root);
    }

    @Test
    @Grade(value = 3)
    @Order(2)
    @GradeFeedback(message="Your algorithm does not work when  the root is wrong. Also be sure to only modify one node.", on= TestResultStatus.FAIL)
    public void testChangeRoot1() {

        Node root = new Node(1);
        makeChain(root, true, 100);
        root.key = Integer.MAX_VALUE;
        test(root);

    }

    @Test
    @Grade(value = 3)
    @Order(2)
    @GradeFeedback(message="Your algorithm does not work when  the root is wrong. Also be sure to only modify one node.", on= TestResultStatus.FAIL)
    public void testChangeRoot2() {

        Node root = new Node(1);
        makeChain(root, false, 100);
        root.key = Integer.MIN_VALUE;
        test(root);

    }

    @Test
    @Grade(value = 3, cpuTimeout = 500, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @Order(3)
    @GradeFeedback(message = "Your algorithm does not correctly fix the tree on the given example. Debug it locally and check its complexity", on = TestResultStatus.FAIL)
    public void randomTest() {
        for (int iter = 0; iter < 10000; iter++) {
            Node root = new Node(1);
            int randomKey = makeRandomBST(root, 100);
            Node randomNode = getRandomNode(root);
            randomNode.key = randomKey;
            test(root);

        }
    }

    // Helper for tests
    private void test(Node root) {
        ArrayList<Integer> preOrderBefore = collectPreorder(root, new ArrayList<>());

        BuggyBST.fix(root);

        ArrayList<Integer> sortedKeysAfter = collectInorder(root, new ArrayList<>());
        ArrayList<Integer> preOrderAfter = collectPreorder(root, new ArrayList<>());

        // verify that the keys are sorted after the fix
        for (int i = 0; i < sortedKeysAfter.size() - 1; i++) {

            assertTrue(sortedKeysAfter.get(i) < sortedKeysAfter.get(i + 1));
        }
        // verify that the tree is not changed, only one value changed in the preorder
        int nChanged = 0;
        for (int i = 0; i < preOrderBefore.size(); i++) {
            if (!preOrderBefore.get(i).equals(preOrderAfter.get(i))) {
                nChanged++;
            }
        }
        assertTrue(nChanged <= 1);

    }


    private Node getRandomNode(Node root) {
        if (root.left != null && random.nextBoolean()) {
            return getRandomNode(root.left);
        } else if (root.right != null && random.nextBoolean()) {
            return getRandomNode(root.right);
        } else {
            return root;
        }
    }


    private Node getMin (Node root) {
        Node min = root;
        while (min.left != null) {
            min = min.left;
        }
        return min;
    }

    private Node getMax (Node root) {
        Node max = root;
        while (max.right != null) {
            max = max.right;
        }
        return max;
    }

    private void makeChain(Node root, boolean right, int size) {
        int key = random.nextInt(size);
        root.key = key;
        for (int i = 1; i < size; i++) {
            key = right ? key + random.nextInt(50) : key - random.nextInt(50);
            put(root, key);
        }
    }
}
