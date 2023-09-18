package graphs;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
// BEGIN STRIP
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.stream.IntStream;
import java.util.stream.Stream;
// END STRIP
@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class MineClimbingTest {

    @Test
    @Grade(value = 5.0, cpuTimeout = 3000)
    @Order(1)
    public void simpleTest() {
        int[][] map = new int[][]{
                {7, 2, 9, 6},
                {8, 7, 6, 0},
                {4, 6, 5, 8}
        };

        assertEquals(8, MineClimbing.best_distance(map, 0, 1, 2, 3));
    }

    @Test
    @Grade(value = 4.0, cpuTimeout = 3000)
    @Order(1)
    public void almostFlatMapTest() {
        int[][] map = new int[][]{
                {0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };

        assertEquals(1, MineClimbing.best_distance(map, 1, 1, 4, 4));
    }


    @Test
    @Grade(value = 4.0, cpuTimeout = 3000)
    @Order(1)
    public void borderTest() {
        int[][] map = new int[][]{
                {9, 9, 1, 9, 2},
                {9, 1, 2, 9, 1},
                {1, 2, 9, 9, 2},
                {9, 9, 2, 1, 9},
                {2, 1, 9, 9, 1}
        };

        assertEquals(10, MineClimbing.best_distance(map, 0, 2, 4, 1));
    }

    @Test
    @Grade(value = 4.0, cpuTimeout = 3000)
    @Order(1)
    public void stepsTest() {
        int[][] map = new int[][]{
                {6, 5, 4, 3, 4, 5, 6},
                {5, 4, 3, 2, 3, 4, 5},
                {4, 3, 2, 1, 2, 3, 4},
                {3, 2, 1, 0, 1, 2, 3},
                {4, 3, 2, 1, 2, 3, 4},
                {5, 4, 3, 2, 3, 4, 5},
                {6, 5, 4, 3, 4, 5, 6},
        };

        assertEquals(6, MineClimbing.best_distance(map, 3, 3, 0, 0));
    }

    @Test
    @Grade(value = 4.0, cpuTimeout = 3000)
    @Order(1)
    public void increasingCostTest() {
        int[][] map = new int[][]{
                {46, -40, 30, -22, 31, -41, 47},
                {-39, 29, -14, 9, -15, 32, -42},
                {28, -13, 5, -2, 6, -16, 33},
                {-21, 12, -5, 1, -3, 10, -23},
                {27, -20, 8, -4, 7, -17, 34},
                {-38, 26, -19, 11, -18, 35, -43},
                {45, -37, 25, -24, 36, -44, 48},
        };

        assertEquals(221, MineClimbing.best_distance(map, 3, 3, 0, 0));
    }

    // BEGIN STRIP
    static Stream<Instance> dataProvider() {
        return IntStream.range(0, 100).mapToObj(i -> new Instance("data/graphs.MineClimbing/in_rand_" + i));
    }

    @ParameterizedTest
    @Grade(value = 1, cpuTimeout = 1000)
    @MethodSource("dataProvider")
    @Order(1)
    public void randomTest(Instance instance) throws Exception {
        for (int i = 0; i < instance.nTests; i++) {
            List<Integer> coords = instance.tests.get(i);
            int student = MineClimbing.best_distance(instance.mine, coords.get(0), coords.get(1), coords.get(2), coords.get(3));
            int sol = instance.solutions.get(i);
            assertEquals(student, sol);
        }
    }
    
    static Stream<Instance> dataProviderComplexity() {
        return Stream.of(
            new Instance("data/graphs.MineClimbing/in_comp_0"),
            new Instance("data/graphs.MineClimbing/in_comp_1")
        );
    }
    
    @ParameterizedTest
    @Grade(value = 1, cpuTimeout = 10000)
    @MethodSource("dataProviderComplexity")
    @Order(2)
    public void complexityTest(Instance instance) {
        for (int i = 0; i < instance.nTests; i++) {
            List<Integer> coords = instance.tests.get(i);
            MineClimbing.best_distance(instance.mine, coords.get(0), coords.get(1), coords.get(2), coords.get(3));
        }
    }

    static class Instance {

        int[][] mine;
        int nTests;
        List<List<Integer>> tests;
        List<Integer> solutions;

        public Instance(String file) {

            try {
                Scanner dis = new Scanner(new FileInputStream(file));
                int size = dis.nextInt();
                mine = new int[size][size];
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        mine[i][j] = dis.nextInt();
                    }
                }
                nTests = dis.nextInt();
                tests = new ArrayList<>();
                solutions = new ArrayList<>();
                for (int i = 0; i < nTests; i++) {
                    ArrayList<Integer> test = new ArrayList<>();
                    for (int j = 0; j < 4; j++) {
                        test.add(dis.nextInt());
                    }
                    tests.add(test);
                    solutions.add(dis.nextInt());
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    // END STRIP

}
