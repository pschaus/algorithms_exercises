package searching;

/**
 * In this exercise, we ask you to implement the put method of a 23Tree.
 * To do so, we recommend you to divide the task in 2 different functions : put2Node and put3Node.
 * The first one can be used to put a value in a 2Node and the second one in a 3Node.
 *
 * We strongly recommend you to divide this task to make the implementation easier.
 * We also give you 3 static variables (accessible everywhere in this file), which
 * you can use to store intermediate result of the put method
 * (for example if you must insert a key higher in tree after manipulations).
 * The two other variables can be used to store temporary subtrees.
 *
 * Also don't forget that if a key is taken up to the root and the root is already
 * a 3Node, you must create a new level in the tree
 */
public class Tree23 {

    Node23 root;

    public Tree23(){
        root = null;
    }

    static Node23 carry;
    static Node23 subtree, subtree2;

    public Node23 put(Node23 n, Integer key, Double value){
        if (n == null) return new Node23(key, value, null, null);
        Node23 ret = n.put(key, value);
        if (carry != null) {
            carry.left = subtree;
            carry.middle = subtree2;
            return carry;
        }
        return ret;
    }

    public void put(Integer key, Double value){
        // BEGIN STRIP
        carry = null;
        root = put(root, key, value);
        // END STRIP
        // TODO
        // STUDENT return;

    }

    public Double search(Integer k){
        if (root == null) return null;
        Double temp = root.search(k);
        return temp;
    }

    public boolean isBalanced(){
        if (root == null) return true;
        return root.isBalanced() >= 0;
    }

