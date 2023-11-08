package sorting;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

@Grade
@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PhotoTest {
    // BEGIN STRIP
    Random r = new Random(31235657);
    // END STRIP
    
    @Test
    @Grade(value = 1)
    @Order(0)
    public void testExampleTrue() {
        int [] teamA = new int[]{170, 155, 162, 184};
        int [] teamB = new int[]{160, 172, 190, 185};
        assertEquals(36, Photo.canTakePictures(teamA, teamB));
        assertEquals(36, Photo.canTakePictures(teamB, teamA));
    }
    
    @Test
    @Grade(value = 1)
    @Order(0)
    public void testExampleFalse() {
        int [] teamA = new int[]{144, 173, 158, 195};
        int [] teamB = new int[]{152, 169, 165, 189};
        assertEquals(-1, Photo.canTakePictures(teamA, teamB));
        assertEquals(-1, Photo.canTakePictures(teamB, teamA));
    }
    
    
    // BEGIN STRIP
    private int [] randomArray(int size) {
        int [] a = new int[size];
        for (int i = 0; i < size; i++) {
            a[i] = 100 + r.nextInt(100);
        }
        return a;
    }

    private int [] randomArrayEven(int size) {
        int [] a = new int[size];
        for (int i = 0; i < size; i++) {
            a[i] = 100 + (r.nextInt(50))*2;
        }
        return a;
    }

    private int [] randomArrayOdd(int size) {
        int [] a = new int[size];
        for (int i = 0; i < size; i++) {
            a[i] = 101 + (r.nextInt(50))*2;
        }
        return a;
    }

    @Test
    @Grade(value = 6, cpuTimeout = 1000)
    @Order(1)
    public void testSmall() {
        for (int i = 0; i < 1000; i++) {
            int [] teamA =randomArrayEven(100);
            int [] teamB =randomArrayOdd(100);
            assertEquals( expected(teamA, teamB), Photo.canTakePictures(teamA, teamB));
            assertEquals( expected(teamA, teamB), Photo.canTakePictures(teamB, teamA));
        }
    }
    
    @Test
    @Grade(value = 6, cpuTimeout = 1000)
    @Order(2)
    public void testComplexity() {
        for (int i = 0; i < 1000; i++) {
            int [] teamA =randomArrayEven(100);
            int [] teamB =randomArrayOdd(100);
            assertEquals( expected(teamA, teamB), Photo.canTakePictures(teamA, teamB));
            assertEquals( expected(teamA, teamB), Photo.canTakePictures(teamB, teamA));
        }

        for (int i = 0; i < 20; i++) {
            int [] teamA =randomArrayEven(10_000);
            int [] teamB =randomArrayOdd(10_000);
            assertEquals( expected(teamA, teamB), Photo.canTakePictures(teamA, teamB));
            assertEquals( expected(teamA, teamB), Photo.canTakePictures(teamB, teamA));
        }
    }
    
    @Test
    @Grade(value = 6)
    @Order(2)
    public void testSymmetry() {
        for (int i = 0; i < 100; i++) {
            int [] teamA = randomArrayOdd(100);
            int [] teamB = randomArrayEven(100);
            int maxA = 0;
            for (int h : teamA) {
                maxA = Math.max(maxA, h);
            }
            for (int j = 0; j < teamB.length; j++) {
                teamB[j] += maxA+1;
            }
            assertEquals( expected(teamA, teamB), Photo.canTakePictures(teamA, teamB));
            assertEquals( expected(teamA, teamB), Photo.canTakePictures(teamB, teamA));
        }
    }

    public static int expected(int [] teamA, int [] teamB) {
        Arrays.sort(teamA);
        Arrays.sort(teamB);
        boolean a_front = teamA[0] < teamB[0];
        int sum = 0;
        for (int i = 0; i < teamA.length; i++) {
            if (teamA[i] == teamB[i]) {
                return -1;
            }
            if (a_front && teamA[i] > teamB[i]) {
                return -1;
            } else if (!a_front && teamA[i] < teamB[i]) {
                return -1;
            }
            sum += Math.abs(teamA[i] - teamB[i]);
        }
        return sum;
    }
    // END STRIP
}
