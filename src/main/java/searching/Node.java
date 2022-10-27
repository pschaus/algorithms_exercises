package solution;

public abstract class Node {

    public abstract boolean isLeaf();

    public boolean is2Node(){ return this instanceof Node2;}
    public boolean is3Node(){ return this instanceof Node3;}

    public abstract Double search(Integer k);
    public abstract Node put(Integer k, Double v);
    public abstract int isBalanced();

    public abstract boolean isBst(int lower, int upper);
    public static Double search(Node n, Integer k){
        if (n==null) return null;
        return n.search(k);
    }


    public static Node put(Node n, Integer k, Double v){
        if (n==null) return new Node2(k, v, null, null);
        Node temp = n.put(k, v);
        if (Tree23.carry != null) return new Node2(Tree23.carry.key, Tree23.carry.value, Tree23.temp, Tree23.temp2);
        return temp;
    }
}
