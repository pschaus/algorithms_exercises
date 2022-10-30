package searching;

public class Tree23 {

    Node root;

    public Tree23(){
        root = null;
    }

    static Node2 carry;
    static Node temp, temp2;

    public void put(Integer key, Double value){
        //TODO
        carry = null;
        root = Node.put(root, key, value);
    }

    public Double search(Integer k){
        return Node.search(root, k);
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
