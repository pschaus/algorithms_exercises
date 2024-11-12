package strings;

// BEGIN STRIP
import java.util.LinkedList;
import java.util.Queue;
// END STRIP

/**
 * The AutoCompleter class implements a trie data structure to provide auto-completion functionality.
 * It uses a Node class to represent each node in the trie, where each node can have up to 26 children
 * (one for each lowercase letter). The trie is constructed from a given dictionary of words,
 * and the autoComplete method can be used to find the first word in the dictionary that starts with a given prefix.
 *
 * Example:
 *     // Initialize AutoCompleter with a dictionary
 *     String[] dictionary = {"bifold", "bind","cat", "car", "dot", "dog", "bike", "bill",  };
 *     AutoCompleter completer = new AutoCompleter(dictionary);
 *
 *     // Auto-complete examples
 *     System.out.println(completer.complete("ca")); // Output: "car" since it competes with cat but is lexicographically smaller
 *     System.out.println(completer.complete("do")); // Output: "dog" since it competes with dot but is lexicographically smaller
 *     System.out.println(completer.complete("bi")); // Output: "bike" since it competes bill and bind but is lexicographically smaller (bifold is lexicographically smaller but longer)
 *
 */
public class AutoCompleter {
    private static final int R = 26;
    private Node root = new Node();

    /**
     * Represents a single node in the trie. Each node contains an array of child nodes
     * (one for each letter of the alphabet) and a flag is_key indicating if it represents the end of a word.
     */
    private static class Node {
        boolean isKey = false;
        private Node[] next = new Node[R];
    }

    /**
     * Converts a lowercase letter to an index in the range 0-25.
     *
     * @param c the character to convert
     * @return the corresponding index
     */
    private static int charToIndex(char c) {
        return ((int) c) - 97;
    }

    /**
     * Converts an index in the range 0-25 to a lowercase letter.
     *
     * @param i the index to convert
     * @return the corresponding character
     */
    private static char indexToChar(int i) {
        return (char) (i + 97);
    }

    public AutoCompleter(String[] dictionary) {
        for (String word : dictionary) {
            this.insert(word);
        }
    }

    /**
     * Inserts a word into the trie by calling a recursive helper method.
     *
     * @param word the word to insert into the trie
     */
    private void insert(String word) {
        this.root = insert(root, word, 0);
    }

    /**
     * Recursive helper method that inserts characters of a word into the trie.
     *
     * @param node the current node in the trie
     * @param word the word to insert
     * @param d    the current depth (or index) in the word being processed
     * @return the updated node after insertion
     */
    private Node insert(Node node, String word, int d) {
        if (node == null) {
            node = new Node();
        }
        if (d == word.length()) {
            node.isKey = true;
            return node;
        }
        char c = word.charAt(d);
        node.next[charToIndex(c)] = insert(node.next[charToIndex(c)], word, d+1);
        return node;
    }

    // BEGIN STRIP
    private static class StringNode {
        String prefix;
        Node node;

        StringNode(String prefix, Node node) {
            this.prefix = prefix;
            this.node = node;
        }
    }

    private Node findLastNode(String word) {
        Node current = this.root;
        for (int i = 0; i < word.length(); i++) {
            if (current.next[charToIndex(word.charAt(i))] != null) {
                current = current.next[charToIndex(word.charAt(i))];
            } else {
                return null;
            }
        }
        return current;
    }
    // END STRIP

    /**
     * Returns the shortest (number of letters) word in the dictionary
     * that starts with the given prefix.
     * In case of ties, the lexicographically smallest word is returned.
     *
     * Hint: Use a queue to perform a breadth-first search starting from the last node of the prefix.
     * If you don't know what a breadth-first search is,
     * you can look it here https://en.wikipedia.org/wiki/Breadth-first_search
     *
     * @param prefix the prefix to search for
     * @return the shortest word in the dictionary that starts with the prefix, or null if no such word is found.
     *         in case of ties, the lexicographically smallest word is returned.
     */
    public String complete(String prefix) {
        // TODO
        // BEGIN STRIP
        Node node = this.findLastNode(prefix);
        if (node == null) {  // Cannot find prefix in trie
            return null;
        }
        if (node.isKey) {  // The prefix is a key in the trie
            return prefix;
        }
        Queue<StringNode> q = new LinkedList<>();
        q.add(new StringNode(prefix, node));
        while (!q.isEmpty()) {
            StringNode current = q.poll();
            if (current.node.isKey) {
                return current.prefix;
            }
            for (int i = 0; i < R; i++) {
                if (current.node.next[i] != null) {
                    q.add(new StringNode(current.prefix + indexToChar(i), current.node.next[i]));
                }
            }
        }
        return null;
        // END STRIP
        // STUDENT return null;
    }
}
