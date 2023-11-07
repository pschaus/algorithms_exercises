package strings;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
// END STRIP

/**
 * Implement the class WordCounter that counts the number of occurrences
 * of each word in a given piece of text.
 * Feel free to use existing java classes.
 */
public class WordCounter implements Iterable<String> {

    // BEGIN STRIP
    private Map<String, Integer> wordCounts;
    // END STRIP

    public WordCounter() {
        // BEGIN STRIP
        wordCounts = new TreeMap<>();
        // END STRIP
    }

    /**
     * Add the word so that the counter of the word is increased by 1
     */
    public void addWord(String word) {
        // BEGIN STRIP
        if (wordCounts.containsKey(word)) {
            wordCounts.put(word, wordCounts.get(word) + 1);
        } else {
            wordCounts.put(word, 1);
        }
        // END STRIP
    }

    /**
     * Return the number of times the word has been added so far
     */
    public int getCount(String word) {
        // STUDENT return -2;
        // BEGIN STRIP
        if (wordCounts.containsKey(word)) {
            return wordCounts.get(word);
        } else {
            return 0;
        }
        // END STRIP
    }

    // iterate over the words in ascending lexicographical order
    @Override
    public Iterator<String> iterator() {
        // STUDENT return null;
        // BEGIN STRIP
        return wordCounts.keySet().iterator();
        // END STRIP
    }
}
