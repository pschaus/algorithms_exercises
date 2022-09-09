package searching;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
// BEGIN STRIP
import java.util.Stack;
// END STRIP

import utils.BSTNode;

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
 */
public class BinarySearchTreeIterator<Key extends Comparable<Key>> implements Iterable<Key> {

    private BSTNode<Key> root;

    public BinarySearchTreeIterator() { };

    public int size() {
        return this.size(root);
    }

    private int size(BSTNode<Key> node) {
        if (node == null) {
            return 0;
        }
        return node.getSize();
    }

    public void put(Key key) {
        this.root = put(this.root, key);
    }

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
        private BSTNode<Key> current;
        private int size;
        private Stack<BSTNode<Key>> stack;

        public BSTIterator() {
            this.stack = new Stack<>();
            this.current = root;
            while (current != null) {
                this.stack.push(current);
                current = current.getLeft();
            }
            if (root != null) {
                this.size = root.getSize();
            }
        }

        @Override
        public boolean hasNext() {
            // The size of the tree has changed since the creation of the iterator. It means that
            // the tree has been modified
            if (root != null && this.size != root.getSize()) {
                throw new ConcurrentModificationException();
            }
            return current != null || !this.stack.isEmpty();
        }

        @Override
        public Key next() {
            if (root != null && this.size != root.getSize()) {
                throw new ConcurrentModificationException();
            }
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
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
        }
        // END STRIP
    }
}
