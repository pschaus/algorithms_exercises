package searching;

// BEGIN STRIP
import java.util.HashMap;
// END STRIP

/**
 * Santa’s elves are hard at work preparing gift bags filled with candies for children. Each candy has a specific
 * sweetness level. To ensure the children enjoy their treats while staying healthy, Santa has set a strict rule: every
 * bag must have a total sweetness of exactly k.
 *
 * The elves want to make the most of the candies they have, so they aim to maximize the number of candies in each bag
 * while following Santa’s rule. However, there’s a catch: only a contiguous selection of candies from the factory’s
 * production line can be used to prepare each bag.
 *
 * One of the elves in charge of the candy-packing operation provides you with a list of candies from a production line
 * of the factory, along with their sweetness values. Can you help the elves find the size of the largest possible candy
 * bag they can prepare from this line with a total sweetness of k?
 *
 * Input:
 *     * An array A of n integers, where each integer represents the sweetness value of a candy.
 *     * An integer k, the required total sweetness.
 *
 * Output:
 *     * A single integer representing the maximum number of candies with a total sweetness of k that are contiguous in
 *       the input array.
 *
 * For example:
 *     * Input: array = [1, -1, 5, -2, 3], k = 3
 *     * Output: 4 (the subarray [1, -1, 5, -2] has a sum of 3 and length 4)
 *
 *     * Input: array = [-2, -1, 2, 1], k = 1
 *     * Output: 2 (the subarray [-1, 2] has a sum of 1 and length 2)
 *
 * Expected Time-Complexity: O(N) (N being the size of the array)
 *
 * Hint:
 *     * To find the longest subarray with a given sum efficiently, think about using a prefix sum. As you compute a
 *       running sum while traversing the array, consider how you can quickly check if any prior prefix sum you
 *       encountered would help you find a contiguous subarray with the desired sum k.
 */
public class CandiesBag {

    /**
     * Computes the maximum number of candies with a total sweetness of k that are contiguous in the input array.
     *
     * @param array The array of sweetness values
     * @param k The required total sweetness
     * @return The maximum number of candies with a total sweetness of k that are contiguous in
     *         the input array.
     */
    public static int findMaximumSize(int[] array, int k) {
        // TODO
        // BEGIN STRIP
        // key: prefix sum = sum(array[0,...,i]) value: i.In case two indices have the same prefix sum, we keep the
        // smallest index
        HashMap<Integer, Integer> prefixSums = new HashMap<>();
        prefixSums.put(0, 0);
        int runningSum = 0;
        int maxLength = 0;
        for (int i = 0; i < array.length; i++) {
            runningSum += array[i];
            if (prefixSums.containsKey(runningSum - k)) {
                maxLength = Math.max(maxLength, i - prefixSums.get(runningSum - k) + 1);
            }
            if (!prefixSums.containsKey(runningSum)) {
                prefixSums.put(runningSum, i + 1);
            }
        }
        return maxLength;
        // END STRIP
        // STUDENT return -1;
    }
}
