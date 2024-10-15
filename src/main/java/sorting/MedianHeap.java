package sorting;

// BEGIN STRIP
import java.util.PriorityQueue;
// END STRIP

/**
 * Imagine a data structure that supports :
 * - insertion in logarithmic time
 * - the median retrieval in constant time
 * - median deletion in logarithmic time
 *
 * Hint: You can use two heaps to solve this problem.
 *       There is no need to implement your own heap, you can
 *       use the PriorityQueue class from Java.
 *       Think about the clas invariant that you need to maintain
 *       after each insertion and deletion.
 *
 * When the number of stored element is odd, the median value is the value
 * at rank n+1/2 where n is the number of element
 * For example if the values {0, 1, 2, 3, 5} are stored, the median value is 2
 * When the number of stored element is even, you will arbitrarily consider that the median value
 * is the value at rank n/2 + 1
 * For example if the values {0, 1, 3, 5} are stored, the median value is 3
 */
public class MedianHeap {
    // BEGIN STRIP

    // internal heap containing the value < to the median
    private PriorityQueue<Integer> lHeap;

    // internal heap containing the value >= to the median
    private PriorityQueue<Integer> rHeap;

    // END STRIP

    public MedianHeap(int initialSize) {
        // TODO
        // BEGIN STRIP
        lHeap = new PriorityQueue<>(initialSize/2);
        rHeap = new PriorityQueue<>(initialSize/2);
        // END STRIP
    }

    /**
     * Add a new value in the structure
     * The expected time complexity is O(log(n)) with n the size of the heap
     *
     * @param value the added value
     */
    public void insertion(int value) {
        // TODO
        // BEGIN STRIP
        // all value in lHeap are represented by their opposite,
        // hence lHeap keeps the largest value at its root
        if (rHeap.isEmpty()) rHeap.add(value);
        else if (value < rHeap.peek()) lHeap.add(-value);
        else rHeap.add(value);

        // For the median to be at the root of rHeap,
        // we need that lHeap.length == rHeap.length - 1
        //  when the number of inserted element is odd
        // or lHeap.length == rHeap.length when the number of element is even

        // As this property is kept at each insertion there is at most one
        // transfer of root for an insertion
        balanceHeaps();
        // END STRIP
    }

    // BEGIN STRIP

    /**
     * If the heaps are unbalanced,
     * transfers the root of the heavily loaded heap to
     * the lowly loaded heap
     */
    private void balanceHeaps() {
        if (lHeap.size() > rHeap.size()) {
            int root = -lHeap.poll();
            rHeap.add(root);
        }

        if (lHeap.size() + 1 < rHeap.size()) {
            int root = rHeap.poll();
            lHeap.add(-root);
        }
    }
    // END STRIP

    /**
     * Retrieve the median value of the stored elements
     * Complexity O(1)
     * @return the median
     */
    public int findMedian() {
        // TODO
        // STUDENT return -1;

        // BEGIN STRIP
        return rHeap.peek();
        // END STRIP
    }

    /**
     * Delete and return the median value
     * Complexity O(log(n)) with n the number of stored elements
     * @return the median value
     */
    public int deleteMedian() {
        // TODO
        // STUDENT return -1;
        // BEGIN STRIP
        int median = rHeap.poll();
        balanceHeaps();
        return median;
        // END STRIP
    }
}
