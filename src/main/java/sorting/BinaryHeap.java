package sorting;

/**
 * In this task, you must implement the `push` operation on a binary heap data structure.
 * As a reminder, a heap is a tree data structure such that the following invariant is respected
 *  
 *      For any node in the tree, the value associated with the node is higher (for a maxHeap) or lower
 *      (for a minHeap) than the value of its children.
 *
 * As a consequence, the minimum/maximum value is located at the root and can be retrieved in constant time.
 * In particular, this invariant must be respected after a push (or remove) operation.
 *
 * In this exercise the tree is represented with an array. The parent-child relation is implicitely represented
 * by the indices. A node at index i has its two children at index 2i and 2i+1.
 * For this is it assumed that the root is located at index 1 in the array.
 */
public class BinaryHeap {

    private int [] content;
    private int size;

    public BinaryHeap(int initialSize) {
        this.content = new int[initialSize];
        this.size = 0;
    }

    /**
     * Doubles the available size of this binary heap
     */
    private void increaseSize() {
        int [] newContent = new int[this.content.length*2];
        System.arraycopy(this.content, 0, newContent, 0, this.content.length);
        this.content = newContent;
    }

    /**
     * Add a new value in this heap
     * The expected time complexity is O(log(n)) with n the size of the binary heap
     *
     * @param value the added value
     */
    public void push(int value) {
        // BEGIN STRIP       
        this.size += 1;
        if (this.size == this.content.length) {
            this.increaseSize();
        }
        this.content[this.size] = value;
        int i = this.size;
        while (i > 0 && this.content[i] < this.content[i/2]) {
            int tmp = this.content[i/2];
            this.content[i/2] = this.content[i];
            this.content[i] = tmp;
            i = i/2;
        }
        // END STRIP
    }

    /**
     * Returns the content of this heap
     */
    public int[] getContent() {
        return this.content;
    }

    /**
     * Returns the size of this heap
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns the root of the tree representing this heap
     */
    public int getRoot() {
        return this.content[1];
    }
}
