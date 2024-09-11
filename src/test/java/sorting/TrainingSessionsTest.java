package sorting;


import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.javagrader.TestResultStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Grade
public class TrainingSessionsTest {

    @Test
    @Grade(value = 1)
    @GradeFeedback(message="Your algorithm does not return the correct result on the given example. Debug it locally", on=TestResultStatus.FAIL)
    public void testSimple1() {
        TrainingSessions scheduler = new TrainingSessions();
        int[][] sessions = {
                {9, 12},//          ---
                {3, 6}, //    ---
                {1, 4}, //  ---
                {2, 5}, //   ---
                {7, 8}, //        -
        };
        // three intervals are overlapping at time 3: [1, 4), [2, 5), [3, 6)
        assertEquals(3, scheduler.minFacilitiesRequired(sessions));
    }

    @Test
    @Grade(value = 1)
    @GradeFeedback(message="Your algorithm does not return the correct result on the given example. Debug it locally", on=TestResultStatus.FAIL)
    public void testSimple2() {
        TrainingSessions scheduler = new TrainingSessions();
        int[][] sessions = {
                {1, 4},   //  ---
                {4, 10},   //    ------
                {1, 10}    // ---------
        };
        // two intervals are overlapping everywhere on {1,10} in this example
        assertEquals(2, scheduler.minFacilitiesRequired(sessions));
    }

    // BEGIN STRIP
    @Test
    @Grade(value = 1)
    @GradeFeedback(message="Your method does not return the correct results when there are identical sessions", on=TestResultStatus.FAIL)
    public void testIdenticals() {
        TrainingSessions scheduler = new TrainingSessions();
        int[][] sessions = {
                {1, 2},
                {2, 4},
                {2, 4},
                {4, 6},
                {3, 5},
                {9, 10},
                {8, 11},
        };
        assertEquals(3, scheduler.minFacilitiesRequired(sessions));
    }

    @Test
    @Grade(value = 1)
    @GradeFeedback(message="Your method does not return the correct result when there is only one session", on=TestResultStatus.FAIL)
    public void testOneSession() {
        TrainingSessions scheduler = new TrainingSessions();
        int [][] sessions = {{49978, 56677}};
        assertEquals(1, scheduler.minFacilitiesRequired(sessions));
    }

    @Test
    @Grade(value = 1)
    @GradeFeedback(message="Your algorithm does not return 0 when there are no training session.", on=TestResultStatus.FAIL)
    public void testEmptySchedule() {
        TrainingSessions scheduler = new TrainingSessions();
        int [][] sessions = {};
        assertEquals(0, scheduler.minFacilitiesRequired(sessions));
    }

    @Test
    @Grade(value = 1)
    @GradeFeedback(message="Your method does not return the correct results when there are many identical sessions", on=TestResultStatus.FAIL)
    public void testSameTimeSchedules() {
        TrainingSessions scheduler = new TrainingSessions();
        int nbSessions = 100;
        int start = 50;
        int end = nbSessions - 1;
        int[][] sessions = new int[nbSessions + 2][2];
        for (int i = 0; i < nbSessions; i++) {
            sessions[i + 2][0] = start;
            sessions[i + 2][1] = end;
        }
        sessions[0] = new int[]{12, 15};
        sessions[1] = new int[]{101, 122};
        assertEquals(nbSessions, scheduler.minFacilitiesRequired(sessions));


    }

    @Test
    @Grade(value = 1)
    @GradeFeedback(message="Your algorithm does not return the correct result when there are no overlapping sessions", on=TestResultStatus.FAIL)
    public void testNonOverlappingSchedules() {
        TrainingSessions scheduler = new TrainingSessions();
        int nbSessions = 100;
        int[][] sessions = new int[nbSessions][2];
        for (int i = 0; i < nbSessions; i++) {
            sessions[i][0] = i;
            sessions[i][1] = i+1;
        }
        assertEquals(1, scheduler.minFacilitiesRequired(sessions));
    }

