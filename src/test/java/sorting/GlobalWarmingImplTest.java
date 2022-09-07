package sorting;
import com.github.guillaumederval.javagrading.Grade; /*to be added for grading and test*/
import org.junit.Test;

import static org.junit.Assert.*;

/*normal import*/
import java.util.Random;



/**
 * Created by johnaoga on 22/10/2018.
 */
//@RunWith(Parameterized.class) //For grading
public class GlobalWarmingImplTest {


    final int [] seeds = new int[]{0,7,13};

    Random rand = new Random(seeds[new java.util.Random().nextInt(3)]);

    ///tests Methods Correctness
    @Test
    @Grade(value=10)
    public void testSafePointExam() {
        String message = "safe points returned (should be 14):"+new GlobalWarmingImpl(getExamMatrix()).nbSafePoints(2);
        assertEquals(message, new GlobalWarmingImpl(getExamMatrix()).nbSafePoints(2), 14);
    }

    @Test
    @Grade(value=10)
    public void testSimpleAll() {
        int [][] matrix = getSimpleMatrix();
        GlobalWarming warming = new GlobalWarmingImpl(matrix);

        if (warming.nbSafePoints(-1) != 100) {
            String message = "error in nbSafePoints";
            assertTrue(message,false);
        }

        if (warming.nbSafePoints(0) != 24) {
            String message = "error in nbSafePoints";
            assertTrue(message,false);
        }

        if (warming.nbSafePoints(1) != 0) {
            String message = "error in nbSafePoints";
            assertTrue(message,false);
        }
    }

