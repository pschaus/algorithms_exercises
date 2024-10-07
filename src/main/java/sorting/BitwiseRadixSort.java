package sorting;

/**
 * In this task, you must implement the Radix sort algorithm to sort an array of integers. Similar to the classic radix sort this implementation
 * process one bit at time starting from the most significant bit.
 * The integers are assumed to be 32-bit values represented in two's complement.
 *
 * For example, the number -3 is represented in 4 bits as follows:
 * 1. Positive binary representation of 3: 0011
 * 2. Invert the bits: 1100
 * 3. Add 1: 1100 + 0001 = 1101
 *  Thus, -3 in 4-bit two's complement is represented as 1101.
 *
 * The time complexity of your implementation should be O(n * k), where n is the number of integers and k is the maximum number of bits (32).
 */
public class BitwiseRadixSort {

    // BEGIN STRIP
    private int R = 2;
    private int [] aux;
    // END STRIP

    /**
     *  Sort in place the given array using radix sort
     *
     * @param arr : The array to sort
     */
    public void sort(int[] arr) {
        // TODO
        // BEGIN STRIP
        int n = arr.length;
        aux = new int[n];
        radixSort(arr, 0, n - 1, 31, true);
        // END STRIP

    }

    // BEGIN STRIP
    private void radixSort(int[] arr, int lo, int hi, int bitIndex, boolean isFirstCall) {

        // Stop when the subarray is empty
        if (lo > hi) return;

        // Count array with extra space for cumulative counts and shifts
        int[] count = new int[R + 2];  // R is the radix

        // Frequency count of bits at the current bitIndex
        for (int i = lo; i <= hi; i++) {
            count[getBit(arr[i], bitIndex) + 2]++;
        }

        // Compute cumulative counts. r = 0 is unused
        for (int r = 0; r < R + 1; r++) {
            count[r + 1] += count[r];
        }

        // Sort elements based on the current digit
        for (int i = lo; i <= hi; i++) {
            aux[count[getBit(arr[i], bitIndex) + 1]++] = arr[i];
        }

        // Step 4: Rearrange the array based on whether it's the first call
        if (isFirstCall) {
            // On the first call, move elements with the most significant bit set to the beginning (for negative numbers)
            int[] adjustedCounts = new int[R + 2];
            adjustedCounts[R + 1] = count[R + 1];  // Set last count boundary (size of the array)
            int currentIndex = lo;

            // Rearrange the array: Move elements from aux back to arr
            for (int r = R; r > 0; r--) {
                int startIdx = count[r - 1];  // Start of the current group
                int endIdx = count[r];        // End of the current group

                // Copy elements from aux to arr in the correct order
                System.arraycopy(aux, startIdx, arr, currentIndex, endIdx - startIdx);
                currentIndex += (endIdx - startIdx);
                adjustedCounts[R - r + 1] = currentIndex;  // Update new counts
            }
            count = adjustedCounts;  // Replace count with the updated counts for recursion
        } else {
            // For subsequent calls, simply copy sorted elements back to the original array
            System.arraycopy(aux, 0, arr, lo, hi - lo + 1);
        }

        // Recursively sort each bucket
        for (int r = 0; r < R; r++) {
            radixSort(arr, lo + count[r], lo + count[r + 1] - 1, bitIndex - 1, false);
        }
    }



    private int getBit(int n, int k){
        if (k < 0 || k >= 32) {
            return -1;
        }
        return (n >> k) & 1;
    }
    // END STRIP

}
