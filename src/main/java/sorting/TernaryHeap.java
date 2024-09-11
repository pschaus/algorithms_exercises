package sorting;

/**
 * A max-heap is a hierarchical tree structure with the following invariants:
 *  - The tree is essentially complete, i.e., all levels of the tree are filled except possibly the right most child part of the last one,
 *  - For any node in the tree, the value associated with the node is greater or equal than the values of its children.
 *
 * Consequently, the maximum value is situated at the root and can be accessed in constant time.
 * Notably, this invariant must be maintained after insertions or removals.
 *
 * In this assignment, your task is to implement the insert, size, getMax, and delMax operations for a ternary max heap data structure
 * implemented with an array.
 * In a ternary max heap, each node can have at most three children, and all children have values lower than the parent node.
 * The tree is represented by the array `content`, where the parent-child relationship is implicitly defined by indices.
 * Specifically, a node at index i has three children at indices 3i+1, 3i+2, and 3i+3.
 * It is assumed that the root is at index 0 in the array.
 *
 * For instance, consider a heap with a capacity of 6. After inserting numbers <8,2,3,8,9> in this order,
 * the array content could be as follows:
 * content: [9, 8, 3, 8, 2, 0], the size = 5 and the heap looks like this :
 *
 *                                                  9
 *                                                  |
 *                                       ----------------------
 *                                       |          |         |
 *                                       8          3         8
 *                                       |
 *                                       2
 *
 * Now after deleting the max, the array content could be :
 * content [8, 2, 3, 8, (9), 0] and the size = 4 and the heap :
 *                                                  8
 *                                                  |
 *                                        ----------------------
 *                                        |          |         |
 *                                        2          3         8
 *
 *  Notice that we left the 9 in the array, but it is not part of the heap anymore since the size is 4.
 *
 * To remove the maximum element from the heap while maintaining its structure,
 * the approach involved swapping the root with last element of the last layer (at position size-1) in the content array.
 * Subsequently, re-heapify the structure by allowing the new root to sink using swap with the largest of its children
 * until it is greater than all its children or reaches a leaf.
 *
 * Complete the implementation of the TernaryHeap class.
 *
 * The insert operation should run in O(log_3(n)) time, where n is the number of elements in the heap.
 * The delMax operation should run in O(log_3(n)) time, where n is the number of elements in the heap.
 * The getMax operation should run in O(1) time.
 *
 * Debug your code on the small examples in the test suite.
 */
public class TernaryHeap {

    // Array representing the heap. This is where all the values must be added
    // let this variable protected so that it can be accessed from the test suite
    protected int[] content;

    // BEGIN STRIP
    private int size;
    // END STRIP


    /**
     * Initializes an empty max-heap with the given initial capacity.
     * @param capacity : the initial capacity of the heap
     */
    public TernaryHeap(int capacity) {
        // TODO
        // BEGIN STRIP
        this.content = new int[capacity];
        size = 0;
        // END STRIP
    }

    /**
     * @return the number of keys currently in the heap.
     */
    public int size() {
        // TODO
        // STUDENT return -1;
        // BEGIN STRIP
        return size;
        // END STRIP
    }



    /**
     * Inserts a new key into the heap. After this method is finished, the heap-invariant must be respected.
     * @param x The key to be inserted
     */
    public void insert(int x) {
        // TODO
        // STUDENT
        // BEGIN STRIP
        content[size++] = x;
        swim(size-1);
        // END STRIP
    }

    /**
     * Removes and returns the largest key on the heap. After this method is finished, the heap-invariant must be respected.
     * @return The largest key on the heap
     */
    public int delMax() {
        // TODO
        // STUDENT return Integer.MIN_VALUE;
        // BEGIN STRIP
        int max = content[0];
        swap(0, --size);
        sink(0);
        return max;
        // END STRIP
    }

    /**
     * @return The largest key on the heap without removing it.
     */
    public int getMax() {
        // TODO
        // STUDENT return Integer.MIN_VALUE;
        // BEGIN STRIP
        return content[0];
        // END STRIP
    }

    // BEGIN STRIP
    /**
     * Swap the values at index i and j
     * @param i : the index of the first value
     * @param j : the index of the second value
     */
    private void swap(int i, int j) {
        int temp = content[i];
        content[i] = content[j];
        content[j] = temp;
    }

    /**
     * Move the node at index k up the tree until it is smaller than its parent
     * @param k : the index of the node to move up
     */
    private void swim(int k) {
        int parent = (k-1)/3;
        while (parent >=0 && content[parent] < content[k]) {
            swap(k, parent);
            k = parent;
            parent = (k-1)/3;
        }
    }

    /**
     * Move the node at index k down the tree until it is bigger than its children
     * @param k : the index of the node to move down
     */
    private void sink(int k) {

        while (3*k < size) {
            int largest = largestChild(k);
            if (largest == -1) break;
            if (content[k] >= content[largest]) break;
            swap(k, largest);
            k = largest;
        }
    }

    /**
     * Get the biggest child of the node at index i
     * @param i : the index of the node
     * return the index of the smallest child of the node at index i
     */
    private int largestChild(int i) {
        if (3 * i +1 > size - 1) return -1;
        int index = 3 * i + 1;
        int max = content[index];
        int maxIndex = index;
        for (int j : new int[]{index + 1, index + 2}) {
            if (j < size && content[j] > max) {
                maxIndex = j;
                max = content[j];
            }
        }
        return maxIndex;
    }
    // END STRIP

}