package graphs;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
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

// BEGIN STRIP
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Stream;
// END STRIP

@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class GlobalWarmingTest {

    @Test
    @Grade(value=1, cpuTimeout = 1000)
    @GradeFeedback(message="Sorry, something is wrong with your algorithm. Debug first on this small example")
    @Order(1)
    public void  testExample() {
        int [][] altitude = new int[][] {{1, 3, 3, 1, 3},
                                         {4, 2, 2, 4, 5},
                                         {4, 4, 1, 4, 2},
                                         {1, 4, 2, 3, 6},
                                         {1, 1, 1, 6, 3}};
        int waterLevel = 3;

        GlobalWarming globalWarming = new GlobalWarming(altitude, waterLevel);
        assertEquals(4, globalWarming.nbIslands());
        GlobalWarming.Point p1 = new GlobalWarming.Point(1, 0);
        GlobalWarming.Point p2 = new GlobalWarming.Point(3, 1);
        GlobalWarming.Point p3 = new GlobalWarming.Point(1, 3);
        GlobalWarming.Point p4 = new GlobalWarming.Point(0, 0);
        assertTrue(globalWarming.onSameIsland(p1, p2));
        assertFalse(globalWarming.onSameIsland(p1, p3));
        assertFalse(globalWarming.onSameIsland(p1, p4));
    }

    // BEGIN STRIP
    
    static Stream<Instance> dataProviderCorrectness() {
        return Stream.of(new File("data/graphs.GlobalWarming").listFiles())
            .filter(file -> !file.isDirectory() && !file.getName().startsWith("in_500"))
            .map(file -> new Instance(file.getPath()));
    }

    @ParameterizedTest
    @Grade(value = 1)
    @GradeFeedback(message = "Sorry, something is wrong with your algorithm. Hint: debug on the small example")
    @MethodSource("dataProviderCorrectness")
    @Order(2)
    public void testCorrectness(Instance instance) {
        assertEquals(instance.nbIsland, instance.globalWarming.nbIslands());
        GlobalWarming.Point [] queries = instance.queries;
        assertEquals(instance.queriesAnswers[0], instance.globalWarming.onSameIsland(queries[0], queries[0]));
        for (int i = 0; i < queries.length - 1; i++) {
            assertEquals(instance.queriesAnswers[i+1], instance.globalWarming.onSameIsland(queries[i], queries[i+1]));
        }
    }

    static Stream<Instance> dataProviderComplexity() {
        return Stream.of(new File("data/graphs.GlobalWarming").listFiles())
            .filter(file -> !file.isDirectory() && !file.getName().startsWith("in_500"))
            .map(file -> new Instance(file.getPath()));
    }

    @ParameterizedTest
    @Grade(value=1, cpuTimeout=5)
    @GradeFeedback(message="Your method nbIsland is too slow !")
    @MethodSource("dataProviderComplexity")
    @Order(3)
    public void testNbIsland(Instance instance) {
        instance.globalWarming.nbIslands();
    }

    @ParameterizedTest
    @Grade(value=1, cpuTimeout=5)
    @GradeFeedback(message="Your method onSameIsland is too slow !")
    @MethodSource("dataProviderComplexity")
    public void testOnSameIsland(Instance instance) {
        instance.globalWarming.onSameIsland(instance.queries[0], instance.queries[1]);
    }

    @ParameterizedTest
    @Grade(value=1, cpuTimeout=500)
    @GradeFeedback(message="Your constructor is too slow !")
    @MethodSource("dataProviderComplexity")
    @Order(4)
    public void testConstructor(Instance instance) {
        new GlobalWarming(instance.altitude, instance.waterLevel);
    }

    static class Instance {
        GlobalWarming.Point [] queries;
        boolean [] queriesAnswers;
        GlobalWarming globalWarming;
        int [][] altitude;
        int waterLevel;
        int nbIsland;

        public Instance(String file) {
            try {
                Scanner scan = new Scanner(new FileInputStream(file));
                int n = scan.nextInt();
                this.altitude = new int[n][n];
                for (int row = 0; row < n; row++) {
                    for (int col = 0; col < n; col++) {
                        this.altitude[row][col] = scan.nextInt();
                    }
                }
                this.waterLevel = scan.nextInt();
                this.globalWarming = new GlobalWarming(this.altitude, this.waterLevel);
                this.queries = new GlobalWarming.Point[scan.nextInt()];
                for (int i = 0; i < this.queries.length; i++) {
                    this.queries[i] = new GlobalWarming.Point(scan.nextInt(), scan.nextInt());
                }
                this.nbIsland = scan.nextInt();
                this.queriesAnswers = new boolean[this.queries.length];
                for (int i = 0; i < this.queriesAnswers.length; i++) {
                    this.queriesAnswers[i] = scan.nextBoolean();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // END STRIP
}
