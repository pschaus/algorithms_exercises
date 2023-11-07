package searching;

import static org.junit.jupiter.api.Assertions.*;


import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.javagrader.TestResultStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class SkylineTest {

    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    @GradeFeedback(message = "Sorry, something is wrong with your get algorithm, debug first this small example")
    @Order(0)
    public void debug1() {
        int[][] buildings = {{1, 11, 5}, {2, 6, 7}, {3, 13, 9}, {12, 7, 16}, {14, 3, 25}, {19, 18, 22}, {23, 13, 29}, {24, 4, 28}};

        int [][] res = {{1,11},{3,13},{9,0},{12,7},{16,3},{19,18},{22,3},{23,13},{29,0}};
        List<int[]> expected = Arrays.asList(res);

        List<int[]> computed = Skyline.getSkyline(buildings);

        // test the expected list is equal to the computed list, element by element
        for (int i = 0; i < expected.size(); i++) {
            assertArrayEquals(expected.get(i), computed.get(i));
        }
        assertEquals(expected.size(), computed.size());
    }


    @Test
    @Grade(value = 1, cpuTimeout = 1000)
    @GradeFeedback(message = "Sorry, something is wrong with your get algorithm, debug first this small example")
    @Order(0)
    public void debug2() {
        int[][] buildings = {{0,4,7},{0,8,6},{6,6,12},{6,4,16},{10,5,20},{22,2,26}};

        int [][] res = {{0,8},{6,6},{12,5},{20,0},{22,2},{26,0}};
        List<int[]> expected = Arrays.asList(res);

        List<int[]> computed = Skyline.getSkyline(buildings);

        // test the expected list is equal to the computed list, element by element
        for (int i = 0; i < expected.size(); i++) {
            assertArrayEquals(expected.get(i), computed.get(i));
        }
        assertEquals(expected.size(), computed.size());
    }

    // BEGIN STRIP
    @Test
    @Grade(value = 1, cpuTimeout = 2000)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm result, debug on small examples",on= TestResultStatus.FAIL)
    @GradeFeedback(message = "Too slow, time complexity should be O(n.log(n)), should scale on 100K buildings", on= TestResultStatus.TIMEOUT)
    @Order(1)
    public void timeComplexity() {
        Random rand = new Random(0);
        int max = 0;
        int[][] buildings = new int[100000][3];
        for (int i = 0; i < buildings.length; i++) {
            int h = rand.nextInt(100000);
            max = Math.max(max, h);
            buildings[i][0] = 0;
            buildings[i][1] = h;
            buildings[i][2] = 1;
        }

        int [][] res = {{0,max},{1,0}};
        List<int[]> expected = Arrays.asList(res);
        List<int[]> computed = Skyline.getSkyline(buildings);

        // test the expected list is equal to the computed list, element by element
        for (int i = 0; i < expected.size(); i++) {
            assertArrayEquals(expected.get(i), computed.get(i));
        }
        assertEquals(expected.size(), computed.size());
    }
    // END STRIP




}



