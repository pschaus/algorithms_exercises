package utils;

/**
 * This interface represents a binary node in a tree structure.
 */
public interface BinaryNode {
    /**
     * Returns the left child of this node
     */
    public BinaryNode getLeft();

    /**
     * Sets the left child of the node
     *
     * @param node the left child
     */
    public void setLeft(BinaryNode node);

    /**
     * Returns the right child of this node
     */
    public BinaryNode getRight();

    /**
     * Sets the right child of the node
     *
     * @param node the right child
     */
    public void setRight(BinaryNode node);
}
