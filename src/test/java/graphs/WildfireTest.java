package graphs;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;



@Grade
@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WildfireTest {

    static final int EMPTY = 0;
    static final int TREE = 1;
    static final int BURNING = 2;

    
    @Test
    @Grade(value = 1)
    @Order(0)
    public void testSimple() {
        Wildfire wildfire = new Wildfire();

        int[][] forest1 = {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 2, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1}
        };
        // The first forest has a burning tree right in the middle, and all trees are reachable,
        // so the fire should reach all trees in 4 time units (corners of the forest).
        assertEquals(4, wildfire.burnForest(forest1));

        int[][] forest2 = {
                {1, 1, 1, 0, 1},
                {1, 2, 1, 0, 1},
                {1, 1, 1, 0, 1},
                {1, 1, 1, 1, 1}
        };
        // The second forest has all trees are reachable, but it will require 8 time units for the fire to reach the further tree in the top right corner.
        assertEquals(8, wildfire.burnForest(forest2));

        int[][] forest3 = {
                {1, 0, 1, 1, 1},
                {0, 0, 0, 0, 1},
                {1, 1, 1, 0, 1},
                {1, 2, 1, 1, 1}
        };
        // The third forest has a burning tree, but there are trees that the fire can't reach (the top-right) because of the empty spaces, so it should return -1.
        assertEquals(-1, wildfire.burnForest(forest3));

        int[][] forest4 = {
                {1, 1, 0, 2, 1},
                {1, 0, 0, 0, 1},
                {2, 1, 0, 0, 1},
                {1, 2, 0, 1, 2}
        };
        // The fourth forest has four burning tree, two in each part.
        // all the trees are reachable within three units of time.
        assertEquals(3, wildfire.burnForest(forest4));

    }


    // BEGIN STRIP

    int[][] forest1 = new int[][]{
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1},
            {1, 0, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1},
            {2, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 2, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1},
            {1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 2},
            {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1},
            {1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 2, 0},
            {0, 1, 0, 2, 1, 0, 1, 1, 0, 1, 0, 1, 2, 0, 1, 1, 0, 1, 0, 1},
            {0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 2, 0, 1, 0, 1, 0, 1, 1, 1, 0},
            {1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 2, 1, 1},
            {1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 2},
            {0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0},
            {1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1},
            {0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1},
            {1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1},
            {1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 2, 1},
            {0, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 1, 1, 0, 1, 1, 2, 1, 1, 0, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1}
    };

    int [][] forest2 = new int[][] {
            {0, 1, 1, 1, 1, 1, 1, 0, 1, 2, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 2, 1, 1, 1, 0},
            {1, 1, 1, 1, 1, 2, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1},
            {1, 0, 1, 0, 1, 2, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 2, 0, 1, 1},
            {1, 1, 1, 1, 1, 0, 0, 0, 2, 1, 1, 0, 0, 2, 1, 1, 1, 1, 0, 1},
            {1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 1, 2, 0, 1, 1, 1, 1, 1, 1, 0},
            {1, 0, 0, 1, 2, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1},
            {0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 2},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
            {1, 1, 1, 1, 0, 0, 0, 1, 2, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2},
            {0, 2, 1, 2, 1, 1, 1, 1, 1, 0, 0, 2, 0, 0, 1, 1, 0, 1, 1, 1},
            {1, 1, 1, 0, 2, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 0, 1, 1, 0, 1, 0},
            {1, 1, 1, 0, 1, 1, 1, 2, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 1},
            {1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 2, 2, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0},
            {1, 1, 0, 1, 1, 0, 1, 2, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    int [][] forest3 = new int[][] {
            {1, 0, 2, 1, 0, 2, 1, 0, 1, 0, 2, 1, 1, 2, 2, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 2, 1, 0, 2, 1, 1, 1, 1, 0},
            {2, 2, 1, 1, 1, 1, 2, 1, 2, 2, 1, 2, 1, 1, 1, 1, 0, 1, 1, 1},
            {1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2, 2, 1, 0, 1, 1, 1, 2, 1},
            {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1},
            {1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1},
            {2, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1},
            {1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 2},
            {1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1},
            {2, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1},
            {2, 2, 1, 2, 1, 1, 1, 2, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 1},
            {1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 2, 1, 2, 2, 1, 1, 0, 2, 1},
            {1, 1, 2, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1},
            {2, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 2, 1, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 1, 2, 2, 0, 0, 1}
    };

    int [][] forest4 = new int[][] {
            {1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 2},
            {1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 2, 1, 2},
            {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 1, 1, 1, 2, 1, 1, 2, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 2, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
            {2, 1, 1, 1, 1, 1, 0, 1, 2, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
            {0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 0, 2, 1},
            {0, 1, 1, 1, 2, 0, 1, 1, 1, 0, 1, 1, 2, 2, 1, 1, 1, 1, 2, 1},
            {1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 2, 1, 1},
            {2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 1, 0, 0, 2, 0, 1, 2, 1, 0, 1, 1, 1, 2, 1, 2, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 2, 1, 1, 0, 2, 0, 1, 1, 1, 1, 1},
            {2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 2, 1, 1, 1},
            {1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 2, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 2, 1, 1, 1},
            {2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 1},
            {1, 2, 1, 1, 1, 2, 0, 0, 0, 1, 1, 1, 1, 2, 1, 1, 2, 1, 0, 1},
            {1, 1, 1, 0, 1, 1, 1, 2, 1, 0, 1, 1, 0, 1, 2, 1, 2, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 2, 0}
    };

    public int [][] forest() {
        int n = 1000; // Large size of forest
        int[][] forest = new int[n][n];

        // Initialize all trees as non-burning
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                forest[i][j] = TREE;
            }
        }
        return forest;
    }

    @Test
    @Grade(value = 10)
    @Order(1)
    public void testComplexity() {
        Wildfire wildfire = new Wildfire();

        assertEquals(10,wildfire.burnForest(forest1));
        assertEquals(-1,wildfire.burnForest(forest2));
        assertEquals(6,wildfire.burnForest(forest3));
        assertEquals(5,wildfire.burnForest(forest4));

        int n = 1000; // Large size of forest
        int[][] forest = forest();

        // Set a tree on fire
        forest[n / 2][n / 2] = BURNING;

        // Measure the time taken to burn the forest
        long start = System.currentTimeMillis();
        int res = wildfire.burnForest(forest);
        long end = System.currentTimeMillis();
        assertEquals(1000, res);
        assertTrue((end-start) < 1000);

        // ------------

        forest = forest();
        forest[0][1] = 0;
        forest[1][0] = 0;
        forest[n / 2][n / 2] = 2;
        start = System.currentTimeMillis();
        res = wildfire.burnForest(forest);
        end = System.currentTimeMillis();
        assertEquals(-1, res);
        assertTrue((end-start) < 1000);

    }

    @Test
    @Grade(value = 6)
    public void testCorrectness() {

        Wildfire wildfire = new Wildfire();
        assertEquals(10,wildfire.burnForest(forest1));
        assertEquals(-1,wildfire.burnForest(forest2));
        assertEquals(6,wildfire.burnForest(forest3));
        assertEquals(5,wildfire.burnForest(forest4));


    }


    @Test
    @Grade(value = 3)
    @Order(0)
    public void testCorrectnessLineAndColumnMatrices() {
        Wildfire wildfire = new Wildfire();

        int[][] forest1 = {
                {1, 1, 2, 1, 1},
        };
        assertEquals(2, wildfire.burnForest(forest1));

        int[][] forest2 = {
                {1},
                {2},
                {1},
                {1},
                {1}
        };
        assertEquals(3, wildfire.burnForest(forest2));

        int[][] forest3 = {
                {1, 0, 2, 1, 1}
        };
        assertEquals(-1, wildfire.burnForest(forest3));

        int[][] forest4 = {
                {2},
                {1},
                {1},
                {2},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1}
        };
        assertEquals(11, wildfire.burnForest(forest4));

    }

    public static void generateInstance() {


        Wildfire wildfire = new Wildfire();

        Random r = new Random(11);
        int n = 20;
        int[][] forest = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (r.nextInt(100) < 75) {
                    forest[i][j] = TREE;
                } else {
                    if (r.nextInt(100) < 50)
                        forest[i][j] = BURNING;
                    else
                        forest[i][j] = EMPTY;
                }
            }
        }

        String arrayAsString = Arrays.deepToString(forest)
                .replace("], ", "],\n")
                .replace("[[", "{\n{")
                .replace("]]", "}\n}")
                .replace("[", "{")
                .replace("]", "}");

        System.out.println(arrayAsString);
        System.out.println(wildfire.burnForest(forest));

    }

    // END STRIP
    

}
