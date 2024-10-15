package sorting;

/**
 * Radix sort implementation.
 * Complete the code to pass the test on positive numbers.
 * You can assume that all numbers are non-negative as a first step.
 *
 * As a second step, adapt the code to handle negative numbers.
 * You can also add tests for negative numbers.
 * Remind that negative numbers use the two's complement representation.
 * For example, the number -3 is represented in 4 bits as follows:
 *    1. Positive binary representation of 3: 0011
 *    2. Invert the bits: 1100
 *    3. Add 1: 1100 + 0001 = 1101
 * Thus, -3 in 4-bit two's complement is represented as 1101.
 *
 * What is the time complexity of your algorithm?
 * How does it compare in practice to the other sorting algorithms?
 *
 * @author Pierre Schaus and Harold Kiossous
 */
public class RadixSort {

    // Radix Sort method (we assume all numbers are non-negative)
    public static void sort(int[] A) {
        int maxBits = getMaxBits(A);
        int [] aux = new int[A.length];
        for (int i = 0; i < maxBits; i++) {
            stableSortOnBit(A, i, aux);
        }
    }


    /**
     * Stable Sort the array A based on the i-th bit
     * In order to have the best time complexity, your algorithm should run in O(n)
     * where n is the size of the array A.
     * @param A the array to sort based on the i-th bit
     * @param bitPosition
     * @param aux is an auxiliary array of the same size as A that you can use in your algorithm
     */
    private static void stableSortOnBit(int[] A, int bitPosition, int[] aux) {
        // TODO
        // BEGIN STRIP
        // Counting Sort based on the i-th bit
        int n = A.length;
        int[] count = new int[2]; // Since bits can be 0 or 1

        // Store count of occurrences in count[]
        for (int i = 0; i < n; i++) {
            int bit = getBit(A[i], bitPosition);
            count[bit]++;
        }

        // Change count[i] so that count[i] is now the next position to set for the  bit i
        // this is already ok for count[0] but not for count[1]
        count[1] += count[0];
        // Build the output array in aux
        // Iterate from n-1 to 0 to maintain stability
        for (int i = n - 1; i >= 0; i--) {
            int bit = getBit(A[i], bitPosition);
            count[bit]--;
            aux[count[bit]] = A[i];
        }

        // Copy the aux array to A[], so that A[] now
        System.arraycopy(aux, 0, A, 0, n);
        // END STRIP
    }

    // Helper method to get the bit at the given position
    // For example, getBit(5, 0) returns 1
    //              getBit(5, 1) returns 0
    //              getBit(5, 2) returns 1
    //              getBit(5, 3) returns 0
    private static int getBit(int number, int bitPosition) {
        // STUDENT return -1;
        // BEGIN STRIP
        return (number >> bitPosition) & 1;
        // END STRIP
    }

    // Helper Method to find the maximum number of bits required
    private static int getMaxBits(int[] A) {
        int max = 0;
        for (int num : A) {
            max = Math.max(max, num);
        }
        return 32 - Integer.numberOfLeadingZeros(max);
    }

}