package utils;

public class BSTNode implements BinaryNode {

    private BSTNode left;
    private BSTNode right;
    private int value;

    public BSTNode(int value) {
        this.left = null;
        this.right = null;
        this.value = value;
    }

    public BSTNode getLeft() {
        return this.left;
    }

    public BSTNode getRight() {
        return this.right;
    }

    public int getValue() {
        return this.value;
    }

    /**
     * Adds a new value in the subtree rooted a this node
     */
    public void add(int newValue) {
        if (newValue > this.value) {
            if (this.right == null) {
                this.right = new BSTNode(newValue);
            } else {
                this.right.add(newValue);
            }
        } else {
            if (this.left == null) {
                this.left = new BSTNode(newValue);
            } else {
                this.left.add(newValue);
            }
        }
    }
}
