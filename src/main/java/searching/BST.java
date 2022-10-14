package searching;
import java.util.ArrayList;
import java.util.Arrays;

public class BST {
    /* 
    * We ask you to implement the reconstruction of a BST 
    * (binary search tree, with the well known properties on the ways keys are stored)
    * in linear time starting from a preorder output traversal of the BST. 
    * To simplify things, the BST Node's only contains integer key but no associated value. 
    * For example, for [10,5,7,14,12] the tree should be like this :
    *                             10
    *                              |
    *                  5 ------------------------ 14
    *                  |                          |
    *                  ------ 7           12 ------
    *                                 
    *                                     
    */

    protected Node root;

    public BST() {

    }

    public BST(Node r) {
        root = r;
    }

    private Node put(Node x, int key) {
        if (x == null) return new Node(null, null, key);
        if (key < x.key) x.left = put(x.left, key);
        else if (key > x.key) x.right = put(x.right, key);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void put(int key) {
        root = put(root, key);
    }

    /**
     * Reconstruct the BST based on a valid preorder serialization
     * For instance for [10,5,7,14,12] you should have root equal to
     * new Node(new Node(null,new Node(null,null,7,1),5,2),new Node(new Node(null,null,12,1),null,14,2),10,5)
     * @param preOrderInput is a valid output obtained using preorderWrite
     */
    public  BST(int [] preOrderInput) {
        // no need to change it but you can if you want
        root = preorderRead(preOrderInput,0,Integer.MIN_VALUE,Integer.MAX_VALUE);
    }

    public Node preorderRead(int [] preOrderInput, int i, int min, int max) {
        // !!!!!!! TODO !!!!!!!!
        // STUDENT return new Node(null,null,preOrderInput[0]);
        // BEGIN STRIP
        if(i >= preOrderInput.length)
            return null;
        if(preOrderInput[i] > max || preOrderInput[i] < min)
            return null;
        Node left = preorderRead(preOrderInput, i+1, min, preOrderInput[i]);
        int a = 0;
        if(left != null)
            a += left.size;
        Node right = preorderRead(preOrderInput, i+1+a, preOrderInput[i], max);
        return new Node(left, right, preOrderInput[i]);
        // END STRIP
        
    }

    public int[] preorderWrite() {
        return root == null ? new int[0] : root.preOrder();
    }

    protected static int size(Node x) {
        return x == null ? 0 : x.size;
    }

    @Override
    public String toString() {
        return root == null ? "empty" : root.toString();
    }

    @Override
    public boolean equals(Object obj) {
        // this is the test method used to test that the constructor based on preorder is working
        // don't change it
        if (obj != null) {
            BST other = ((BST) obj);
            if (root == null) return other.root == null;
            else return root.equals(other.root);
        } else {
            return false;
        }
    }

    public static class Node {

        public Node left;
        public Node right;
        public int key;
        public int size;
    
        public Node(Node left, Node right, int key) {
            this.left = left;
            this.right = right;
            this.key = key;
            this.size = (left == null ? 0 : left.size) + (right == null ? 0 : right.size) + 1;
        }
    
        @Override
        public String toString() {
            return "new Node("+left+","+right+","+key+")";
        }
    
        @Override
        public boolean equals(Object obj) {
            if (obj != null) {
                Node other = ((Node) obj);
                if (other.key == key && other.size == size) {
                    boolean leftEqual = left != null ? left.equals(other.left) : other.left == null;
                    return leftEqual && (right != null ? right.equals(other.right) : other.right == null);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    
        public int [] preOrder() {
            ArrayList<Integer> acc = new ArrayList<>();
            preOrder(acc);
            return Arrays.stream(acc.toArray(new Integer[]{})).mapToInt(i -> i).toArray();
        }
    
        private void preOrder(ArrayList<Integer> acc) {
            acc.add(key);
            if (left != null) left.preOrder(acc);
            if (right != null) right.preOrder(acc);
        }
    
    }
}
