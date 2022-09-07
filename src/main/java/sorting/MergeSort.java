package sorting;

/**
 * Author Pierre Schaus
 *
 * Consider the Merge Sort (top-down) algorithm.
 */

public class MergeSort {
    /**
     * Pre-conditions: a[lo..mid] and a[mid+1..hi] are sorted
     * Post-conditions: a[lo..hi] is sorted
     */
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (aux[j].compareTo(aux[i]) < 0) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    // Mergesort a[lo..hi] using auxiliary array aux[lo..hi]
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        // TODO
        // BEGIN STRIP
        if(hi<=lo) return;
        int mid =lo+ (hi-lo)/2;
        sort(a, aux, lo, mid);
        sort(a, aux,  mid + 1, hi);
        merge(a, aux,  lo, mid, hi);
        // END STRIP
    }

    /**
     * Rearranges the array in ascending order, using the natural order
     */
    public static void sort(Comparable[] a) {
        // TODO
        // BEGIN STRIP
        Comparable [] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length-1);
        // END STRIP
    }
}

