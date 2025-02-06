package graphs;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.javagrader.TestResultStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Grade
@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegerTransformationTest {

    @Test
    @Grade(value = 1,  cpuTimeout = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @Order(1)
    @GradeFeedback(message = "Your algorithm seems buggy. Debug it locally", on= TestResultStatus.FAIL)
    public void testNoOperation() {
        assertEquals(0, IntegerTransformation.countSteps(54, 54));
    }

    @Test
    @Grade(value = 1,  cpuTimeout = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @Order(1)
    @GradeFeedback(message = "Your algorithm seems buggy. Debug it locally", on= TestResultStatus.FAIL)
    public void testNeighbors() {
        assertEquals(1, IntegerTransformation.countSteps(13, 18));
        assertEquals(1, IntegerTransformation.countSteps(13, 6));
        assertEquals(1, IntegerTransformation.countSteps(13, 26));
        assertEquals(1, IntegerTransformation.countSteps(13, 4));
    }

    @Test
    @Grade(value = 1,  cpuTimeout = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @Order(1)
    @GradeFeedback(message = "Your algorithm seems buggy. Debug it locally", on= TestResultStatus.FAIL)
    public void testAddition() {
        int start = 4765;
        int target = start;
        for (int i = 1; i < 11; i++) {
            target += 5;
            assertEquals(i, IntegerTransformation.countSteps(start, target));
        }
    }

    @Test
    @Grade(value = 1,  cpuTimeout = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @Order(1)
    @GradeFeedback(message = "Your algorithm seems buggy. Debug it locally", on= TestResultStatus.FAIL)
    public void testSubtraction() {
        int start = 7529;
        int target = start;
        for (int i = 1; i < 11; i++) {
            target -= 7;
            assertEquals(i, IntegerTransformation.countSteps(start, target));
        }
    }

    @Test
    @Grade(value = 1,  cpuTimeout = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @Order(1)
    @GradeFeedback(message = "Your algorithm seems buggy. Debug it locally", on= TestResultStatus.FAIL)
    public void testMultiplication() {
        int start = 19;
        int target = start;
        for (int i = 1; i < 11; i++) {
            target *= 2;
            assertEquals(i, IntegerTransformation.countSteps(start, target));
        }
    }

    @Test
    @Grade(value = 1,  cpuTimeout = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @Order(1)
    @GradeFeedback(message = "Your algorithm seems buggy. Debug it locally", on= TestResultStatus.FAIL)
    public void testDivision() {
        int start = 59149;
        int target = start;
        for (int i = 1; i < 11; i++) {
            target /= 3;
            assertEquals(i, IntegerTransformation.countSteps(start, target));
        }
    }

    @Test
    @Grade(value = 4,  cpuTimeout = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @Order(2)
    @GradeFeedback(message = "Your algorithm seems buggy. Debug it locally", on= TestResultStatus.FAIL)
    public void testTwoOps() {
        Random random = new Random(0);
        for (int iter = 0; iter < 100; iter++) {
            int start = 100+ random.nextInt(100);
            int [] next = {start + 5, start - 7, start * 2, start / 3};
            int target = next[random.nextInt(4)];
            next = new int[]{target + 5, target - 7, target * 2, target / 3};
            target = next[random.nextInt(4)];
            assertEquals(2, IntegerTransformation.countSteps(start, target));
        }
    }


    @Test
    @Grade(value = 1,  cpuTimeout = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @Order(2)
    @GradeFeedback(message = "Your algorithm seems buggy. Debug it locally", on= TestResultStatus.FAIL)
    public void testMultipleOperations() {
        for (int start = 7; start < 15; start ++) {
            for (int shift = 3; shift < 8; shift++) {
                int end = (start << shift) - 7; // (start * 2 ^ shift - 7)
                assertEquals(shift + 1, IntegerTransformation.countSteps(start, end));
                end = (start << shift) + 5; // (start * 2 ^ shift + 5)
                assertEquals(shift +1, IntegerTransformation.countSteps(start, end));
            }
        }
    }

}
