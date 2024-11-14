package searching;

// BEGIN STRIP
import static java.lang.Long.bitCount;
// END STRIP

/**
 * A Bitset is an efficient alternative to using a Hashset
 * when you need to store a dense set of integers.
 * Instead of using an array of boolean to represent
 * the inclusion or exclusion of the value at the corresponding index,
 * it uses an array of long (called words) to store bits.
 * Each word encode 64 individual bits.
 *
 * Your task is to implement:
 * 1. The initialization of the Bitset,
 *      calculating the required number of long words to store the specified number of bits.
 * 2. The `getNbWords()` method should return the total number of long words used.
 * 3. Helper methods to set, clear, and count bits within the Bitset.
 *
 * Example:
 * Suppose we create a Bitset with a capacity of 8 bits,
 * with each "word" storing up to 4 bits (for simplicity in illustration).
 * The Bitset would initially be represented internally as [0000, 0000]. The following operations would yield:
 *  - getNbWords() : Returns 2 (total number of long words used).
 *  - set(4)       : Updates state to [0000, 0001] (sets the first bit in the second word).
 *  - set(2)       : Updates state to [0100, 0001] (sets the third bit in the first word).
 *  - set(7)       : Updates state to [0100, 1001] (sets the fourth bit in the second word).
 *  - count()      : Returns 3 (total number of bits set).
 *  - contains(2)  : Returns true.
 *  - clear(4)     : Updates state to [0100, 1000] (clears the first bit in the second word).
 */
public class Bitset {

    // BEGIN STRIP
    final long [] words; // Internal array storing the current representation
    // END STRIP

    /**
     * Instantiate a new bitset able to hold n elements
     * @param n the number of bits to store
     */
    public Bitset(int n) {
        // TODO
        //BEGIN STRIP
        int nbWords = (n + 63) >>> 6; // divided by 64
        words = new long[nbWords];
        // END STRIP
    }

    /**
     *
     * @return the number of words used to store the n bits
     */
    public int getNbWords() {
        // TODO
        // STUDENT return 0;
        // BEGIN STRIP
        return words.length;
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
        // TODO
        // STUDENT return 0;
        // BEGIN STRIP
        int count = 0;
        for (long word : words) {
            if (word != 0L) {
                count += bitCount(word);
            }
        }
        return count;
        // END STRIP
    }

}
