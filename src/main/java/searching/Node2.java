package solution;

public class Node2 extends Node{

    Integer key;
    Double value;
    Node left, right;

    Node2(Integer k, Double v, Node l, Node r){
        key = k; value = v; left = l; right = r;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public Node put(Integer k, Double v){
        if (isLeaf()) return new Node3(key, k, value, v, null, null, null);
        if (k.compareTo(key) < 0){
            Node n = left.put(k, v);
            Node2 temp = Tree23.carry;
            if (temp != null) return new Node3(key, temp.key, value, temp.value, Tree23.temp, Tree23.temp2, right);
            else left = n;

        }else{
            Node n = right.put(k, v);
            Node2 temp = Tree23.carry;
            if (temp != null){
                Node ret = new Node3(key, temp.key, value, temp.value, left, Tree23.temp, Tree23.temp2);
                Tree23.carry = null;
                return ret;
            }
            else right = n;
        }
        return this;
    }

    @Override
    public int isBalanced() {
        if (isLeaf()) return 1;
        int l = left.isBalanced();
        int r = right.isBalanced();
        if (l == -1 || r != l) return -1;
        return l + 1;
    }

    @Override
    public boolean isBst(int lower, int upper) {
        if (isLeaf()) return true;
        if (key < lower || key > upper) return false;
        return left.isBst(lower, key) && right.isBst(key, upper);
    }

    @Override
    public Double search(Integer k) {
        if (key.equals(k)) return value;
        if (k.compareTo(key) < 0) return Node.search(left, k);
        else return Node.search(right, k);
    }
}
