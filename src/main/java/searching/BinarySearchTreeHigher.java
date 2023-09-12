package searching;

/**
 * With the given partial implementation of a Binary Search Tree,
 * we ask you to implement the following methods :
 * - minKey() which return the smallest key in the BST, or null if it is empty
 * - higherKey(Key key) which returns the least key strictly greater than the given key,
 *   or null if there is no such key
 *
 */
public class BinarySearchTreeHigher<Key extends Comparable<Key>, Value> {

    private Node root;             // root of BST

    private class Node {
        private final Key key;       // sorted by key
        private Value val;           // associated data
        private Node left, right;    // left and right subtrees
        private int size;            // number of nodes in subtree

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public BinarySearchTreeHigher() {}

    /**
     * Returns true if this symbol table is empty.
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table
     *         and {@code null} if the key is not in the symbol table
     */
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }

    /**
     * Search for key, update value if key is found. Grow table if key is new.
     *
     * @param  key the key
     * @param  val the value
     */
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }


    /**
     * Returns the minimum key
     * The expected complexity is O(h) where h is the height of the BST
     *
     * @return the minimum key, null if the tree is empty
     */
    public Key minKey() {
        // STUDENT return null;
        // BEGIN STRIP
        return minKey(root);
        // END STRIP
    }


    // BEGIN STRIP
    private Key minKey(Node x) {
        if (x == null) {
            return null;
        } else if (x.left == null) {
            return x.key;
        } else {
            return minKey(x.left);
        }
    }
    // END STRIP

    /**
     * Returns the smallest key strictly greater than the given key, or null if there is no such key.
     * The expected complexity is O(h) where h is the height of the BST
     *
     * @param key - the key
     * @return the least key greater than key, or null if there is no such key
     */
    public Key higherKey(Key key) {
        // STUDENT return null;
        // BEGIN STRIP
        return higherKey(root, key);
        // END STRIP
    }

    private Key higherKey(Node x, Key key) {
        // STUDENT return null;
        // BEGIN STRIP
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        // key >= x.key
        if (cmp >= 0) return higherKey(x.right, key);
        // key < x.key
        Key left = higherKey(x.left, key);
        if (left == null) return x.key;
        else return left;
        // END STRIP
    }

}