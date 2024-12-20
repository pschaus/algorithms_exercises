package sorting;

import org.javagrader.Grade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

// BEGIN STRIP
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Named.named;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
// END STRIP

@Grade
public class PancakeSortingTest {

    private static void flip(int[] array, int to) {
        for (int i = 0; i < (to + 1) / 2; i++) {
            int t = array[i];
            array[i] = array[to - i];
            array[to - i] = t;
        }
    }

    @Test
    @Grade(value=1)
    public void testSimple() {
        int[] input = new int[]{3, 1, 2, 5, 4};
        int[] flips = PancakeSorting.sort(Arrays.copyOf(input, input.length));
        assertTrue(flips.length <= 3 * input.length);
        for (int flip : flips) {
            assertTrue(flip >= 0 && flip < input.length);
            flip(input, flip);
        }
        for (int i = 0; i < input.length - 1; i++) {
            assertTrue(input[i] <= input[i + 1]);
        }
    }

    // BEGIN STRIP
    public static Instance[] instances;

    @BeforeAll
    public static void setUpClass() {
        Random rand = new Random(21096);
        instances = new Instance[10];
        for (int i = 0; i < 10; i++) {
            instances[i] = generateRandomInstance(rand);
        }
    }

    private static Instance generateRandomInstance(Random rand) {
        int[] array = new int[1_000];
        for (int i = 0; i < 1_000; i++) {
            array[i] = rand.nextInt(1_000) + 1;
        }
        return new Instance(array);
    }

    @Test
    @Grade(value=1)
    public void testSorted() {
        int[] input = new int[10];
        for (int i = 0; i < 10; i++) {
            input[i] = i+1;
        }
        int[] flips = PancakeSorting.sort(input);
        assertEquals(0, flips.length);
    }

    @Test
    @Grade(value=1)
    public void testEmpty() {
        int[] input = new int[0];
        int[] flips = PancakeSorting.sort(input);
        assertEquals(0, flips.length);
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
        int[] flips = PancakeSorting.sort(instance.array);
        assertTrue(flips.length <= 3 * instance.array.length);
        for (int i = 0; i < flips.length; i++) {
            assertTrue(flips[i] >= 0 && flips[i] < instance.array.length);
            flip(copy_input, flips[i]);
        }
        for (int i = 0; i < copy_input.length - 1; i++) {
            assertTrue(copy_input[i] <= copy_input[i + 1]);
        }
    }

    public static class Instance {
        public int[] array;

        public Instance(int[] array) {
            this.array = array;
        }
    }
    // END STRIP
}
