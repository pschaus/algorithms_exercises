package sorting;

import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Grade
public class AggregateTest {




    @Test
    @Grade(value = 1, cpuTimeout = 1)
    @GradeFeedback(message = "Simple Aggregate Test")
    public void testAggregateSimple() {
        int[][] arrayTest = {
                {1, 4, 6},
                {2, 1, 4},
                {2, 2, 4},
                {1, 4, 7},
                {3, 3, 5},
                {1, 5, 7}
        };

        int[][] outputRef0 = {
                {1, 4, 7},
                {2, 1, 4},
                {3, 3, 5}
        };

        int[][] outputRef1 = {
                {2, 1, 4},
                {2, 2, 4},
                {3, 3, 5},
                {1, 4, 6},
                {1, 5, 7}
        };

        int[][] outputRef2 = {
                {2, 1, 4},
                {3, 3, 5},
                {1, 4, 6},
                {1, 4, 7}
        };

        int[][] outputTest0 = Aggregate.aggregate(arrayTest, 0);
        assertArrayEquals(outputRef0, outputTest0);

        int[][] outputTest1 = Aggregate.aggregate(arrayTest, 1);
        assertArrayEquals(outputRef1, outputTest1);

        int[][] outputTest2 = Aggregate.aggregate(arrayTest, 2);
        assertArrayEquals(outputRef2, outputTest2);
    }


    @Test
    @Grade(value = 2, cpuTimeout = 1)
    @GradeFeedback(message = "Simple Mode Test")
    public void testModeSimple() {
        int[][] array = {
                {1, 5, 3},
                {4, 5, 2},
                {6, 2, 8},
                {9, 2, 2},
                {1, 5, 3}
        };
        assertEquals(2, Aggregate.mode(array, 1, 4, 1));
        assertEquals(5, Aggregate.mode(array, 0, 4, 1));
    }






    // BEGIN STRIP

    @Test
    @Grade(value = 2, cpuTimeout = 1)
    @GradeFeedback(message = "Simple Mode Test without tie break")
    public void testModeNoTieBreak() {
        int[][] array = {
                {1, 5, 3},
                {4, 5, 2},
                {6, 3, 8},
                {9, 3, 2},
                {1, 6, 3},
                {1, 5, 3}
        };
        assertEquals(3, Aggregate.mode(array, 1, 4, 1));
        assertEquals(5, Aggregate.mode(array, 0, 5, 1));
    }


    @Test
    @Grade(value = 1, cpuTimeout = 1)
    @GradeFeedback(message = "Simple test with clusters already presented sorted and all the lines the same in clusters")
    public void testAggregateNoTieAlreadySorted() {
        int[][] input = {
                {1, 2, 3},
                {1, 2, 3},
                {4, 5, 6},
                {4, 5, 6},
                {7, 8, 9},
                {7, 8, 9}
        };

        int[][] outputRef = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] outputTest = Aggregate.aggregate(input, 0);
        assertArrayEquals(outputRef, outputTest);

    }



    @Test
    @Grade(value = 1, cpuTimeout = 1)
    @GradeFeedback(message = "The groups must sorted in ascending order")
    public void testGroupOrder() {
        int[][] input = {
                {5},
                {1},
                {2},
                {6},
                {3},
                {4}
        };

        int[][] outputRef = {
                {1},
                {2},
                {3},
                {4},
                {5},
                {6}
        };

        int[][] outputTest = Aggregate.aggregate(input, 0);
        assertArrayEquals(outputRef, outputTest);
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    @GradeFeedback(message = "Check that the correct column is used to aggregate, and that the columns order is unchanged")
    public void testColumnOrder() {
        int[][] input = {
                {1, 1, 2},
                {1, 1, 2},
                {1, 2, 2},
                {1, 3, 2}
        };

        int[][] outputRef = {
                {1, 1, 2},
                {1, 2, 2},
                {1, 3, 2}
        };

        int[][] outputTest = Aggregate.aggregate(input, 1);
        assertArrayEquals(outputRef, outputTest);
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    @GradeFeedback(message = "The resulting set of groups are not the good ones")
    public void testMissingAggregate() {
        int[][] input = {
                {2},
                {5},
                {2},
                {5},
                {8}
        };

        int[][] outputRef = {
                {2},
                {5},
                {8}
        };

        int[][] outputTest = Aggregate.aggregate(input, 0);
        assertArrayEquals(outputTest, outputRef);
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    @GradeFeedback(message = "For each group and column, the value with the highest number of occurrence must be chosen")
    public void testModeInAggregate() {
        int[][] input = {
                {1, 2},
                {1, 3},
                {1, 2},
                {1, 2},
                {1, 4}
        };

        int[][] outputRef = {
                {1, 2}
        };

        int[][] outputTest = Aggregate.aggregate(input, 0);
        assertArrayEquals(outputRef, outputTest);
    }

    @Test
    @Grade(value = 1, cpuTimeout = 1)
    @GradeFeedback(message = "When two values have the same number of occurrence in a group," +
            " the smallest one should be chosen")
    public void testSmallest() {
        int[][] input = {
                {1, 8},
                {1, 8},
                {1, 6},
                {1, 6}
        };

        int[][] outputRef = {
                {1, 6}
        };

        int[][] outputTest = Aggregate.aggregate(input, 0);
        assertArrayEquals(outputTest, outputRef);
    }


    @Test
    @Grade(value = 3, cpuTimeout = 1)
    @GradeFeedback(message = "Time Complexity Mode")
    public void testComplexityMode() {
        int n = 100000;
        int m = 50;

        int [][] input = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i % 2 == 0)
                    input[i][j] = 42;
                else
                    input[i][j] = i+j;
            }
        }
        for (int j = 0; j < m; j++) {
            assertEquals(42,Aggregate.mode(input, 0, n-1, j));
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                assertEquals(input[i][j],Aggregate.mode(input, i, i, j));
            }
        }
    }


    @Test
    @Grade(value = 1, cpuTimeout = 1)
    @GradeFeedback(message = "Time Complexity Aggregate")
    public void testComplexityAggregate() {
        int n = 100000;
        int m = 50;

        int[][] input = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                input[i][j] = j;
            }
        }

        int[][] ref = new int[1][m];
        for (int j = 0; j < m; j++)
            ref[0][j] = j;

        assertArrayEquals(ref, Aggregate.aggregate(input, 0));

        input = new int[n+1][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                input[i][j] = j;
            }
        }
        for (int j = 0; j < m; j++)
            input[m][j] = 2;

        ref = new int[2][m];
        for (int j = 0; j < m; j++) {
            ref[0][j] = j;
            ref[1][j] = 2;
        }

        assertArrayEquals(ref, Aggregate.aggregate(input, 0));
    }

    // END STRIP
}
