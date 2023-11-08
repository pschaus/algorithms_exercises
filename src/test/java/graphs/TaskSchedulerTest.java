package graphs;

import org.javagrader.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;



@Grade
@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskSchedulerTest {

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    @Order(0)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm, debug first this small example", on = TestResultStatus.FAIL)
    public void testSingleTask() {
        TaskScheduler scheduler = new TaskScheduler();
        scheduler.addTask("A", Arrays.asList());
        assertTrue(scheduler.isValid(Arrays.asList("A")));
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    @Order(0)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm, debug first this small example", on = TestResultStatus.FAIL)
    public void testDependentTasksSmall() {
        TaskScheduler scheduler = new TaskScheduler();
        scheduler.addTask("A", Arrays.asList());
        scheduler.addTask("B", Arrays.asList("A"));
        scheduler.addTask("C", Arrays.asList("A"));
        scheduler.addTask("D", Arrays.asList("B", "C"));

        assertTrue(scheduler.isValid(Arrays.asList("A", "B", "C", "D")));
        assertTrue(scheduler.isValid(Arrays.asList("A", "C", "B", "D")));
        assertFalse(scheduler.isValid(Arrays.asList("C", "B", "D", "A")));
        assertFalse(scheduler.isValid(Arrays.asList("A", "B", "C", "A"))); // same node twice
        assertFalse(scheduler.isValid(Arrays.asList("A", "B", "C"))); // incomplete list
    }



    @Test
    @Grade(value = 3, cpuTimeout = 1000)
    @Order(0)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm, debug first this small example", on = TestResultStatus.FAIL)
    public void testDependentTasksMedium() {
        TaskScheduler scheduler = new TaskScheduler();
        scheduler.addTask("F", Arrays.asList("E"));
        scheduler.addTask("E", Arrays.asList("D","H"));
        scheduler.addTask("H", Arrays.asList("G"));
        scheduler.addTask("D", Arrays.asList("G","B","C"));
        scheduler.addTask("G", Arrays.asList("A"));
        scheduler.addTask("C", Arrays.asList());
        scheduler.addTask("B", Arrays.asList("A"));
        scheduler.addTask("I", Arrays.asList("A"));
        scheduler.addTask("A", Arrays.asList());

        assertTrue(scheduler.isValid(Arrays.asList("A", "C", "B", "G","I","D","H","E","F")));
        assertTrue(scheduler.isValid(Arrays.asList("A", "I", "C", "B", "G","H","D","E","F")));
        assertFalse(scheduler.isValid(Arrays.asList("A", "I", "C", "B", "E", "G","H","D","F")));
        assertFalse(scheduler.isValid(Arrays.asList("A", "C", "B","I","D","H","E","F"))); // missing node
        assertFalse(scheduler.isValid(Arrays.asList("A", "C", "B", "G","I","I","D","H","E","F"))); // duplicate
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    @Order(0)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm, debug first this small example", on = TestResultStatus.FAIL)
    public void testCyclicDependencies() {
        TaskScheduler scheduler = new TaskScheduler();
        scheduler.addTask("A", Arrays.asList("B"));
        scheduler.addTask("B", Arrays.asList("C"));
        scheduler.addTask("C", Arrays.asList("A"));
        assertFalse(scheduler.isValid(Arrays.asList("A", "B", "C")));
        assertFalse(scheduler.isValid(Arrays.asList("A", "C", "B")));
        assertFalse(scheduler.isValid(Arrays.asList("B", "A", "C")));
        assertFalse(scheduler.isValid(Arrays.asList("B", "C", "A")));
        assertFalse(scheduler.isValid(Arrays.asList("C", "A", "B")));
        assertFalse(scheduler.isValid(Arrays.asList("C", "B", "A")));
    }

    // BEGIN STRIP

    @Test
    @Grade(value = 5, cpuTimeout = 1000)
    @Order(1)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm, debug first this small example", on = TestResultStatus.FAIL)
    public void testRandom() {

        List<String> valid = new LinkedList<>();
        for (char i = 'A'; i < 'Z' ; i++) {
            valid.add(i+"");
        }

        Random r = new Random(0);

        // valid ones
        for (int k = 0; k < 100; k++) {
            TaskScheduler scheduler = new TaskScheduler();
            for (char i = 'A'; i < 'Z' ; i++) {
                List<String> preds = new LinkedList<>();
                for (char j = (char)(i-1) ; j >= 'A' ; j--) {
                    if (r.nextBoolean())
                        preds.add(j+"");
                }
                scheduler.addTask(i+"", preds);
            }
            assertTrue(scheduler.isValid(valid));
        }

        // invalid valid ones
        for (int k = 0; k < 100; k++) {
            TaskScheduler scheduler = new TaskScheduler();
            for (char i = 'A'; i < 'Z' ; i++) {
                List<String> preds = new LinkedList<>();
                for (char j = (char)(i-1); j >= 'A' ; j--) {
                    if (r.nextBoolean())
                        preds.add(j+"");
                }
                if (i == 'Q') {
                    preds.add("R");
                }
                scheduler.addTask(i+"", preds);
            }
            assertFalse(scheduler.isValid(valid));
        }

    }

    @Test
    @Grade(value = 5, cpuTimeout = 1000)
    @Order(2)
    @GradeFeedback(message = "Time Complexity", on = TestResultStatus.FAIL)
    public void testComplexity() {

        int n = 1000;

        List<String> schedule = new LinkedList<>();
        for (int i = 0; i <= n; i++) {
            schedule.add(i+"");
        }

        TaskScheduler scheduler = new TaskScheduler();
        for (int i = n; i > 0; i--) {
            List<String> preds = new LinkedList<>();
            for (int j = 0; j < i; j++) {
                preds.add(""+j);
            }
            scheduler.addTask(i+"", preds);
        }
        scheduler.addTask("0",Arrays.asList());
        assertTrue(scheduler.isValid(schedule));

        scheduler.addTask((n-2)+"0",Arrays.asList(""+(n-1),""+(n)));

        assertFalse(scheduler.isValid(schedule));
    }

    // END STRIP
}
