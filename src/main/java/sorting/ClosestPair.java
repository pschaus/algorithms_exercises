package sorting;

// BEGIN STRIP
import java.util.Arrays;
import java.lang.Math;
// END STRIP
/**
 * Let a be an array of integers.
 * In this exercise we are interested in finding
 * the two entries i and j such that a[i] + a[j] is the closest from a target x.
 * In other words, there are no entries k,l such that |x - (a[i] + a[j])| > |x - (a[k] + a[l])|.
 * Note that we can have i = j.
 * Your program must return the values a[i] and a[j].
 *
 * For example let a = [5, 10, 1, 75, 150, 151, 155, 18, 75, 50, 30]
 *
 * then for x = 20, your program must return the array [10, 10],
 *      for x = 153 you must return [1, 151] and
 *      for x = 170 you must return [18, 151]
 */
public class ClosestPair {

    /**
      * Finds the pair of integers which sums up to the closest value of x
      *
      * @param a the array in which the value are looked for
      * @param x the target value for the sum
      */
    public static int[] closestPair(int [] a, int x) {
        // STUDENT return null; // TODO
        // BEGIN STRIP
        Arrays.sort(a);
        int low = 0;
        int high = a.length - 1;
        int [] ret = new int[]{a[low], a[high]};
        while (low < high) {
            int current = a[low] + a[high];
            if (current < x) {
                low += 1;
            } else if (current > x) {
                high -= 1;
            } else {
                ret[0] = a[low];
                ret[1] = a[high];
                break;
            }
            if (Math.abs(x - (a[low] + a[high])) < Math.abs(x - (ret[0] + ret[1]))) {
                ret[0] = a[low];
                ret[1] = a[high];
            }
        }
        return ret;
        // END STRIP
    }
}
