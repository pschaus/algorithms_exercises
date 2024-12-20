package searching;

import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Named.named;
// END STRIP

@Grade
public class SleighRankingTest {

    @Test
    @Grade(value = 1)
    public void testSimple() {
        int[] rankings = SleighRanking.computeRankings(new int[]{5, 4, 6, 4, 2, 3, 7});
        assertArrayEquals(new int[]{0, 0, 2, 0, 0, 1, 6}, rankings);
    }

    // BEGIN STRIP
    @Test
    @Grade(value = 1)
    public void testSorted() {
        int[] input = new int[10];
        for (int i = 0; i < 10; i++) {
            input[i] = i;
        }
        int[] copy_input = Arrays.copyOf(input, 10);
        int[] rankings = SleighRanking.computeRankings(input);
        assertArrayEquals(copy_input, rankings);
    }

    @Test
    @Grade(value = 1)
    public void testInverseSorted() {
        int[] input = new int[10];
        for (int i = 0; i < 10; i++) {
            input[i] = 10 - i;
        }
        int[] copy_input = Arrays.copyOf(input, 10);
        int[] rankings = SleighRanking.computeRankings(input);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, rankings);
    }

    @Test
    @Grade(value = 1)
    public void testEmpty() {
        int[] rankings = SleighRanking.computeRankings(new int[]{});
        assertArrayEquals(new int[]{}, rankings);
    }

    public static Instance[] instances;

    @BeforeAll
    public static void setUpClass() {
        Random rand = new Random(53708);
        instances = new Instance[10];
        for (int i = 0; i < 9; i++) {
            instances[i] = generateRandomInstance(100_000, rand);
        }
        instances[9] = generateRandomInstance(100, rand);
    }

    private static Instance generateRandomInstance(int max, Random rand) {
        int[] array = new int[300_000];
        for (int i = 0; i < 300_000; i++) {
            array[i] = rand.nextInt(max);
        }
        return new Instance(
                array,
                SolutionSleighRanking.computeRankings(Arrays.copyOf(array, array.length))
        );
    }

    public static Stream<Arguments> instanceProvider() {
        return IntStream.range(0, instances.length).mapToObj(i -> Arguments.of(named("Instance " + i, instances[i])));
    }

    @ParameterizedTest
    @Grade(value = 1, cpuTimeout = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm. Hint: debug on a small example or try to reduce the complexity of your algorithm")
    @MethodSource("instanceProvider")
    public void test(Instance instance) {
        int[] copy_input = Arrays.copyOf(instance.array, instance.array.length);

        long nano_start = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();

        int[] rankings = SleighRanking.computeRankings(copy_input);

        long nano_end = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
        assertTrue(nano_end - nano_start < 1_000_000_000, "Execution time is more than 1 CPU second");

        assertArrayEquals(instance.solution, rankings);
    }

    public static class Instance {
        public int[] array;
        public int[] solution;

        public Instance(int[] array, int[] solution) {
            this.array = array;
            this.solution = solution;
        }
    }

    private static class SolutionSleighRanking {

        private static class BST {
            private Node root = null;

            private static class Node {
                private final int key;

                private Node left = null;
                private Node right = null;

                private int N_LEFT = 0;
                private int N = 0;

                public Node(int key) {
                    this.key = key;
                    this.N = 1;
                }
            }

            public BST() {}

            public void put(int key) {
                root = put(root, key);
            }

            private Node put(Node x, int key) {
                if (x == null) {
                    return new Node(key);
                }
                if (key < x.key) {
                    x.left = put(x.left, key);
                    x.N_LEFT++;
                } else if (key > x.key) {
                    x.right = put(x.right, key);
                } else {
                    x.N++;
                }
                return x;
            }

            public int getRank(int key) {
                return getRank(root, key);
            }

            private int getRank(Node x, int key) {
                if (x == null) {
                    return 0;
                }
                if (key < x.key) {
                    return getRank(x.left, key);
                } else if (key > x.key) {
                    return getRank(x.right, key) + x.N_LEFT + x.N;
                } else {
                    return x.N_LEFT;
                }
            }
        }

        public static int[] computeRankings(int[] array) {
            int[] answer = new int[array.length];
            BST bst = new BST();
            for (int i = 0; i < array.length; i++) {
                bst.put(array[i]);
                answer[i] = bst.getRank(array[i]);
            }
            return answer;
        }
    }
    // END STRIP
}