    @Test
    @Grade(value=30)
    public void testCorrectnessNbSafePoints() {
        int[][] matrix = getExamMatrix();
        GlobalWarming g1 = new GlobalWarmingImpl(matrix);
        int[] true_value = {25, 18, 14, 9, 3};
        for (int l = 0; l < 5; l += 1) {
            String msg = "at " +l+" number should be " + g1.nbSafePoints(l) + " but it's " + true_value[l];
            assertEquals(msg, true_value[l], g1.nbSafePoints(l));
        }
        matrix = getExamMatrix2();
        GlobalWarming g2 = new GlobalWarmingImpl(matrix);
        int[] true_value2 = {98, 97, 97, 97, 95, 95, 94, 94, 94, 93, 93, 92, 90, 90, 88, 87, 87, 87, 87, 87, 85, 85, 85,
                85, 85, 85, 85, 85, 85, 84, 83, 82, 82, 81, 81, 81, 81, 80, 80, 78, 78, 78, 78, 78, 78, 77, 77, 76, 76,
                76, 76, 75, 74, 74, 74, 72, 71, 69, 68, 68, 68, 67, 67, 67, 67, 67, 67, 67, 66, 66, 65, 65, 65, 65, 64,
                63, 60, 60, 60, 58, 57, 57, 57, 57, 56, 56, 56, 53, 53, 53, 52, 52, 51, 51, 49, 49, 46, 45, 43, 43, 42,
                42, 40, 40, 39, 38, 37, 37, 35, 35, 35, 34, 32, 32, 32, 32, 32, 32, 29, 29, 29, 28, 27, 24, 23, 21, 19,
                19, 19, 18, 17, 16, 16, 15, 14, 12, 10, 10, 9, 9, 9, 8, 7, 7, 5, 2, 2, 1, 1, 0};
        for (int l = 0; l < 150; l += 1) {
            String msg = "at " +l+" number should be " + g2.nbSafePoints(l) + " but it's " + true_value2[l];
            assertEquals(msg, true_value2[l], g2.nbSafePoints(l));
        }

        matrix = getExamMatrix3();
        GlobalWarming g3 = new GlobalWarmingImpl(matrix);
        int[] true_value3 = {100, 99, 97, 96, 96, 95, 95, 95, 95, 95, 94, 94, 92, 92, 92, 92, 91, 91, 89, 87, 86, 85,
                84, 82, 82, 81, 80, 79, 79, 79, 78, 78, 78, 78, 77, 77, 77, 77, 76, 76, 76, 74, 73, 71, 70, 70, 70, 67,
                67, 67, 67, 66, 66, 64, 64, 63, 63, 61, 59, 59, 58, 58, 57, 57, 57, 56, 56, 55, 54, 53, 52, 52, 51, 50,
                49, 47, 47, 45, 45, 44, 43, 43, 42, 42, 42, 41, 41, 41, 39, 39, 39, 38, 38, 37, 37, 34, 33, 32, 32, 32,
                32, 31, 30, 30, 29, 27, 27, 26, 24, 24, 23, 22, 20, 20, 19, 19, 17, 15, 15, 15, 15, 15, 15, 13, 12, 12,
                12, 12, 12, 12, 11, 11, 11, 11, 11, 10, 9, 5, 4, 3, 3, 2, 2, 2, 2, 2, 2, 2, 0, 0};
        for(int l = 0; l < 150; l += 1){
            String msg = "at " +l+" number should be " + g3.nbSafePoints(l) + " but it's " + true_value3[l];
            assertEquals(msg, true_value3[l], g3.nbSafePoints(l));
        }

        matrix = getExamMatrix4();
        GlobalWarming g4 = new GlobalWarmingImpl(matrix);
        int[] true_value4 = {99, 99, 98, 98, 98, 97, 97, 96, 94, 93, 92, 91, 91, 89, 89, 88, 87, 87, 87, 87, 87, 86, 86,
                85, 85, 85, 85, 85, 82, 81, 81, 80, 80, 80, 77, 76, 75, 75, 75, 74, 73, 70, 70, 69, 69, 68, 68, 68, 67,
                67, 67, 66, 66, 64, 64, 64, 64, 64, 64, 64, 64, 64, 61, 61, 61, 60, 59, 56, 56, 56, 56, 54, 53, 52, 52,
                51, 51, 50, 48, 47, 45, 43, 42, 41, 40, 40, 39, 39, 39, 39, 38, 35, 35, 35, 34, 33, 33, 32, 32, 31, 31,
                30, 29, 28, 27, 27, 26, 25, 24, 24, 23, 23, 23, 21, 21, 19, 19, 18, 18, 17, 17, 16, 16, 16, 16, 13, 13,
                12, 11, 11, 10, 10, 10, 10, 9, 9, 7, 7, 6, 5, 4, 3, 2, 2, 2, 2, 2, 1, 0, 0};
        for(int l = 0; l < 150; l += 1){
            String msg = "at " +l+" number should be " + g4.nbSafePoints(l) + " but it's " + true_value4[l];
            assertEquals(msg, true_value4[l], g4.nbSafePoints(l));
        }

        matrix = getExamMatrix5();
        GlobalWarming g5 = new GlobalWarmingImpl(matrix);
        int[] true_value5 = {100, 100, 98, 98, 98, 98, 98, 98, 98, 97, 97, 96, 95, 95, 94, 94, 92, 92, 92, 92, 92, 92,
                91, 90, 88, 87, 86, 85, 84, 84, 84, 84, 82, 82, 80, 78, 77, 77, 76, 74, 74, 74, 72, 71, 68, 67, 65, 65,
                64, 64, 62, 62, 61, 61, 60, 59, 59, 59, 58, 58, 58, 58, 58, 58, 57, 57, 56, 55, 55, 52, 51, 51, 50, 48,
                48, 48, 47, 46, 45, 45, 45, 45, 45, 45, 45, 43, 42, 42, 42, 42, 41, 41, 41, 37, 36, 35, 35, 35, 33, 33,
                31, 30, 29, 28, 28, 26, 26, 24, 24, 24, 22, 22, 20, 19, 19, 18, 18, 17, 17, 16, 15, 14, 13, 13, 11, 11,
                9, 9, 9, 8, 7, 7, 6, 6, 6, 6, 6, 5, 3, 3, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0};
        for(int l = 0; l < 150; l += 1){
            String msg = "at " +l+" number should be " + g5.nbSafePoints(l) + " but it's " + true_value5[l];
            assertEquals(msg, true_value5[l], g5.nbSafePoints(l));
        }

        matrix = getExamMatrix6();
        GlobalWarming g6 = new GlobalWarmingImpl(matrix);
        int[] true_value6 = {100, 99, 99, 98, 97, 96, 95, 95, 94, 93, 93, 92, 90, 90, 89, 89, 89, 89, 87, 87, 86, 84,
                84, 84, 83, 82, 82, 80, 79, 77, 75, 75, 75, 75, 75, 75, 72, 72, 72, 72, 70, 69, 69, 69, 69, 69, 69, 69,
                67, 66, 65, 65, 65, 65, 64, 64, 63, 61, 61, 61, 59, 59, 58, 57, 57, 56, 56, 55, 54, 54, 53, 52, 52, 51,
                50, 50, 47, 47, 47, 46, 46, 46, 46, 46, 44, 43, 42, 42, 41, 41, 41, 41, 41, 39, 39, 39, 38, 37, 37, 36,
                36, 35, 34, 33, 33, 33, 32, 32, 32, 32, 32, 29, 25, 25, 25, 23, 22, 20, 19, 18, 16, 16, 15, 14, 14, 14,
                13, 12, 12, 12, 12, 12, 11, 11, 11, 11, 9, 8, 6, 6, 6, 5, 5, 4, 4, 3, 3, 3, 1, 0};
        for(int l = 0; l < 150; l += 1){
            String msg = "at " +l+" number should be " + g6.nbSafePoints(l) + " but it's " + true_value6[l];
            assertEquals(msg, true_value6[l], g6.nbSafePoints(l));
        }
    }


