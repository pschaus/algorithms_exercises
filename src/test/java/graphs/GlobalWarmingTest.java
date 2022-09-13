package graphs;

import utils.Point;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradingRunnerWithParametersFactory;
import org.junit.runners.Parameterized;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

// BEGIN STRIP
import java.util.Collection;
import java.util.LinkedList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.stream.Collectors;
// END STRIP

@RunWith(Enclosed.class)
public class GlobalWarmingTest {

    public static class TestNotParameterized {
        @Test
        @Grade(value=1, cpuTimeout = 1000)
        @GradeFeedback(message="Sorry, something is wrong with your algorithm. Debug first on this small example", onFail=true)
        public void  testExample() {
            int [][] altitude = new int[][] {{1, 3, 3, 1, 3},
                                             {4, 2, 2, 4, 5},
                                             {4, 4, 1, 4, 2},
                                             {1, 4, 2, 3, 6},
                                             {1, 1, 1, 6, 3}};
            int waterLevel = 3;

            GlobalWarming globalWarming = new GlobalWarming(altitude, waterLevel);
            assertEquals(4, globalWarming.nbIslands());
            Point p1 = new Point(1, 0);
            Point p2 = new Point(3, 1);
            Point p3 = new Point(1, 3);
            Point p4 = new Point(0, 0);
            assertTrue(globalWarming.onSameIsland(p1, p2));
            assertFalse(globalWarming.onSameIsland(p1, p3));
            assertFalse(globalWarming.onSameIsland(p1, p4));
        }
    }

    // BEGIN STRIP
    @RunWith(Parameterized.class)
    @Parameterized.UseParametersRunnerFactory(GradingRunnerWithParametersFactory.class)
    public static class TestParameterized {

        @Parameterized.Parameters(name="{0}")
        public static Collection data() {
            return Stream.of(new File("data/graphs.GlobalWarming").listFiles())
                .filter(file -> !file.isDirectory())
                .map(file -> new Object [] { file.getName(), new Instance(file.getPath()) })
                .collect(Collectors.toList());
        }

        final Instance instance;

        public TestParameterized(String name, Instance instance) {
            this.instance = instance;
        }

        @Test
        @Grade(value = 1, cpuTimeout = 1000)
        @GradeFeedback(message = "Sorry, something is wrong with your algorithm. Hint: debug on the small example", onFail=true)
        @GradeFeedback(message = "Check the complexity of your algorithm", onTimeout=true)
        public void test()  throws Exception {
            assertEquals(instance.nbIsland, instance.globalWarming.nbIslands());
            Point [] queries = instance.queries;
            assertEquals(instance.queriesAnswers[0], instance.globalWarming.onSameIsland(queries[0], queries[0]));
            for (int i = 0; i < queries.length - 1; i++) {
                assertEquals(instance.queriesAnswers[i+1], instance.globalWarming.onSameIsland(queries[i], queries[i+1]));
            }
        }
    }

    @RunWith(Parameterized.class)
    @Parameterized.UseParametersRunnerFactory(GradingRunnerWithParametersFactory.class)
    public static class TestComplexity {

        @Parameterized.Parameters(name="{0}")
        public static Collection data() {
            return Stream.of(new File("data/graphs.GlobalWarming").listFiles())
                .filter(file -> !file.isDirectory() && file.getName().startsWith("in_500"))
                .map(file -> new Object [] { file.getName(), new Instance(file.getPath()) })
                .collect(Collectors.toList());
        }

        final Instance instance;

        public TestComplexity(String name, Instance instance) {
            this.instance = instance;
        }

        @Test(timeout=5)
        @Grade(value=1)
        @GradeFeedback(message="Your method nbIsland is too slow !")
        public void testNbIsland() {
            instance.globalWarming.nbIslands();
        }

        @Test(timeout=5)
        @Grade(value=1)
        @GradeFeedback(message="Your method onSameIsland is too slow !")
        public void testOnSameIsland() {
            instance.globalWarming.onSameIsland(instance.queries[0], instance.queries[1]);
        }

        @Test(timeout=0)
        @Grade(value=1, cpuTimeout=500)
        @GradeFeedback(message="Your constructor is too slow !")
        public void testConstructor() {
            GlobalWarming g = new GlobalWarming(instance.altitude, instance.waterLevel);
        }
    }

    static class Instance {
        Point [] queries;
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
                this.queries = new Point[scan.nextInt()];
                for (int i = 0; i < this.queries.length; i++) {
                    this.queries[i] = new Point(scan.nextInt(), scan.nextInt());
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
