package graphs;

import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class MineClimbingTest {

    public static class TestNotParameterized {

        @Test(timeout = 3000)
        @Grade(value = 5.0)
        public void simpleTest() {
            int[][] map = new int[][]{
                    {7, 2, 9, 6},
                    {8, 7, 6, 0},
                    {4, 6, 5, 8}
            };

            assertEquals(8, MineClimbing.best_distance(map, 0, 1, 2, 3));
        }

        @Test(timeout = 3000)
        @Grade(value = 4.0)
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


        @Test(timeout = 3000)
        @Grade(value = 4.0)
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

        @Test(timeout = 3000)
        @Grade(value = 4.0)
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

        @Test(timeout = 3000)
        @Grade(value = 4.0)
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

    }

    @RunWith(Parameterized.class)
    public static class TestParameterizedRandom {

        @Parameterized.Parameters(name = "{0}")
        public static Collection data() {
            LinkedList<Object[]> coll = new LinkedList<>();
            for (int i = 0; i < 100; i++) {
                String name = "data/graphs.MineClimbing/in_rand_" + i;
                coll.add(new Object[]{name, new Instance(name)});
            }
            return coll;
        }

        public TestParameterizedRandom(String name, Instance instance) {
            this.instance = instance;
        }

        final Instance instance;

        @Test
        @Grade(value = 1, cpuTimeout = 1000)
        public void randomTest() throws Exception {
            for (int i = 0; i < instance.nTests; i++) {
                List<Integer> coords = instance.tests.get(i);
                int student = MineClimbing.best_distance(instance.mine, coords.get(0), coords.get(1), coords.get(2), coords.get(3));
                int sol = instance.solutions.get(i);
                assertEquals(student, sol);
            }
        }
    }

    @RunWith(Parameterized.class)
    public static class TestParameterizedComp {

        @Parameterized.Parameters(name = "{0}")
        public static Collection data() {
            LinkedList<Object[]> coll = new LinkedList<>();
            for (int i = 0; i < 2; i++) {
                String name = "data/graphs.MineClimbing/in_comp_" + i;
                coll.add(new Object[]{name, new Instance(name)});
            }
            return coll;
        }

        public TestParameterizedComp(String name, Instance instance) {
            this.instance = instance;
        }

        final Instance instance;

        @Test
        @Grade(value = 1, cpuTimeout = 10000)
        public void complexityTest() throws Exception {
            for (int i = 0; i < instance.nTests; i++) {
                List<Integer> coords = instance.tests.get(i);
                int student = MineClimbing.best_distance(instance.mine, coords.get(0), coords.get(1), coords.get(2), coords.get(3));
                int sol = instance.solutions.get(i);
                assertEquals(student, sol);
            }
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
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
