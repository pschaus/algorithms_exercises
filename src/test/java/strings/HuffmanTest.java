package strings;

import utils.HuffmanNode;

import java.util.LinkedList;
import java.util.List;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradingRunnerWithParametersFactory;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
public class HuffmanTest {

    /**
     * Collect all the leaves of a trie rooted at root.
     *
     * @param root The root of the trie
     */
    private static List<HuffmanNode> collectLeafNodes(HuffmanNode root) {
        LinkedList<HuffmanNode> list = new LinkedList<>();
        collectLeafNodes(list, root);
        return list;
    }

    /**
     * Recursively add the leaves of the trie in the `l` list
     *
     * @param l the list in which the leaves are inserted
     * @param root the root of the current sub-trie
     */
    private static void collectLeafNodes(List<HuffmanNode> l, HuffmanNode root) {
        if (root.isLeaf()) {
            l.add(root);
        } else {
            collectLeafNodes(l, root.getLeft());
            collectLeafNodes(l, root.getRight());
        }
    }

    /**
     * Checks if the weight of the nodes are consistent. This means that the weight of the leaves
     * must be their frequencies and the weight of an internal node is the sum of the weights of its
     * children.
     *
     * @param root the root of the current sub-trie
     * @param freq the frequencies from which the trie is constructed
     */
    private static boolean checkSums(HuffmanNode root, int [] freq) {
        if (root.isLeaf()) {
            return root.getFrequency() == freq[root.getChar()];
        }
        return checkSums(root.getLeft(), freq) && checkSums(root.getRight(), freq) && root.getFrequency() == root.getLeft().getFrequency() + root.getRight().getFrequency();
    }

    /**
     * Returns the Weigthed External Path Length of the trie rooted at root
     *
     * @param root the root of the trie
     */
    private static int weightedExternalPathLength(HuffmanNode root) {
        return weightedExternalPathLength(root, 0);
    }

    /**
     * Recursively computes the Weighted External Path Length of the sub-trie rooted at
     * `root` at depth `depth`
     *
     * @param root the root of the current sub-trie
     * @param depth the depth of root in the trie
     */
    private static int weightedExternalPathLength(HuffmanNode root, int depth) {
        if (root.isLeaf()) {
            return root.getFrequency()*depth;
        }
        return weightedExternalPathLength(root.getLeft(), depth+1) + weightedExternalPathLength(root.getRight(), depth+1);
    }

    public static class TestNotParameterized {

        @Test
        @Grade(value=1, cpuTimeout = 1000)
        @GradeFeedback(message="", onFail=true)
        public void testExample() {
            // In the example:      a  g  z  t  m
            int [] freq = new int[]{4, 5, 3, 1, 2};
            HuffmanNode trie = Huffman.buildTrie(freq);
            List<HuffmanNode> leaves = collectLeafNodes(trie);
            assertEquals(freq.length, leaves.size());
            assertTrue(checkSums(trie, freq));
            // In the example, the following encoding in bit is done
            // a 00
            // g 01
            // z 10
            // t 110
            // m 111
            int weightedExternalPath = 2*freq[0] + 2* freq[1] + 2*freq[2] + 3*freq[3] + 3*freq[4];
            assertEquals(weightedExternalPath, weightedExternalPathLength(trie));
        }
    }

       // BEGIN STRIP
    @RunWith(Parameterized.class)
    @Parameterized.UseParametersRunnerFactory(GradingRunnerWithParametersFactory.class)
    public static class TestParameterized {
        
        @Parameterized.Parameters(name="{0}")
        public static Collection data() {
            return Stream.of(new File("data/strings.Huffman").listFiles())
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
            List<HuffmanNode> leaves = collectLeafNodes(instance.trie);
            assertEquals(instance.frequencies.length, leaves.size());
            assertTrue(checkSums(instance.trie, instance.frequencies));
            assertEquals(instance.wepl, weightedExternalPathLength(instance.trie));
        }
    }

    static class Instance {
        int [] frequencies;
        HuffmanNode trie;
        int wepl;

        public Instance(String file) {
            try {
                Scanner scan = new Scanner(new FileInputStream(file));
                int n = scan.nextInt();
                this.frequencies = new int[n];
                for (int i = 0; i < n; i++) {
                    this.frequencies[i] = scan.nextInt();
                }
                this.wepl = scan.nextInt();
                this.trie = Huffman.buildTrie(this.frequencies);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // END STRIP

}
