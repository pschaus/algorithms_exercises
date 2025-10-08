package sorting;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.javagrader.TestResultStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
// END STRIP

@Grade
@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FixQuickSortTest {

    // BEGIN STRIP
    Random random = new Random(546543);
    // END STRIP


    @Test
    @Grade(value = 1, cpuTimeout = 1000, unit = TimeUnit.MILLISECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @GradeFeedback(message = "Your implementation does not finish in the allocated time.", on = TestResultStatus.FAIL)
    @Order(3)
    public void testLarge() {
        int n = 100000;
        int[] array = IntStream.range(0, n).toArray();
        FixQuickSort.shuffleArray(array);
        FixQuickSort.sort(array);
        assertTrue(FixQuickSort.isSorted(array));
        for (int i = 0; i < n; i++) {
            assertEquals(i, array[i]);
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000, unit = TimeUnit.MILLISECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @GradeFeedback(message = "Your implementation does not finish in the allocated time.", on = TestResultStatus.FAIL)
    @Order(3)
    public void testLargeSameValue() {
        int n = 100000;
        int[] array = new int[n]; // all zeros
        FixQuickSort.shuffleArray(array);
        FixQuickSort.sort(array);
        assertTrue(FixQuickSort.isSorted(array));
    }





}
