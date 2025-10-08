package sorting;


import java.util.Arrays;
import java.util.Random;

/**
 * This is a quick sort implementation for integers.
 * It works correctly, but unfortunately, has you can see in the test,
 * it has a very bad worst-case performance when all values are the same.
 * Since all the values are the same, the pre-shuffle does not help in this case.
 * What is wrong with the implementation? Can you fix it?
 * What is the complexity of the current implementation when all values are the same?
 * What is the complexity of your fixed implementation in this case?
 */
public class FixQuickSort {

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(int[] a) {
        // shuffle array to avoid worst-case
        shuffleArray(a);
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    }

    // quicksort the subarray from a[lo] to a[hi]
    private static void sort(int[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
        assert isSorted(a, lo, hi);
    }

    // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
    // and return the index j.
    private static int partition(int[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        int v = a[lo];
        while (true) {
            // find item on lo to swap
            // STUDENT while (a[++i] <= v) {
            // BEGIN STRIP
            while (a[++i] < v) {
            // END STRIP
                if (i == hi) break;
            }
            // find item on hi to swap
            // STUDENT while (v <= a[--j]) {
            // BEGIN STRIP
            while (v < a[--j]) {
             // END STRIP
                if (j == lo) break;      // redundant since a[lo] acts as sentinel
            }
            // check if pointers cross
            if (i >= j) break;
            exch(a, i, j);
        }
        // put partitioning item v at a[j]
        exch(a, lo, j);
        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }



    // exchange a[i] and a[j]
    private static void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // Fisher–Yates shuffle
    public static void shuffleArray(int[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }


    /***************************************************************************
     *  Check if array is sorted - useful for debugging and testing, do not modify
     ***************************************************************************/

    public static boolean isSorted(int[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (a[i-1] > a[i]) return false;
        return true;
    }

}