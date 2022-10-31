package searching;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(Enclosed.class)
public class Tree23Test {

    /**
     * check if a tree is balanced => same number of connexions between root and leafs
     * @return -1 if the length in some of the children is different
     *          height of the tree if the root has the same number of connexions in it's children
     */
    public static int isBalancedHelp(Node23<Integer> tree) {
        if (tree.isLeaf()) return 1;
        int m = isBalancedHelp(tree.middleChild);
        int l = isBalancedHelp(tree.leftChild);
        int r = l;
        if (tree.is3Node()) r = isBalancedHelp(tree.rightChild);
        if (l < 0 || l != r || r != m) return -1;
        return l + 1;
    }

    /**
     * @param tree : the root of the tree
     * @return true if this tree is balanced (same number of connexions between root and leafs)
     */
    public static boolean isBalanced(Node23<Integer> tree){
        return isBalancedHelp(tree) >= 0;
    }

    /**
     * @param n : the node in which we search a key (3Node)
     * @param k : the key to search
     * @return the value associated with the key if it is in the tree, null otherwise
     */
    public static Integer search3Node(Node23<Integer> n, Integer k) {
        if (n.leftKey.equals(k)) return n.leftValue;
        if (n.rightKey.equals(k)) return n.rightValue;

        if (k.compareTo(n.leftKey) > 0 && k.compareTo(n.rightKey) < 0) return search(n.middleChild, k);
        else if (k.compareTo(n.leftKey) < 0) return search(n.leftChild, k);
        else return search(n.rightChild, k);
    }

    /**
     * @param n : the node in which we search a key (2Node)
     * @param k : the key to search
     * @return the value associated with the key if it is in the tree, null otherwise
     */
    public static Integer search2Node(Node23<Integer> n, Integer k) {
        if (n.leftKey.equals(k)) return n.leftValue;

        if (k.compareTo(n.leftKey) > 0) return search(n.middleChild, k);
        else return search(n.leftChild, k);
    }

    /**
     * @param n : the node in which we search a key
     * @param k : the key to search
     * @return the value associated with the key if it is in the tree, null otherwise
     */
    public static Integer search(Node23<Integer> n, Integer k){
        if (n == null) return null;
        if (n.is2Node()) return search2Node(n, k);
        else return search3Node(n, k);
    }

    public static void inorder(Node23<Integer> n, Stack<Integer> s){
        if (n == null) return;
        if (n.is2Node()){
            inorder(n.leftChild, s);
            s.push(n.leftKey);
            inorder(n.middleChild, s);
        }else{
            inorder(n.leftChild, s);
            s.push(n.leftKey);
            inorder(n.middleChild, s);
            s.push(n.rightKey);
            inorder(n.rightChild, s);
        }
    }

    /**
     * check if this tree is ordered, thus :
     * if n is a 2Node:
     * all keys in the leftChild are < leftKey
     * all keys in the rightChild are > leftKey
     *
     * if n is a 3Node:
     * all keys in the leftChild are < leftKey
     * all keys in the middleChild are < rightKey && > leftKey
     * all keys in the rightChild are > rightKey
     *
     * @param n : the node to verify
     * @return true if this tree is an ordered tree
     *         false otherwise
     */
    public static boolean isBst(Node23<Integer> n) {
        Stack<Integer> st = new Stack<>();
        inorder(n, st);
        Integer[] arr = st.toArray(new Integer[]{});
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(arr[i-1]) < 0) return false;
        }
        return true;
    }

    public static class TestNotParameterized {

        @Test
        @Grade(value = 5, cpuTimeout = 1000)
        @GradeFeedback(message = "Sorry, something is wrong with your algorithm. Debug first on this small example", onFail = true)
        public void testSimple() {

            Node23<Integer> n = new Node23<>(0,0,null, null);
            for (int i = 1; i < 10; i++) {
                n = n.put(i, i);
            }
            for (int i = 0; i < 10; i++) {
                assertEquals((Integer) i, search(n, i));
            }

            assertEquals(search(n, 11), null);
        }

        @Test
        @Grade(value = 5, cpuTimeout = 1000)
        @GradeFeedback(message = "Sorry, something is wrong with your algorithm. Debug first on this small example", onFail = true)
        public void testSimple2() {

            Node23<Integer> n = new Node23<>(10,10,null, null);
            for (int i = 9; i >= 0; i--) {
                n = n.put(i, i);
            }
            for (int i = 0; i < 10; i++) {
                assertEquals(search(n, i), (Integer) i);
            }

            assertEquals(search(n, 11), null);
        }

        @Test
        @Grade(value = 30, cpuTimeout = 2000)
        @GradeFeedback(message = "Your tree isn't balanced when inserting keys", onFail = true)
        public void isBalancedTest() {
            Node23<Integer> n = new Node23<Integer>(0,0,null, null);
            for (int i = 1; i < 1000; i++) {
                n = n.put(i, i);
                assertTrue(isBalanced(n));
            }

        }

        @Test
        @Grade(value = 30, cpuTimeout = 2000)
        @GradeFeedback(message = "The keys in your tree aren't in the good place", onFail = true)
        public void isBstTest() {
            Node23<Integer> n = new Node23(0,0,null, null);
            for (int i = 1; i < 1000; i++) {
                n = n.put(i, i);
                assertTrue(isBst(n));
            }
        }

        @Test
        @Grade(value = 30, cpuTimeout = 2000)
        @GradeFeedback(message = "There is an error in your tree when inserting random keys", onFail = true)
        public void randomTest() {
            Node23<Integer> n = new Node23(0,0,null, null);
            HashMap<Integer, Integer> correct = new HashMap<>();
            Random rng = new Random();
            for (int i = 0; i < 1000; i++) {
                int rand = rng.nextInt(5000);
                if (correct.containsKey(rand)) continue;
                n = n.put(i, i);
                correct.put(i, i);
            }

            for (Integer i : correct.keySet()) {
                assertEquals(search(n, i), (Integer) i); // check if all keys are present
            }

            for (int i = 0; i < 1000; i++) {
                int rand = rng.nextInt(5000);
                assertEquals(search(n, rand), correct.get(rand));
            }
        }
    }

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
        public void test()  throws Exception {
            Set<Integer> lst = instance.s.keySet();
            for (Integer i : lst){
                assertEquals(search(instance.tree, i), instance.s.get(i));
            }
            assertTrue(isBalanced(instance.tree));
            assertTrue(isBst(instance.tree));
        }
    }

    static class Instance {
        Node23<Integer> tree = null;
        HashMap<Integer, Integer> s = new HashMap<>();

        public Instance(String file) {
            try {
                Scanner scan = new Scanner(new FileInputStream(file));
                int n = scan.nextInt();
                for (int i = 0; i < n; i++) {
                    int v = scan.nextInt();
                    if (tree == null) tree = new Node23(v, v, null, null);
                    else tree = tree.put(v, v);
                    s.put(v, v);
                }
                scan.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


