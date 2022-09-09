package utils;

/**
 * This interface define the methods that a node identified by a key need to implements.
 * This interface is generic over K which is a type that *must* implement the Comparable interface.
 */
public interface KeyNode<K extends Comparable<K>> {

    /**
     * Returns the key of the node
     */
    public K getKey();

    /**
     * Sets the key of the node
     *
     * @param newKey the new key
     */
    public void setKey(K newKey);
}
