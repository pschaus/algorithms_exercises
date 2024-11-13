package strings;

// BEGIN STRIP
import java.util.Iterator;
import static java.lang.Long.bitCount;
// END STRIP

/**
 * A Bitset is a data structure that efficiently represents a fixed-size sequence of bits (binary values of 0 or 1) in memory.
 * Instead of using an array of boolean values, it uses an array of long integers to store bits compactly.
 *  Each long (64 bits) can hold 64 individual bits, which allows for space-efficient storage.
 *
 *  Your task consists of :
 *  1. Implementing the initialization of the Bitset, calculating the necessary number of long words needed to represent the desired number of bits.
 *  2. Implementing helpers methods to set, unset and count bits in the bitsets
 *
 */
public class Bitset {

    final long [] words; // Internal array storing the current representation

    int n;
    /**
     * Instantiate a new bitset able to hold n elements
     * @param n the number of bits to store
     */
    public Bitset(int n) {
        // TODO
        //BEGIN STRIP
        this.n = n;
        int nbWords = (n + 63) >>> 6; // divided by 64
        words = new long[nbWords];
        // END STRIP
    }

    /**
     * Set the ith bit
     * @param i the bit to set
     */
    public void set(int i) {
        // TODO
        // BEGIN STRIP
        words[i >>> 6] |= 1L << i;
        // END STRIP
    }

    /**
     * Clear the ith bit
     * @param i the bit to clear
     */
    public void clear(int i) {
        // TODO
        // BEGIN STRIP
        words[i >>> 6] &= ~(1L << i);
        // END STRIP
    }

    /**
     * Check if the ith bit is set in the structure
     * @param i the bit to check
     * @return true if the bit is set and false otherwise.
     */
    public boolean contains(int i) {
        // TODO
        // STUDENT return false;
        // BEGIN STRIP
        return (words[i >>> 6] & (1L << i)) != 0;
        // END STRIP
    }

    /**
     * Returns the number of bits set in the structure
     * @return the total number of set bits
     */
    public int count() {
        int count = 0;
        for (long word : words) {
            if (word != 0L) {
                count += bitCount(word);
            }
        }
        return count;
    }

    /**
     *
     * @return the number of words used to store the n bits
     */
    public int getNbWords() {
        return words.length;
    }

    public Iterator<Integer> iterator() {
     return null;
    }

}
