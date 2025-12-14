package strings;

import java.util.ArrayList;

/**
 * You are a secret agent and your just received a new mission. The mission was sent to you as a Huffman encoded
 * message with the Huffman tree to decode it. BUT, to keep the mission order secret, your boss messed the Huffman tree
 * by exchanging some leaves of the tree.
 *
 * Your task is to repair the Huffman tree and decode the secret mission.
 *
 * Each node in the tree has a symbol and a frequency. The frequency and the symbol of a non leaf node are the sum of
 * the frequencies and symbols of their children.
 * Here is an example of a messed tree. The leaves (c, 3) and (e, 2) are exchanged.
 *
 *                      (abcde, 15)
 *                         |
 *         (ab, 9) -------------------- (cde, 6)
 *           |                           |
 *  (a, 4)------(b, 5)        (e, 2)----------(de, 3)
 *                                              |
 *                                     (d, 1)------(c, 3)
 */
public class HuffmanDecoder {


    /**
     * Decoder for a Huffman encoding. Should return the decoded string.
     * You should return an empty string if the encoded string is empty.
     *
     * @param encoded is the encoded message to decode
     * @param root the root of the Huffman tree
     * @return the decoded message
     */
    public static String decode(String encoded, Node root) {
        // BEGIN STRIP
        if (encoded.isEmpty()) return "";
        char[] chararray = encoded.toCharArray();
        if (chararray.length == 1) return root.symbol;

        StringBuilder decoded = new StringBuilder();
        repairTree(root);
        Node current = root;

        for (char bit : chararray) {
            if (bit == '0') current = current.left;
            else current = current.right;

            if (current.left == null && current.right == null) {
                decoded.append(current.symbol);
                current = root;
            }
        }
        return decoded.toString();
        // END STRIP
        // STUDENT return "";
    }



    public static boolean IsLeaf(Node node) {
        /**
         * Returns true if the node is a leaf, or else returns false
         */
        // BEGIN STRIP
        return node.left == null && node.right == null;
    }

    public static Node swapleaves(HuffmanDecoder.Node current, HuffmanDecoder.Node node2){
        /**
         * Swap the two leaves current and node2, and returns node2
         */
        HuffmanDecoder.Node parent1 = current.parent;
        HuffmanDecoder.Node parent2 = node2.parent;

        if (parent1.left.equals(current)){
            if (parent2.left.equals(node2)){
                parent1.left = node2;
                node2.parent = parent1;
                parent2.left = current;
                current.parent = parent2;
            }else{
                parent1.left = node2;
                node2.parent = parent1;
                parent2.right = current;
                current.parent = parent2;
            }
        }else{
            if (parent2.left.equals(node2)){
                parent1.right = node2;
                node2.parent = parent1;
                parent2.left = current;
                current.parent = parent2;
            }else{
                parent1.right = node2;
                node2.parent = parent1;
                parent2.right = current;
                current.parent = parent2;
            }
        }
        return node2;
    }

    public static void GetLeaves(Node root, ArrayList<Node> leaves) {
        /**
         * Fill an ArrayList with all the leaves
         */
        if (root.left == null && root.right == null) {
            leaves.add(root);
            return;
        }
        GetLeaves(root.left, leaves);
        GetLeaves(root.right, leaves);
        // END STRIP
        // STUDENT return false;
    }

    public static void repairTree(Node root) {
        /**
         * Sort the Huffman tree so it can be used to decode an encoded message
         */
        // BEGIN STRIP
        ArrayList<Node> leaves = new ArrayList<>();
        ArrayList<Node> parents = new ArrayList<>();
        GetLeaves(root, leaves);
        for (Node leaf : leaves) if(!parents.contains(leaf.parent)) parents.add(leaf.parent);


        for (Node parent : parents){
            if (IsLeaf(parent.left) && parent.symbol.charAt(0) != parent.left.symbol.charAt(0)){
                for (Node leaf : leaves){
                    if (parent.symbol.charAt(0) == leaf.symbol.charAt(0)) swapleaves(parent.left, leaf);
                }
            }
            if (IsLeaf(parent.right) && parent.symbol.charAt(parent.symbol.length()-1) != parent.right.symbol.charAt(0)){
                for (Node leaf : leaves){
                    if (parent.symbol.charAt(parent.symbol.length()-1) == leaf.symbol.charAt(0)) swapleaves(parent.right, leaf);
                }
            }
        }

        // END STRIP
    }


    public static class Node implements Comparable<Node> {
        String symbol;
        int freq;
        Node left;
        Node right;
        Node parent;

        public Node(String symbol, int frequency, Node left, Node right, Node parent) {
            this.symbol = symbol;
            this.freq = frequency;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        @Override
        public int compareTo(Node other) {
            if (this.freq == other.freq) return this.symbol.compareTo(other.symbol);
            return this.freq - other.freq;
        }
    }

}