package searching;

import java.util.ArrayList;


/**
 *  We study a BST representation using an arrayList internal representation.
 *  Rather than using a Linked-Node Data-Structure, the left/right children
 *  will be encoded with indices in array lists.
 *  More exactly, in this data-structure each node
 *  is represented by an index i (that correspond to the ith added element)
 *  The left  node of node i, if any, is located
 *        at index idxLeftNode.get(i) otherwise idxLeftNode.get(i) == NONE
 *  The right node of node i, if any is located
 *       at index idxRightNode.get(i) otherwise idxRightNode.get(i) == NONE
 *
 *  The tree below is the result of putting (key,value) pairs (12,A),(15,B),(5,C),(8,d),(1,E) (in this order)
 *
 *                12(A)
 *                / \
 *               /   \
 *             5(C)  15(B)
 *             / \
 *          1(E)  8(D)
 *
 *   The state of internal array list after those put operations is
 *    values:        A, B, C, D, E
 *    keys:        12, 15, 5, 8, 1
 *    idxLeftNode:  2, -1, 4,-1,-1
 *    idxRightNode: 1, -1, 3,-1,-1
 *
 *  You can implement the get method before the put method if you prefer since
 *  the two methods will be graded separately.
 *
 *  You cannot add of change the fields already declared, nor change
 *  the signatures of the methods in this
 *  class but feel free to add methods if needed.
 *
 */
public class ArrayBST<Key extends Comparable<Key>, Value> {

    // The next four array lists should always have exactly equal size after a put

    public ArrayList<Key> keys;
    public ArrayList<Value> values;

    public ArrayList<Integer> idxLeftNode; // idxLeftNode[i] = index of left node of i
    public ArrayList<Integer> idxRightNode; // idxRightNode[i] = index of right node of i

    final int NONE = -1;

    public ArrayBST() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
        idxLeftNode = new ArrayList<>();
        idxRightNode = new ArrayList<>();
    }

    // BEGIN STRIP
    private void addNode(Key key, Value val) {
        keys.add(key);
        values.add(val);
        idxLeftNode.add(NONE);
        idxRightNode.add(NONE);
    }
    // END STRIP
    /**
     * Insert the entry in the BST, replace the value if the key is already present
     * in O(h) where h is the height of the tree
     * @param key a key that is present or not in the BST
     * @param val the value that must be attached to this key
     * @return true if the key was added, false if already present and the value has simply been erased
     */
    public boolean put(Key key, Value val) {
        // STUDENT return false;
        // BEGIN STRIP
        if (values.size() > 0) {
            int i = 0; // start at the root
            // the new node will be created at index values.size
            ArrayList<Integer> idxChild;
            do {
                int cmp = key.compareTo(keys.get(i));
                if (cmp == 0) {
                    // key already present in this node, just replace its value
                    values.set(i,val);
                    return false;
                } else {
                    // key different, follow the left or right link
                    idxChild = cmp < 0 ? idxLeftNode : idxRightNode;
                    int next = idxChild.get(i);
                    if (next == NONE) idxChild.set(i,keys.size());  // leaf node reached, we create the reference toward the new node
                    i = next;
                }
            } while (i != NONE);
        }
        // create the new node
        addNode(key,val);
        return true;
        // END STRIP
    }

    /**
     * Return the value attached to this key, null if the key is not present
     * in O(h) where h is the height of the tree
     * @param key
     * @return the value attached to this key, null if the key is not present
     */
    public Value get(Key key) {
        // STUDENT return null;
        // BEGIN STRIP
        if (values.size() > 0) {
            int i = 0; // start at the root
            ArrayList<Integer> idxChild;
            do {
                if (key.compareTo(keys.get(i)) == 0) {
                    // key found
                    return values.get(i);
                } else {
                    // key different, simply follow the left or right link
                    idxChild = key.compareTo(keys.get(i)) < 0 ? idxLeftNode : idxRightNode;
                    i = idxChild.get(i);
                }
            } while (i != NONE);
        }
        return null;
        // END STRIP
    }




}
