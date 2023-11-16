package sorting;

import static java.lang.Math.log;

/**
 * This class represent a MinMaxHeap (generic over a Key type) implement as an array-based array.
 * Such data structure is useful to store elements and retrieve their minimum/maximum in constant time.
 * In order to do so, the invariants that are usually used in minimum or maximum heap are adapted.
 * For example, the `BinaryHeap` class (that you can find in another exercise) implements a minimum heap
 * by keeping the following invariant true: "For any node in the tree, every of its descendant has a value
 * greater or equal than it".
 * A similar invariant can be expressed for maximum heap.
 *
 * In a MinMaxHeap, the tree is decomposed into layers based on the depth of the node:
 *  - Every node in a depth which is even (0, 2, 4, ...) is a *min layer*
 *  - Every node in a depth which is odd (1, 3, 5, ...) is a *max layer*
 *
 * and the following invariants hold:
 *  - For every node in a min layer, every of its descendant has a value greater than it
 *  - For every node in a max layer, every of its descendant has a value lower than it
 *
 * In this exercise you need implement three methods:
 *  1) The min() method which return the minimum of the heap
 *  2) The max() method which return the maximum of the heap
 *  3) The swim() method which is used after a call to `insert` for ensuring the invariants
 *      of the MinMaxHeap are respected
 *
 * To help you for the swim function, a `getNodeDepth` function is provided which returns the depth of a node.
 */
public class MinMaxHeap<Key extends Comparable<Key>> {

    private Key[] content;
    private int size;

    @SuppressWarnings("unchecked")
    public MinMaxHeap(int initialSize) {
        this.content = (Key []) new Comparable[initialSize];
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    private void increaseSize() {
        Key [] newContent = (Key []) new Comparable[this.content.length*2];
        System.arraycopy(this.content, 0, newContent, 0, this.content.length);
        this.content = newContent;
    }

    /**
     * Returns the size of this heap
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns the minimum of the heap.
     * Expected time complexity: O(1)
     */
    public Key min() {
        // BEGIN STRIP
        if (this.size() == 0)
            return null;
        return this.content[1];
        // END STRIP
        // STUDENT return null;
    }

    /**
     * Returns the maximum of the heap.
     * Expected time complexity: O(1)
     */
    public Key max() {
        // BEGIN STRIP
        if (this.size() <= 1)
            return this.min();
        if (this.size() == 2)
            return this.content[2];
        Key k1 = this.content[2];
        Key k2 = this.content[3];
        if (higherThan(k1, k2)) {
            return k1;
        }
        return k2;
        // END STRIP
        // STUDENT return null;
    }

    /**
     * Swaps the elements at index i and j in the
     * array representing the tree
     *
     * @param i the first index to swap
     * @param j the second index to swap
     */
    private void swap(int i, int j) {
        Key tmp = this.content[i];
        this.content[i] = this.content[j];
        this.content[j] = tmp;
    }

    /**
     * Returns true if the first key is less than the second key
     *
     * @param key The base key for comparison
     * @param comparedTo The key compared to
     */
    private boolean lessThan(Key key, Key comparedTo) {
        return key.compareTo(comparedTo) < 0;
    }

    /**
     * Returns true if the first key is greater than the second key
     *
     * @param key The base key for comparison
     * @param comparedTo The key compared to
     */
    private boolean higherThan(Key key, Key comparedTo) {
        return key.compareTo(comparedTo) > 0;
    }

    /**
     * Returns the depth of the node at a given position
     *
     * @param position The index in the `content` array for which the depth must be computed
     */
    private int getNodeDepth(int position) {
        // There is no log2 function in java.lang.Math so we use this little 
        // formula to compute the log2 of K (which give, when rounded to its
        // integer value, the depth of the index)
        return (int) (log(position) / log(2));
    }

    /**
     * Swim a node in the tree
     *
     * @param position The position of the node to swim in the `content` array
     */
    public void swim(int position) {
        // BEGIN STRIP
        if (position == 1)
            return;
        Key node = this.content[position];
        Key parent = this.content[position/2];
        boolean has_grandparent = position/4 != 0 && position/2 != position/4;
        Key grandParent = this.content[position/4];
        int depth = this.getNodeDepth(position);
        boolean isMinLevel = depth % 2 == 0;
        if (isMinLevel) {
            // If the node is at a min level, we need to check that its value is
            // below its parent (which is at a max level)
            if (this.higherThan(node, parent)) {
                this.swap(position, position/2);
                this.swim(position/2);
            } else if (has_grandparent && this.lessThan(node, grandParent)) {
                this.swap(position, position/4);
                this.swim(position/4);
            }
        } else {
            // In the other case, the parent is at a min level so its children must be
            // higher than it
            if (this.lessThan(node, parent)) {
                this.swap(position, position/2);
                this.swim(position/2);
            } else if (has_grandparent && this.higherThan(node, grandParent)) {
                this.swap(position, position/4);
                this.swim(position/4);
            }
        }
        // END STRIP
    }

    /**
     * Inserts a new value in the heap
     *
     * @param k the value to insert
     */
    public void insert(Key k) {
        this.size += 1;
        if (this.size >= this.content.length) {
            this.increaseSize();
        }
        this.content[this.size] = k;
        this.swim(this.size);
    }
}
