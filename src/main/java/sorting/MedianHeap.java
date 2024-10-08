package sorting;

/**
 * In this task you need to implement a structure to store values such as :
 * - insertion is done in logarithmic time
 * - the median can be retrieved in constant time
 * - the median can be deleted in logarithmic time
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

    /**
     * Implementation of a binary heap that keep the minimal
     * value at its root
     */
    private class Heap {
        public int size;
        public int[] content;

        public Heap(int initialSize) {
            this.size = 0;
            this.content = new int[initialSize];
        }

        /**
         * Insertion of a new value in the heap
         * Complexity O(log(n)) with n the size of this heap
         * @param value the value to insert
         */
        public void insertion(int value) {
            if (this.size == this.content.length) this.increaseSize();
            this.content[this.size++] = value;
            swim(this.size-1);
        }

        /**
         * Doubles the available size of this binary heap
         */
        public void increaseSize() {
            int[] newContent = new int[this.size*2];
            System.arraycopy(this.content, 0, newContent, 0, this.content.length);
            this.content = newContent;
        }

        /**
         * Move the node at index k down the tree until it is lower than its children
         * Complexity O(log(n)) with n the size of this heap
         * @param k : the index of the node to move down
         */
        private void sink(int k) {
            while (2*k < this.size) {
                int lowestChild = 2*k;
                if (2*k+1 < this.size && this.content[2*k] > this.content[2*k+1]) lowestChild = 2*k+1;

                if (this.content[k] <= this.content[lowestChild]) break;

                int tmp = this.content[k];
                this.content[k] = this.content[lowestChild];
                this.content[lowestChild] = tmp;
                k = lowestChild;
            }
        }

        /**
         * Move the node at index k up the tree until it is stronger than its parent
         * Complexity O(log(n)) with n the size of this heap
         * @param k : the index of the node to move up
         */
        private void swim(int k) {
            while (k > 0 && this.content[k] < this.content[k/2]) {
                int tmp = this.content[k/2];
                this.content[k/2] = this.content[k];
                this.content[k] = tmp;
                k = k/2;
            }
        }

        /**
         * Retrieve and delete the root of this heap
         * Complexity O(log(n)) with n the size of this heap
         * @return the root of this heap
         */
        private int pullRoot() {
            assert this.size > 0;
            int root = this.content[0];
            this.content[0] = this.content[--this.size];
            sink(0);

            return root;
        }

        public int getRoot() {return this.content[0];}
    }

    // internal heap containing the value < to the median
    private Heap lHeap;

    // internal heap containing the value >= to the median
    private Heap rHeap;

    // END STRIP

    public MedianHeap(int initialSize) {
        // TODO
        // BEGIN STRIP
        lHeap = new Heap(initialSize/2);
        rHeap = new Heap(initialSize/2);
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
        if (value < rHeap.getRoot()) lHeap.insertion(-value);
        else rHeap.insertion(value);

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
        if (lHeap.size > rHeap.size) {
            int root = -lHeap.pullRoot();
            rHeap.insertion(root);
        }

        if (lHeap.size + 1 < rHeap.size) {
            int root = rHeap.pullRoot();
            lHeap.insertion(-root);
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
        return rHeap.getRoot();
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
        int median = rHeap.pullRoot();
        balanceHeaps();
        return median;
        // END STRIP
    }
}
