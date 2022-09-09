package utils;

public class BSTNode<K extends Comparable<K>> implements BinaryNode, KeyNode<K> {

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
    public void setLeft(BinaryNode node) {
        if (node instanceof BSTNode) {
            this.left = (BSTNode<K>) node;
        }
    }

    public BSTNode<K> getRight() {
        return this.right;
    }

    @SuppressWarnings("unchecked")
    public void setRight(BinaryNode node) {
        if (node instanceof BSTNode) {
            this.right = (BSTNode<K>) node;
        }
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
     * Adds a new value in the subtree rooted a this node
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
