package sorting;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 *  Author: Pierre Schaus
 *
 *  Generic min priority queue implementation with a linked-data-structure
 *  The heap-tree is internally represented with Node's that store the keys
 *  Each node
 *
 *  - has a pointer its children and parent:
 *      - a null pointer indicates the absence of child/parent
 *  - a key (the values stored in the heap)
 *  - a size that is equal to the number of descendant nodes
 *
 *  A heap is an essentially an almost complete tree
 *  that satisfies the heap property:
 *  for any given node the key is less than the ones in the descendant nodes
 *  Here is an example of a heap:
 *
 *               3
 *           /      \
 *         5         4
 *        /  \      /  \
 *       6    7    8    5
 *      / \  / \  /
 *     7  8 8  9 9
 *
 *
 *
 *  The insert and min methods are already implemented maintaining the heap property
 *  Your task is to implement the delMin() method.
 *  This method should execute in O(log(n)) where n is the number of elements in the priority queue
 *
 *  You can add any method that you want but leave the instance variable
 *  and public API untouched since it used by the tests
 *
 * Hint: use the unit tests to debug your code, you might get some inspiration from the insert method
 *
 * @param <Key> the generic type of key on this priority queue
 */
public class MinPQLinked<Key> {

    // class used to implement the Nodes in the heap
    public class Node {
        public Key value;
        public Node left;
        public Node right;
        public Node parent;
        public int size;

        public Node(Node parent) {
            this.parent = parent;
            this.size = 1;
        }
    }

    public Node root; // number of items on priority queue
    public Comparator<Key> comparator;  // comparator used to compare the keys


    /**
     * Initializes an empty priority queue using the given comparator.
     *
     * @param  comparator the order in which to compare the keys
     */
    public MinPQLinked(Comparator<Key> comparator) {
        this.comparator = comparator;
    }

    /**
     * Returns true if this priority queue is empty.
     *
     * @return {@code true} if this priority queue is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns the number of keys on this priority queue.
     *
     * @return the number of keys on this priority queue
     */
    public int size() {
        if (root == null) {
            return 0;
        } else {
            return root.size;
        }
    }

    /**
     * Returns a smallest key on this priority queue.
     *
     * @return a smallest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return root.value;
    }



    /**
     * Adds a new key to this priority queue.
     *
     * @param  x the key to add to this priority queue
     */
    public void insert(Key x) {
        if (root == null) {
            root = new Node(null);
            root.value = x;
        } else {
            Node n = createNodeInLastLayer(); // create the new node in last layer
            n.value = x; // store x in this node
            swim(n); // restore the heap property from this leaf node to the root
        }
    }


    // maintains the heap invariant
    private void swim(Node n) {
        while (n != root && greater(n.parent, n)) {
            exch(n, n.parent);
            n = n.parent;
        }
    }

    // Creates a new empty node in last layer (ensuring it stay essentially an almost complete tree)
    // and returns the node
    private Node createNodeInLastLayer() {
        Node current = root;
        current.size++;
        while (current.left != null && current.right != null) {
            // both left and right are not null
            if (isPowerOfTwo(current.left.size+1) && current.right.size < current.left.size) {
                // left is complete and there is fewer in right subtree
                current = current.right; // follow right direction
            } else {
                current = current.left; // follow left direction
            }
            current.size++; // augment size sisnce this node will have new descendant
        }
        // hook up the new node
        if (current.left == null) {
            current.left = new Node(current);
            return current.left;
        } else {
            assert (current.right == null);
            current.right = new Node(current);
            return current.right;
        }
    }


    /**
     * Removes and returns a smallest key on this priority queue.
     *
     * @return a smallest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key delMin() {
        // TODO (unfold the comment on top of the file to read the instructions)
        // STUDENT return null;
        // BEGIN STRIP
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        Key min = root.value;
        if (size() == 1) {
            root = null;
        } else {
            root.value = removeLastNodeInLastLayer();
            sink(root);
        }
        return min;
        // END STRIP
    }

    // BEGIN STRIP
    private void sink(Node n) {
        while (n.left != null) {
            Node minChild = n.left;
            if (n.right != null && greater(n.left,n.right)) {
                minChild = n.right;
            }
            if (!greater(n,minChild)) break;
            exch(n,minChild);
            n = minChild;
        }
    }

    private Key removeLastNodeInLastLayer() {
        assert (root!= null && root.left != null);
        Node current = root;
        if (size() == 1) {
            root = null;
            return current.value;
        }
        current.size--;
        boolean right = true;
        while (current.left != null) {
            boolean leftComplete = isPowerOfTwo(current.left.size+1);
            if (!leftComplete || current.right == null) { // left incomplete or right = null
                current = current.left;
            } else { // left complete & current.right != null
                boolean rightComplete = isPowerOfTwo(current.right.size+1);
                if (!rightComplete) {
                    current = current.right;
                } else {
                    // left and right are complete
                    if (current.left.size > current.right.size) {
                        current = current.left;
                    } else {
                        current = current.right;
                    }
                }
            }
            current.size--;
        }
        if (current.parent.left == current) {
            current.parent.left = null;
        } else {
            current.parent.right = null;
        }

        return current.value;
    }

    // alternative method based on binary encoding of last index
    private Key removeLastNodeInLastLayer1() {
        // height of the heap = ceil (log_2 (n+1))
        int h = (int) Math.ceil((Math.log(size()+1) / Math.log(2)));
        // position of the last node on the last layer
        int index = size() - ((1 << (h-1)) - 1) - 1;
        Node current = root;
        current.size--;
        for (int i = h-2; i >= 0; i--) {
            // if i_th bit is 0, follow left otherwise follow right
            if ((1 << i & index) == 0) {
                current = current.left;
            } else {
                current = current.right;
            }
            current.size--;
        }
        Key k = current.value;
        if (current.parent.left == current) {
            current.parent.left = null;
        } else {
            current.parent.right = null;
        }
        return k;
    }

    // END STRIP


    /***************************************************************************
     * Helper functions for compares and swaps.
     ***************************************************************************/


    // check if x = 2^n for some x>0
    private boolean isPowerOfTwo(int x) {
        return (x & (x - 1)) == 0;
    }

    // Check if node i > j
    private boolean greater(Node i, Node j) {
        return comparator.compare(i.value,j.value) > 0;
    }

    // exchange the values in two nodes
    private void exch(Node i, Node j) {
        Key swap = i.value;
        i.value = j.value;
        j.value = swap;
    }


}