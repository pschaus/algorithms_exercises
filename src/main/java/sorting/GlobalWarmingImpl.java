package sorting;

import java.util.Arrays;

/**
 * Author Pierre Schaus
 *
 * Assume the following 5x5 matrix that represent a grid surface:
 * int [][] tab = new int[][] {{1,3,3,1,3},
 *                             {4,2,2,4,5},
 *                             {4,4,1,4,2},
 *                             {1,4,2,3,6},
 *                             {1,1,1,6,3}};
 *
 * Each entry in the matrix represents an altitude point at the corresponding grid coordinate.
 * The goal is to implement a GlobalWarmingImpl class that extends the GlobalWarming abstract class described below.
 *
 * Given a global water level, all positions in the matrix with a value <= the water level are flooded and therefore unsafe.
 * So, assuming the water level is 3, all safe points are highlighted between parenthesis:
 *
 *   1 , 3 , 3 , 1 , 3
 *  (4), 2 , 2 ,(4),(5)
 *  (4),(4), 1 ,(4), 2
 *   1 ,(4), 2 , 3 ,(6)
 *   1 , 1 , 1 ,(6), 3}
 *
 * The method you need to implement is nbSafePoints
 * - calculating the number of safe points for a given water level
 *
 * Complete the code below. Pay attention to the expected time complexity of each method.
 * To meet this time complexity, you need to do some pre-computation in the constructor.
 * Feel free to create internal classes in GlobalWarmingImpl to make your implementation easier.
 * Feel free to use any method or data structure available in the Java language and API.
 */

abstract class GlobalWarming {


    protected final int[][] altitude;

    /**
     * @param altitude is a n x n matrix of int values representing altitudes (positive or negative)
     */
    public GlobalWarming(int[][] altitude) {
        this.altitude = altitude;
    }

    /**
     *
     * @param waterLevel
     * @return the number of entries in altitude matrix that would be above
     *         the specified waterLevel.
     *         Warning: this is not the waterLevel given in the constructor/
     */
    public abstract int nbSafePoints(int waterLevel);

}


public class GlobalWarmingImpl extends GlobalWarming {

    // BEGIN STRIP
    int[] flattenArray = new int[altitude.length * altitude.length];
    // END STRIP

    public GlobalWarmingImpl(int[][] altitude) {
        super(altitude);
        // TODO
        // expected pre-processing time in the constructror : O(n^2 log(n^2))
        // BEGIN STRIP
        int origin = 0;
        for (int i = 0; i < altitude.length; i++) {
            System.arraycopy(altitude[i], 0, flattenArray, origin, altitude.length);
            origin += altitude.length;
        }
        Arrays.sort(flattenArray);
        // END STRIP

    }

    /**
     * Returns the number of safe points given a water level
     *
     * @param waterLevel the level of water
     */
    public int nbSafePoints(int waterLevel) {
        // TODO
        // expected time complexity O(log(n^2))
        // STUDENT return -1;
        // BEGIN STRIP
        int limit = binarySearch(flattenArray, 0, flattenArray.length - 1, waterLevel);
        if (limit > -1) {
            return flattenArray.length - limit;
        }
        return 0;
        // END STRIP
    }

    // BEGIN STRIP
    private int binarySearch(int[] arr, int lo, int hi, int x) {
        int mid = lo + (hi - lo) / 2;
        if (lo <= hi) {

            if (arr[mid] == x && (mid + 1) < arr.length && arr[mid + 1] > arr[mid]) {
                return mid + 1;
            }
            if (arr[mid] > x) {
                return binarySearch(arr, lo, mid - 1, x);
            }
            return binarySearch(arr, mid + 1, hi, x);
        }
        if (mid < arr.length && arr[mid] > x) {
            return mid;
        }
        return -1;
    }
    // END STRIP


}
