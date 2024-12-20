package searching;

import org.javagrader.Grade;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

// BEGIN STRIP
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Named.named;
// END STRIP

@Grade
public class CandiesBagTest {

    @Test
    @Grade(value = 1)
    public void testSimple1() {
        int maxLength = CandiesBag.findMaximumSize(new int[]{1, -1, 5, -2, 3}, 3);
        assertEquals(4, maxLength);
    }

    @Test
    @Grade(value = 1)
    public void testSimple2() {
        int maxLength = CandiesBag.findMaximumSize(new int[]{-2, -1, 2, 1}, 1);
        assertEquals(2, maxLength);
    }

    // BEGIN STRIP
    @Test
    @Grade(value = 1)
    public void testAll() {
        int[] input = new int[10];
        for (int i = 0; i < 10; i++) {
            input[i] = 1;
        }
        int maxLength = CandiesBag.findMaximumSize(input, 10);
        assertEquals(10, maxLength);
    }

    @Test
    @Grade(value = 1)
    public void testNoPossibilities() {
        int[] input = new int[10];
        for (int i = 0; i < 10; i++) {
            input[i] = i;
        }
        int maxLength = CandiesBag.findMaximumSize(input, -1);
        assertEquals(0, maxLength);
    }

    @Test
    @Grade(value = 1)
    public void testEmpty() {
        int[] input = new int[]{};
        int maxLength = CandiesBag.findMaximumSize(input, 10);
        assertEquals(0, maxLength);
    }

    public static Instance[] instances;

    @BeforeAll
    public static void setUpClass() {
        Random rand = new Random(53708);
        instances = new Instance[10];
        for (int i = 0; i < 10; i++) {
            instances[i] = generateRandomInstance(rand);
        }
    }

    private static Instance generateRandomInstance(Random rand) {
        int[] array = new int[200_000];
        for (int i = 0; i < 200_000; i++) {
            array[i] = -1_000 + rand.nextInt(2_000);
        }
        int start = rand.nextInt(array.length / 2);
        int count = rand.nextInt(array.length - start);
        int k = 0;
        for (int i = 0; i < count; i++) {
            k += array[start + i];
        }
        return new Instance(
                array, k,
                SolutionCandiesBag.findMaximumSize(Arrays.copyOf(array, array.length), k)
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
        long nano_start = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();

        int maxLength = CandiesBag.findMaximumSize(instance.array, instance.k);

        long nano_end = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
        assertTrue(nano_end - nano_start < 1_000_000_000, "Execution time is more than 1 CPU second");

        assertEquals(instance.solution, maxLength);
    }

    private static class Instance {
        public int[] array;
        public int k;
        public int solution;

        public Instance(int[] array, int k, int solution) {
            this.array = array;
            this.k = k;
            this.solution = solution;
        }
    }

    private static class SolutionCandiesBag {
        public static int findMaximumSize(int[] array, int k) {
            HashMap<Integer, Integer> prefixSums = new HashMap<>();
            prefixSums.put(0, 0);
            int runningSum = 0;
            int maxLength = 0;
            for (int i = 0; i < array.length; i++) {
                runningSum += array[i];
                if (prefixSums.containsKey(runningSum - k)) {
                    maxLength = Math.max(maxLength, i - prefixSums.get(runningSum - k) + 1);
                }
                if (!prefixSums.containsKey(runningSum)) {
                    prefixSums.put(runningSum, i + 1);
                }
            }
            return maxLength;
        }
    }
    // END STRIP
}
