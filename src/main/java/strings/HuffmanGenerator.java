package strings;

import java.util.Random;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class HuffmanGenerator {

    public static void main(String [] args) {
        Random r = new Random(6539744);
        for (int instance_id = 0; instance_id < 100; instance_id++) {
            int [] frequencies = getRandomFrequencies(256, 100, r);
            HuffmanNode trie = Huffman.buildTrie(frequencies);
            int wepl = getWeightedExternalPathLength(trie);
            String instance_file = "data/strings.Huffman/in_" + instance_id;
            writeInstance(instance_file, frequencies, wepl);
        }
    }

    private static int[] getRandomFrequencies(int size, int limit, Random r) {
        int [] frequencies = new int[size];
        for (int i = 0; i < size; i++) {
            frequencies[i] = r.nextInt(limit);
        }
        return frequencies;
    }

    private static int getWeightedExternalPathLength(HuffmanNode root) {
        return getWeightedExternalPathLength(root, 0);
    }

    private static int getWeightedExternalPathLength(HuffmanNode root, int depth) {
        if (root.isLeaf()) {
            return root.getFrequency()*depth;
        }
        return getWeightedExternalPathLength(root.getLeft(), depth+1)+getWeightedExternalPathLength(root.getRight(), depth+1);
    }

    private static void writeInstance(String file, int [] input, int wepl) {
        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(file));
            p.println(input.length);
            for (int i : input) {
                p.println(i);
            }
            p.println(wepl);
            p.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