    @Test
    @Grade(value = 1)
    @GradeFeedback(message="Your algorithm does not return the correct result when there is one big overlapping session", on=TestResultStatus.FAIL)
    public void testOneBigSession() {
        TrainingSessions scheduler = new TrainingSessions();
        int nbSessions = 100;
        int start = 0;
        int end = nbSessions - 1;
        int[][] sessions = new int[nbSessions][2];
        sessions[0][0] = start;
        sessions[0][1] = end;
        for (int i = 1; i < nbSessions; i++) {
            sessions[i][0] = i;
            sessions[i][1] = i+1;
        }
        assertEquals(2, scheduler.minFacilitiesRequired(sessions));
    }

    @Test
    @Grade(value = 4, cpuTimeout = 1)
    @GradeFeedback(message = "Your algorithm has bugs when tested on small random instances (pre-requisite to pass the complexity tests)", on=TestResultStatus.TIMEOUT)
    public void algoCorrectnessRandomSessions() {
        assertTrue(algoCorrect());
    }

    @Test
    @Grade(value = 4, cpuTimeout = 1)
    @GradeFeedback(message = "Your algorithm does not have the correct complexity or it has bugs", on=TestResultStatus.TIMEOUT)
    public void testComplexityNumberOfSessions() {
        assertTrue(algoCorrect());
        TrainingSessions scheduler = new TrainingSessions();
        for (int k = 10; k < 50; k++) {
            int nbSessions = 10000;
            int[][] sessions = new int[nbSessions][2];
            for (int i = 0; i < nbSessions; i++) {
                sessions[i][0] = i;
                sessions[i][1] = i+k;
            }
            assertEquals(k, scheduler.minFacilitiesRequired(sessions));
        }
    }

    @Test
    @Grade(value = 4, cpuTimeout = 100,threadMode = Timeout.ThreadMode.SEPARATE_THREAD, unit = TimeUnit.MILLISECONDS)
    @GradeFeedback(message = "Your algorithm does not have the correct complexity on very large integers or it has bugs", on=TestResultStatus.TIMEOUT)
    public void testComplexityLargeHorizon() {
        assertTrue(algoCorrect());
        TrainingSessions scheduler = new TrainingSessions();
        for (int k = 10; k < 50; k++) {
            int nbSessions = 50;
            int[][] sessions = new int[nbSessions+1][2];
            for (int i = 0; i < nbSessions; i++) {
                sessions[i][0] = i;
                sessions[i][1] = i+k;
            }
            sessions[nbSessions][0] = 0;
            sessions[nbSessions][1] = Integer.MAX_VALUE/2;
            assertEquals(k+1, scheduler.minFacilitiesRequired(sessions));
        }

    }




    public static boolean algoCorrect() {
        // generate random sessions
        TrainingSessions scheduler = new TrainingSessions();
        Random r = new Random(0);
        int nbSessions = 15;

        for (int k = 0; k < 1000; k++) {
            int[][] sessions = new int[nbSessions][2];
            for (int i = 0; i < nbSessions; i++) {
                int start = r.nextInt(15);
                int end = start + 1+ r.nextInt(10);
                sessions[i][0] = start;
                sessions[i][1] = end;
            }
            if (algoCorrect(sessions) !=  scheduler.minFacilitiesRequired(sessions)) {
                return false;
            }
        }
        return true;
    }

    public static int algoCorrect(int [][] sessions) {
        int maxEndTime = 0;
        for (int[] a : sessions) {
            if (a[1] > maxEndTime) {
                maxEndTime = a[1];
            }
        }
        int [] overlaps = new int[maxEndTime+1];
        for (int[] a : sessions) {
            overlaps[a[0]] += 1;
            overlaps[a[1]] -= 1;
        }
        int maximumNeededFacilities = 0;
        int cur = 0;
        for (int a : overlaps) {
            cur += a;
            if (cur > maximumNeededFacilities) {
                maximumNeededFacilities = cur;
            }
        }
        return maximumNeededFacilities;
    }

    // END STRIP
}
