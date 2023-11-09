package searching;

import java.util.ArrayList;


/**
 *  We study a BST representation using an arrayList internal representation.
 *  Rather than using a Linked-Node Data-Structure, the left/right children
 *  links will be encoded with indices in array lists.
 *  More exactly, in this data-structure each node
 *  is represented by an index i (that corresponds to the ith added element)
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
 *    values:        A,  B, C, D, E
 *    keys:         12, 15, 5, 8, 1
 *    idxLeftNode:   2, -1, 4,-1,-1
 *    idxRightNode:  1, -1, 3,-1,-1
 *
 * We ask you to augment the data-structure with a *delete* method.
 *
 * ⚠️There is one strong requirement on your design ⚠️:
 *   The memory consumed since the creation of the BST is in O(K)
 *   where K is the number different keys used
 *   since the creation of the data-structure.
 *   More concretely, the size of the "keys" vector cannot be more than K
 *
 *  You are
 *  - not allowed to import or use other java classes than ArrayList or the ones already present.
 *  - allowed to modify all the existing methods and add instance variables
 *
 *  HINT 💡: Consider reusing key locations of previously deleted keys without
 *           trying to reorganize the BST.
 */
public class ArrayBSTDelete<Key extends Comparable<Key>, Value> {

    // The next four array lists should always have exactly equal sizes

    public ArrayList<Key> keys;
    public ArrayList<Value> values;

    public ArrayList<Integer> idxLeftNode; // idxLeftNode[i] = index of left node of i
    public ArrayList<Integer> idxRightNode; // idxRightNode[i] = index of right node of i

    // BEGIN STRIP
    private ArrayList<Boolean> deleted; // deleted[i] = true if node i has been deleted from the tree
    // END STRIP

    final int NONE = -1;

    public ArrayBSTDelete() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
        idxLeftNode = new ArrayList<>();
        idxRightNode = new ArrayList<>();
        // BEGIN STRIP
        deleted = new ArrayList<>();
        // END STRIP
    }

    private void addNode(Key key, Value val) {
        keys.add(key);
        values.add(val);
        idxLeftNode.add(NONE);
        idxRightNode.add(NONE);
        // BEGIN STRIP
        deleted.add(false);
        // END STRIP
    }

    /**
     * Insert the entry in the BST, replace the value if the key is already present
     * in O(h) where h is the height of the tree
     * @param key a key that is present or not in the BST
     * @param val the value that must be attached to this key
     * @return true if the key was added, false if already present and the value has simply been erased
     */
    public boolean put(Key key, Value val) {
        if (!values.isEmpty()) {
            int i = 0; // start at the root
            // the new node will be created at index values.size
            ArrayList<Integer> idxChild;
            do {
                int cmp = key.compareTo(keys.get(i));
                if (cmp == 0) {
                    // key already present in this node, just replace its value
                    values.set(i,val);
                    // BEGIN STRIP
                    // if the node was deleted, we undelete it
                    if (deleted.get(i)) {
                        deleted.set(i,false);
                        return true;
                    }
                    // END STRIP
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
    }

    /**
     * Return the value attached to this key, null if the key is not present
     * in O(h) where h is the height of the tree
     * @param key the key to search
     * @return the value attached to this key, null if the key is not present
     */
    public Value get(Key key) {
        int i = getNodeIndex(key);
        if (i == NONE) return null;
        // BEGIN STRIP
        if (deleted.get(i)) return null;
        // END STRIP
        return values.get(i);
    }

    /**
     * Retrieves the node index with key if present,
     * NONE if not present
     */
    private int getNodeIndex(Key key) {
        if (!keys.isEmpty()) {
            int i = 0;
            while (i != NONE) {
                int cmp = key.compareTo(keys.get(i));
                if (cmp == 0) return i;
                else if (cmp < 0) i = idxLeftNode.get(i);
                else i = idxRightNode.get(i);
            }
        }
        return NONE;
    }

    /**
     * Delete the key (and its associated value) from the BST.
     * @param key the key to delete
     * @return true if the key was deleted, false if the key was not present
     */
    public boolean delete(Key key) {
        // TODO
        // STUDENT return false;
        // BEGIN STRIP
        int i = getNodeIndex(key);
        if (i == NONE) return false;
        if (deleted.get(i)) return false;
        deleted.set(i, true);
        return true;
        // END STRIP
    }


    public static void main(String[] args) {
        ArrayBSTDelete<Integer,Character> bst = new ArrayBSTDelete<>();

        // (12,A),(15,B),(5,C),(8,d),(1,E)
        bst.put(12,'A');
        bst.put(15,'B');
        bst.put(5,'C');
        bst.put(8,'D');
        bst.put(1,'E');

        for (int i = 0; i < 100; i++) {
            bst.delete(15);
            bst.put(15,'B');
        }
        System.out.println(bst.keys.size());


    }


}
