package searching;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
// BEGIN STRIP
import java.util.Stack;
// END STRIP

/**
 * In this exercise, we are interested in implementing an iterator (BSTIterator) for a Binary Search Tree (BST).
 * The iterator must traverse the tree in-order. This means that for each node, the left sub-tree is visited, then the node and finally the right sub-tree.
 *
 *  For example, consider the following tree
 *
 *                              12
 *                              |
 *                 8 ------------------------ 18
 *                  |                          |
 *           3 ------------ 11       14 -------------- 20
 *                          |        |
 *                     9 ---         --- 15
 *
 *
 * The iterator visits the nodes in this order: 3, 8, 9, 11, 12, 14, 15, 18, 20
 * We ask you to complete the BSTIterator class, which must implement the Iterator interface.
 *
 * The BSTNode are generic over their key (the integers in the example above) and implement the 
 * BinaryNode and KeyNode interface available in the utils package.
 *
 * Hint: You have two strategies to implement this iterator Fail-Fast and Fail-Safe
 * https://www.geeksforgeeks.org/fail-fast-fail-safe-iterators-java/
 *
 * The Fail-Safe will collect all the keys in a collection and return an iterator on this collection.
 * The Fail-Fast will lazily return the elements and throw an exception if the BST is modified while iterating on it.
 *
 * The advantage of Fail-Fast is that the constructor and iteration is Lazy.
 * The total cost of iterating will be Theta(n) but the initialization can be O(h).
 *
 * It is a good exercise to implement both version.
 *
 */
public class BinarySearchTreeIterator<Key extends Comparable<Key>> implements Iterable<Key> {

    private BSTNode<Key> root;

    public BinarySearchTreeIterator() { };

    /**
     * Returns the size of the tree
     */
    public int size() {
        return this.size(root);
    }

    /**
     * Returns the size the subtree rooted at node
     *
     * @param node the root of the subtree
     */
    private int size(BSTNode<Key> node) {
        if (node == null) {
            return 0;
        }
        return node.getSize();
    }

    /**
     * Adds a value in the tree
     *
     * @param key the value to add
     */
    public void put(Key key) {
        this.root = put(this.root, key);
    }

    /**
     * Adds a value in a subtree of the tree
     *
     * @param node the root of the subtree
     * @param key the value to add
     */
    private BSTNode<Key> put(BSTNode<Key> node, Key key) {
        if (node == null) {
            return new BSTNode<>(key, 1);
        }
        int cmp = key.compareTo(node.getKey());
        if (cmp < 0) {
            node.setLeft(put(node.getLeft(), key));
        } else if (cmp > 0) {
            node.setRight(put(node.getRight(), key));
        }
        node.setSize(1 + size(node.getLeft()) + size(node.getRight()));
        return node;
    }

    @Override
    public Iterator<Key> iterator() {
        return new BSTIterator();
    }

    private class BSTIterator implements Iterator<Key> {

        // BEGIN STRIP
        private int size;
        // Internal stack used to keep track of the ancestors of the current node
        private Stack<BSTNode<Key>> stack;
		// END STRIP

        public BSTIterator() {
			// BEGIN STRIP
            this.stack = new Stack<>();
            BSTNode<Key> current = root;
            while (current != null) {
                this.stack.push(current);
                current = current.getLeft();
            }
            this.size = size();
			// END STRIP
        }

        @Override
        public boolean hasNext() {
			// TODO
			// STUDENT return false;
			// BEGIN STRIP
            // The size of the tree has changed since the creation of the iterator.
            // It means that the tree has been modified (safe since no deletion allowed)
            if (this.size != size()) {
                throw new ConcurrentModificationException();
            }
            return !this.stack.isEmpty();
			// END STRIP
        }

        @Override
        public Key next() {
			// TODO
			// STUDENT return null;
			// BEGIN STRIP
            if (this.size != size()) {
                throw new ConcurrentModificationException();
            }
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            // INVARIANT:
            // Node on top of the stack contains a reference
            // to the node that should be returned next
            BSTNode<Key> node = this.stack.pop();
            Key key = node.getKey();
            if (node.getRight() != null) {
                node = node.getRight();
                while (node != null) {
                    this.stack.push(node);
                    node = node.getLeft();
                }
            }
            return key;
        	// END STRIP
        }
    }

    class BSTNode<K extends Comparable<K>> {

        private BSTNode<K> left;
        private BSTNode<K> right;
        private K key;
        private int size;

        public BSTNode(K key) {
            this.left = null;
            this.right = null;
            this.key = key;
            this.size = 0;
        }

        public BSTNode(K key, int size) {
            this.left = null;
            this.right = null;
            this.key = key;
            this.size = size;
        }

        public BSTNode<K> getLeft() {
            return this.left;
        }

        @SuppressWarnings("unchecked")
        public void setLeft(BSTNode<K> node) {
            this.left = node;
        }

        public BSTNode<K> getRight() {
            return this.right;
        }

        @SuppressWarnings("unchecked")
        public void setRight(BSTNode<K> node) {
            this.right = node;
        }

        public K getKey() {
            return this.key;
        }

        public void setKey(K newKey) {
            this.key = newKey;
        }

        public int getSize() {
            return this.size;
        }

        public void setSize(int newSize) {
            this.size = newSize;
        }

        /**
         * Adds a new value in the subtree rooted at this node
         */
        public void add(K key) {
            if (key.compareTo(this.key) > 0) {
                if (this.right == null) {
                    this.right = new BSTNode<>(key);
                } else {
                    this.right.add(key);
                }
            } else {
                if (this.left == null) {
                    this.left = new BSTNode<>(key);
                } else {
                    this.left.add(key);
                }
            }
        }
    }
}

