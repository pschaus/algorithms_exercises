package searching;

/**
 * In this exercise, we ask you to implement the put method of a 23Tree.
 * We give you a class representing a 23Node
 *
 * A 2Node is represented with only leftChild and middleChild + rightKey is set to null
 *
 * A 3Node is represented using all variables (with leftKey < rightKey) and leftValue is the value associated with leftKey
 * (the same goes for rightKey and rightValue)
 *
 * Also don't forget that if a key is taken up to the root and the root is already
 * a 3Node, you must create a new level in the tree
 *
 * For example : the insert of Key, Value (6,6) in the following tree:
 *
 *
 *                 9
 *                / \
 *              7-8  10     // a 3Node containing (7 and 8) and a 2 Node containg 10
 *
 * should return
 *
 *                7-9      // 3Node with 3 children
 *               / | \
 *              6  8  10   // 2Nodes
 *
 */
class Node23<Key extends Comparable<Key>>{

    // BEGIN STRIP
    Key key_carry;   // used to carry values upper in the tree => hidden to students
    // is necessary to make a key go in the parent or even up to the root
    Integer value_carry;
    static Node23 subtree, subtree2;
    // END STRIP

    Key leftKey, rightKey;  // leftKey < rightKey
    Integer leftValue, rightValue;
    Node23<Key> leftChild, middleChild, rightChild;


    public Node23(Key k, Integer v, Node23 left, Node23 right){
        this.leftKey = k;
        this.leftValue = v;
        this.leftChild = left;
        this.middleChild = right; // is comprised between [leftKey, inf[
        this.rightKey = null;
        this.rightChild = null;
    }

    public Node23(Key leftKey, Key rightKey, Integer leftValue, Integer rightValue, Node23 left, Node23 middle, Node23 right){
        this.leftKey = leftKey.compareTo(rightKey) < 0  ? leftKey : rightKey;
        this.rightKey = leftKey.compareTo(rightKey) < 0  ? rightKey : leftKey;
        this.leftValue = leftKey.compareTo(rightKey) < 0  ? leftValue : rightValue;
        this.rightValue = leftKey.compareTo(rightKey) < 0  ? rightValue : leftValue;
        this.leftChild = left;
        this.rightChild = right;
        this.middleChild = middle;
    }

    /**
     * @return true if this node is a 2Node (only one key and 2 children)
     */
    public boolean is2Node(){
        return rightKey==null;
    }

    /**
     * @return true if this node is a 3Node (2 keys and 3 children)
     */
    public boolean is3Node(){
        return !is2Node();
    }

    /**
     * @return true if this node is a leaf (no children)
     *         false otherwise
     */
    public boolean isLeaf() {
        return leftChild == null && middleChild == null && rightChild == null;
    }

    // BEGIN STRIP
    public Node23<Key> put(Node23<Key> n, Key k, Integer v) {
        if (n.is3Node()) return n.put3Node(k, v);
        else return n.put2Node(k, v);

    }
    // END STRIP

    public Node23<Key> put(Key k, Integer v){
        // BEGIN STRIP
        Node23<Key> newNode = put(this, k, v);
        if (newNode.key_carry != null) return new Node23<Key>(newNode.key_carry, newNode.value_carry, subtree, subtree2);
        return newNode;
        // END STRIP
        // TODO
        // STUDENT return null;
    }

    // BEGIN STRIP
    public Node23<Key> put3Node(Key k, Integer v){
        if (leftKey.compareTo(k) == 0) {
            leftValue = v;
            return this;
        }
        if (rightKey.compareTo(k) == 0) {
            rightValue = v;
            return this;
        }
        if (isLeaf()){
            // create an initial carry
            if (k.compareTo(leftKey) > 0 && k.compareTo(rightKey) < 0){
                // the new key is the carry
                subtree = new Node23<Key>(leftKey, leftValue, null, null);
                subtree2 = new Node23<Key>(rightKey, rightValue, null, null);
                key_carry = k;
                value_carry = v;
            }else if (k.compareTo(leftKey) < 0){
                // the left key is the carry
                subtree = new Node23<Key>(k, v, null, null);
                subtree2 = new Node23<Key>(rightKey, rightValue, null, null);
                key_carry = leftKey;
                value_carry = leftValue;
            }else{
                // the right key is the carry
                subtree = new Node23<Key>(leftKey, leftValue, null, null);
                subtree2 = new Node23<Key>(k, v, null, null);
                key_carry = rightKey;
                value_carry = rightValue;
            }
            return this;
        }
        // insert middle
        if (k.compareTo(leftKey) > 0 && k.compareTo(rightKey) < 0){
            Node23<Key> n = put(middleChild, k, v);
            // carry is not null => continue carry to the parent because this is a 3Node
            // also split the Node
            if (n.key_carry != null){
                subtree = new Node23<Key>(leftKey, leftValue, leftChild, subtree);
                subtree2 = new Node23<Key>(rightKey, rightValue, subtree2, rightChild);
                key_carry = n.key_carry;
                value_carry = n.value_carry;
                n.key_carry = null;
            }
            else middleChild = n;
        }else if(k.compareTo(leftKey) < 0){
            // insert left
            Node23 n = put(leftChild, k, v);
            // carry is not null => continue carry to the parent because this is a 3Node
            // also split the Node
            if (n.key_carry != null){
                subtree = new Node23(n.key_carry, n.value_carry, subtree, subtree2);
                subtree2 = new Node23<Key>(rightKey, rightValue, middleChild, rightChild);
                key_carry = leftKey;
                value_carry = leftValue;
                n.key_carry = null;
            }
            else leftChild = n;
        }else{
            // insert right
            Node23 n = put(rightChild, k, v);
            // carry is not null => continue carry to the parent because this is a 3Node
            // also split the Node
            if (n.key_carry != null) {
                subtree2 = new Node23(n.key_carry,n.value_carry, subtree, subtree2);
                subtree = new Node23<Key>(leftKey,leftValue, leftChild, middleChild);
                key_carry = rightKey;
                value_carry = rightValue;
                n.key_carry = null;
            }
            else rightChild = n;
        }
        return this;
    }


    public Node23 put2Node(Key k, Integer v){
        if (leftKey.compareTo(k)==0){
            leftValue = v;
            return this;
        }
        if (isLeaf()) return new Node23<Key>(leftKey, k, leftValue, v, null, null, null);
        // insert left
        if (k.compareTo(leftKey) < 0){
            Node23 n = put(leftChild, k, v);
            // if we have a carry => create a 3Node with the carried key and stop carrying
            if (n.key_carry != null) return new Node23(leftKey, n.key_carry, leftValue, n.value_carry, subtree, subtree2, middleChild);
            else leftChild = n;
        }else{
            // insert right
            Node23 n = put(middleChild, k, v);
            // if we have a carry => create a 3Node with the carried key and stop carrying
            if (n.key_carry != null) return new Node23(leftKey, n.key_carry, leftValue, n.value_carry, leftChild, subtree, subtree2);
            else middleChild = n;
        }
        return this;
    }
    // END STRIP



}
