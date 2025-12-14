package strings;

import java.util.*;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;


public class HuffmanDecoderGenerator {
    private static Hashtable<Character, String> codes;

    public static void main(String [] args) throws Exception{
        HashMap<Integer, String> sentences = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader("data/strings.HuffmanDecoder/LoremIpsum.txt"));

        int LignNumber = 0;
        String ligne;
        while ((ligne = br.readLine()) != null) {
            sentences.put(LignNumber, ligne);
            LignNumber++;
        }
        br.close();

        for (int instance_id = 0; instance_id < LignNumber; instance_id++) {
            String sentence = sentences.get(instance_id);
            HuffmanTree(sentence);
            String encodedsentence = encode(sentence);
            String instance_file = "data/strings.HuffmanDecoder/in_" + instance_id;
            writeInstance(instance_file, sentence, encodedsentence);
        }
    }

    public static void GetLeaves(HuffmanDecoder.Node root, ArrayList<HuffmanDecoder.Node> leaves) {
        /**
         * Fill an ArrayList with all the leaves
         */
        if (root.left == null && root.right == null) {
            leaves.add(root);
            return;
        }
        GetLeaves(root.left, leaves);
        GetLeaves(root.right, leaves);

    }

    public static void swap(HuffmanDecoder.Node node1, HuffmanDecoder.Node node2){
        HuffmanDecoder.Node parent1 = node1.parent;
        HuffmanDecoder.Node parent2 = node2.parent;

        if (parent1.left.equals(node1)){
            if (parent2.left.equals(node2)){
                parent1.left = node2;
                node2.parent = parent1;
                parent2.left = node1;
                node1.parent = parent2;
            }else{
                parent1.left = node2;
                node2.parent = parent1;
                parent2.right = node1;
                node1.parent = parent2;
            }
        }else{
            if (parent2.left.equals(node2)){
                parent1.right = node2;
                node2.parent = parent1;
                parent2.left = node1;
                node1.parent = parent2;
            }else{
                parent1.right = node2;
                node2.parent = parent1;
                parent2.right = node1;
                node1.parent = parent2;
            }
        }
    }

    public static void MessTree(HuffmanDecoder.Node root) {
        /**
         * Builds a Huffman tree and return the encoded sentence s
         */
        ArrayList<HuffmanDecoder.Node> leaves = new ArrayList<>();
        GetLeaves(root, leaves);
        int nbLeaves = leaves.size();

        Random r = new Random();
        if (r.nextInt(10) != 0){
            int nbShuffle = r.nextInt(nbLeaves-2)+2;
            for (int i = 0; i < nbShuffle; i++) {
                HuffmanDecoder.Node leaf1 = leaves.get(r.nextInt(nbLeaves));
                HuffmanDecoder.Node leaf2 = leaves.get(r.nextInt(nbLeaves));
                swap(leaf1, leaf2);

            }
        }
    }

    public static HuffmanDecoder.Node HuffmanTree(String s) {
        /**
         * Builds a Huffman tree and return the encoded sentence s
         */
        Hashtable<Character,Integer> uniquesymbolfreq = new Hashtable<>();

        for (char c : s.toCharArray()) {
            uniquesymbolfreq.put(c, uniquesymbolfreq.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<HuffmanDecoder.Node> pq = new PriorityQueue<>();
        for (Character c : uniquesymbolfreq.keySet()) {
            pq.add(new HuffmanDecoder.Node(String.valueOf(c), uniquesymbolfreq.get(c), null, null, null));
        }

        while (pq.size() > 1) {
            HuffmanDecoder.Node left = pq.poll();
            HuffmanDecoder.Node right = pq.poll();
            HuffmanDecoder.Node parent = new HuffmanDecoder.Node(left.symbol + right.symbol, left.freq + right.freq, left, right, null);
            left.parent = parent;
            right.parent = parent;
            pq.add(parent);
        }

        HuffmanDecoder.Node root = pq.poll();
        codes = new Hashtable<>();
        generateCodes(root, "");

        return root;
    }

    private static void generateCodes(HuffmanDecoder.Node node, String code) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            codes.put(node.symbol.charAt(0), code.isEmpty() ? "0" : code);
            return;
        }
        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    public static String encode(String s) {
        StringBuilder encoded = new StringBuilder();
        for (char c : s.toCharArray()) {
            encoded.append(codes.get(c));
        }
        return encoded.toString();
    }


    private static void writeInstance(String file, String sentence, String encodedsentence) {
        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(file));
            p.println(sentence);
            p.println(encodedsentence);
            p.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