    ///extra methods
    public int [][] getSimpleMatrix() {
        int[][] matrix = new int[][]{
                {0, 0, 0, 0, 0, 1, 1, 1, 0, 0},
                {0, 1, 0, 0, 0, 1, 0, 1, 1, 1},
                {0, 0, 0, 0, 0, 1, 0, 0, 1, 0},
                {0, 1, 0, 0, 0, 1, 0, 1, 1, 0},
                {0, 1, 0, 0, 0, 1, 1, 1, 1, 1},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        return matrix;
    }


    public int [][] getExamMatrix() {
        int [][] tab = new int[][] {{1,3,3,1,3},
                {4,2,2,4,5},
                {4,4,1,4,2},
                {1,4,2,3,6},
                {1,1,1,6,3}};
        return tab;
    }

    public int [][] getExamMatrix2() {
        int [][] tab = new int[][] {{136, 14, 135, 94, 130, 4, 118, 149, 0, 84},
                {0, 12, 108, 61, 145, 112, 111, 138, 57, 129},
                {142, 74, 33, 1, 68, 104, 39, 100, 126, 11},
                {123, 123, 79, 141, 135, 92, 118, 6, 96, 134},
                {125, 80, 123, 147, 133, 145, 112, 39, 76, 102},
                {12, 45, 144, 20, 94, 52, 108, 15, 126, 57},
                {124, 75, 96, 79, 122, 105, 20, 55, 29, 47},
                {4, 102, 87, 70, 14, 96, 30, 121, 31, 98},
                {76, 144, 87, 136, 97, 56, 58, 51, 125, 98},
                {106, 118, 37, 87, 76, 131, 90, 9, 145, 55}};
        return tab;
    }

    public int [][] getExamMatrix3() {
        int [][] tab = new int[][] {{60, 148, 79, 47, 65, 53, 41, 111, 69, 104},
                {77, 77, 23, 112, 95, 44, 34, 75, 41, 20},
                {43, 138, 74, 47, 2, 10, 3, 82, 42, 23},
                {95, 95, 137, 137, 2, 62, 75, 53, 88, 135},
                {110, 25, 5, 80, 148, 141, 124, 136, 112, 12},
                {19, 27, 116, 96, 57, 16, 58, 137, 93, 97},
                {137, 88, 55, 108, 108, 117, 1, 68, 58, 18},
                {38, 22, 107, 101, 26, 30, 47, 12, 85, 70},
                {67, 123, 117, 19, 105, 102, 139, 43, 51, 91},
                {123, 130, 18, 114, 116, 73, 57, 72, 105, 21}};
        return tab;
    }

    public int [][] getExamMatrix4() {
        int [][] tab = new int[][] {{86, 15, 119, 53, 117, 67, 81, 147, 138, 13},
                {130, 125, 148, 121, 97, 7, 36, 91, 90, 51},
                {21, 134, 80, 115, 136, 104, 34, 62, 142, 0},
                {78, 53, 99, 95, 91, 10, 125, 62, 94, 9},
                {67, 11, 71, 23, 127, 28, 66, 71, 35, 41},
                {136, 43, 113, 107, 8, 78, 16, 8, 140, 75},
                {5, 45, 108, 29, 65, 125, 83, 40, 41, 106},
                {48, 82, 128, 141, 67, 79, 39, 91, 102, 31},
                {77, 81, 28, 101, 34, 113, 139, 62, 110, 73},
                {72, 115, 13, 41, 103, 2, 80, 34, 84, 28}};
        return tab;
    }

    public int [][] getExamMatrix5() {
        int [][] tab = new int[][] {{115, 32, 69, 98, 138, 90, 110, 16, 23, 112},
                {54, 44, 12, 78, 39, 122, 9, 124, 25, 140},
                {43, 110, 67, 100, 32, 72, 70, 93, 64, 2},
                {50, 76, 102, 45, 105, 113, 93, 48, 77, 120},
                {46, 2, 35, 26, 69, 93, 22, 107, 50, 35},
                {94, 66, 44, 98, 119, 38, 28, 85, 46, 126},
                {147, 24, 124, 39, 93, 103, 73, 42, 36, 24},
                {55, 44, 95, 146, 121, 107, 34, 137, 27, 34},
                {129, 132, 100, 58, 105, 52, 11, 130, 86, 138},
                {69, 101, 126, 42, 85, 14, 117, 16, 73, 112}};
        return tab;
    }

    public int [][] getExamMatrix6() {
        int [][] tab = new int[][] {{115, 12, 8, 41, 101, 136, 12, 103, 3, 18},
                {120, 88, 149, 18, 11, 60, 29, 57, 36, 65},
                {67, 25, 145, 136, 86, 74, 21, 85, 60, 1},
                {40, 24, 27, 20, 63, 27, 117, 76, 102, 84},
                {118, 79, 73, 68, 71, 141, 30, 36, 54, 143},
                {122, 106, 48, 117, 57, 84, 49, 112, 62, 127},
                {93, 138, 112, 9, 70, 76, 30, 93, 4, 119},
                {48, 97, 132, 126, 148, 99, 56, 76, 14, 116},
                {120, 123, 96, 28, 21, 5, 111, 112, 6, 148},
                {111, 115, 36, 112, 29, 137, 138, 111, 40, 50}};
        return tab;
    }

    public int [][] getRandomMatrix(int n,int bound) {
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = rand.nextInt(bound);
            }
        }
        return matrix;
    }


    ///Complexities
    @Test(timeout=300)
    @Grade(value=20)
    public void timeComplexityConstructorCorrect() {
        final int [][] matrix = getRandomMatrix(1000,2000000);
        final GlobalWarming g = new GlobalWarmingImpl(matrix);

    }

    final GlobalWarming gwi = new GlobalWarmingImpl(getRandomMatrix(1000,2000000));

    @Test(timeout=50)
    @Grade(value=30)
    public void timeComplexityNbSafePoints() {
        int max = 0;
        for (int i = 0; i < 1000; i++) {
            gwi.nbSafePoints(i*1000);
        }

    }

}