    public boolean isBst(){
        if (root == null) return true;
        return root.isBst(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
}

class Node23{

    Integer k1, k2;  // k1 < k2
    Double v1, v2;
    Node23 left, middle, right;
    // when we have a 2Node => only left and middle are used and k2 is set to INF
    // thus any key greater than k1 will be less than k2 (used for search for example)

    Node23(Integer k, Double v, Node23 left, Node23 right){
        this.k1 = k;
        this.v1 = v;
        this.left = left;
        this.middle = right; // is comprised between [k1, inf[
        this.k2 = Integer.MAX_VALUE;
        this.right = null;
    }

    Node23(Integer k1, Integer k2, Double v1, Double v2, Node23 left, Node23 middle, Node23 right){
        this.k1 = Math.min(k1, k2);
        this.k2 = Math.max(k1, k2);
        this.v1 = k1<k2 ? v1 : v2;
        this.v2 = k1<k2 ? v2 : v1;
        this.left = left;
        this.right = right;
        this.middle = middle;
    }

    public boolean is2Node(){ return k2==Integer.MAX_VALUE; }
    public boolean is3Node(){ return !is2Node(); }

    /**
     *
     * @param k : a key to compare
     * @return 0 if k1 < k < k2
     *        -1 if k < k1
     *         1 if k > k2
     */
    public int compare(Integer k){
        if (k.compareTo(k1) > 0 && k.compareTo(k2) < 0) return 0;
        if (k.compareTo(k1) < 0 ) return -1;
        return 1;
    }

    public boolean isLeaf() {
        return left == null && middle == null && right == null;
    }

    public Double search(Node23 n, Integer k) {
        if (n==null) return null;
        if (n.k1.equals(k)) return n.v1;
        if (n.k2.equals(k)) return n.v2;

        if (k.compareTo(n.k1)>0 && k.compareTo(n.k2) <0) return search(n.middle, k);
        else if (k.compareTo(n.k1) < 0) return search(n.left, k);
        else return search(n.right, k);
    }

    public Double search(Integer k){
        Double temp =  search(this, k);
        return temp;
    }

    public Node23 put(Integer k, Double v) {
        // BEGIN STRIP
        if (is3Node()) return put3Node(k, v);
        else  return put2Node(k, v);
        // END STRIP
        // TODO
        // STUDENT return null;

    }

    public Node23 put3Node(Integer k, Double v){
        // BEGIN STRIP
        int cmp = compare(k);
        if (isLeaf()){
            // create an initial carry
            if (cmp == 0){
                // the new key is the carry
                Tree23.subtree = new Node23(k1, v1, null, null);
                Tree23.subtree2 = new Node23(k2, v2, null, null);
                Tree23.carry = new Node23(k, v, null, null);
            }else if (cmp < 0){
                // the left key is the carry
                Tree23.subtree = new Node23(k, v, null, null);
                Tree23.subtree2 = new Node23(k2, v2, null, null);
                Tree23.carry = new Node23(k1, v1, null, null);
            }else {
                // the right key is the carry
                Tree23.subtree = new Node23(k1, v1, null, null);
                Tree23.subtree2 = new Node23(k, v, null, null);
                Tree23.carry = new Node23(k2, v2, null, null);
            }
            return null;
        }
        // insert middle
        if (cmp == 0){
            Node23 n = middle.put(k, v);
            // carry is not null => continue carry to the parent
            if (Tree23.carry != null){
                Tree23.subtree = new Node23(k1, v1, Tree23.subtree, Tree23.subtree2);
                Tree23.subtree2 = new Node23(k2, v2, middle, right);
                Tree23.carry = new Node23(Tree23.carry.k1, Tree23.carry.v1, null, null);
                return null;
            }
            else middle = n;
        }else if(cmp < 0){
            // insert left
            Node23 n = left.put(k, v);
            // carry is not null => continue carry to the parent
            if (Tree23.carry != null){
                Tree23.subtree = new Node23(Tree23.carry.k1, Tree23.carry.v1, Tree23.subtree, Tree23.subtree2);
                Tree23.subtree2 = new Node23(k2, v2, middle, right);
                Tree23.carry = new Node23(k1,v1, null, null);
                return null;
            }
            else left = n;
        }else{
            // insert right
            Node23 n = right.put(k, v);
            // carry is not null => continue carry to the parent
            if (Tree23.carry != null) {
                Tree23.subtree2 = new Node23(Tree23.carry.k1,Tree23.carry.v1, Tree23.subtree, Tree23.subtree2);
                Tree23.subtree = new Node23(k1,v1, left, middle);
                Tree23.carry = new Node23(k2, v2, null, null);
                return null;
            }
            else right = n;
        }
        return this;
        // END STRIP
        // TODO
        // STUDENT return null;
    }

    public Node23 put2Node(Integer k, Double v){
        // BEGIN STRIP
        if (isLeaf()) return new Node23(k1, k, v1, v, null, null, null);
        // insert left
        if (k.compareTo(k1) < 0){
            Node23 n = left.put(k, v);
            Node23 temp = Tree23.carry;
            // if we have a carry => create a 3Node
            if (temp != null){
                Node23 ret = new Node23(k1, temp.k1, v1, temp.v1, Tree23.subtree, Tree23.subtree2, middle);
                Tree23.carry = null;
                return ret;
            }
            else left = n;
        }else{
            // insert right
            Node23 n = middle.put(k, v);
            Node23 temp = Tree23.carry;
            // if we have a carry => create a 3Node
            if (temp != null){
                Node23 ret = new Node23(k1, temp.k1, v1, temp.v1, left, Tree23.subtree, Tree23.subtree2);
                Tree23.carry = null;
                return ret;
            }
            else middle = n;
        }
        return this;
        // END STRIP
        // TODO
        // STUDENT return null;
    }

    public int isBalanced() {
        if (isLeaf()) return 1;
        int m = middle.isBalanced();
        int l = left.isBalanced();
        int r = l;
        if (is3Node()) r = right.isBalanced();
        if (l < 0 || l != r || r != m) return -1;
        return l + 1;
    }

    public boolean isBst2Node(int lower, int upper){
        if (isLeaf()) return true;
        if (k1 < lower || k1>upper) return false;
        return left.isBst(lower, k1) && middle.isBst(k1, k2);
    }

    public boolean isBst3Node(int lower, int upper) {
        if (isLeaf()) return true;
        if (k1 < lower || k2 < lower || k1>upper || k2>upper || k1>k2) return false;
        return left.isBst(lower, k1) && middle.isBst(k1, k2) && right.isBst(k2, upper);
    }

    public boolean isBst(int lower, int upper) {
        if (is3Node()) return isBst3Node(lower, upper);
        else return isBst2Node(lower, upper);
    }

}
