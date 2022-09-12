package strings;

import utils.HuffmanNode;

// BEGIN STRIP
import java.util.PriorityQueue;
// END STRIP

/**
 * This class is used to construct a Huffman trie from frequencies of letters (in unicode or ASCII).
 * As a reminder, in an Huffman trie nodes are weighted (see the `utils.HuffmanNode` class) by
 * the frequencies of the character (if lead node) or the sum of the frequencies of its children
 * (if internal node).
 * For example, let us assume that we have the following letters with their associated frequencies:
 *  (t, 1), (m, 2), (z, 3), (a, 4), (g, 5)
 *
 *  The the following Huffman trie can be constructed
 *
 *
 *                      (_, 15)
 *                         |
 *         (_, 9) -------------------- (_, 6)
 *           |                           |
 *  (a, 4)------(g, 5)        (z, 3)----------(_, 3)
 *                                              |
 *                                     (t, 1)------(m, 2)
 *
 * In practice you are given an array of frequencies for each of the 256 ASCII code or 65536 unicode characters.
 * The goal is to construct the Huffman trie from this array of frequencies.
 */
public class Huffman {

    /**
     * Constructs an Huffman trie for the frequencies of the characters given in arguments.
     * The character are implicitely defined by the `freq` array (ranging from 0 to freq.length -1)
     *
     * @param freq the frequencies of the characters
     */
    public static HuffmanNode buildTrie(int [] freq) {
        // BEGIN STRIP
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
        for (int i = 0; i < freq.length; i++) {
            queue.add(new HuffmanNode(i, freq[i], null, null));
        }
        while (queue.size() != 1) {
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();
            int nodeFreq = left.getFrequency() + right.getFrequency();
            queue.add(new HuffmanNode(-1, nodeFreq, left, right));
        }
        return queue.poll();
        // END STRIP
        // STUDENT return null;
    }
}
