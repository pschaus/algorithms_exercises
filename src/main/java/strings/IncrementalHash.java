package strings;

/**
 * Author: Pierre Schaus
 *
 * You have to implement an incremental hash function on a string represented as an array of char's.
 * This is the hash function that is used by the Rabin-Karp algorithm.
 *
 * We advise you to debug your code using the first two small unit-tests provided with string of length 2 and 3
 */
public class IncrementalHash {


    public static final int R = 31;
    private int M;
    private int RM;
    private int Q;

    /**
     *
     * @param Q is the modulo to apply
     * @param M is the length of the words to hash
     */
    public IncrementalHash(int Q, int M) {
        assert (M > 0);
        assert (Q > 0);
        this.Q = Q;
        this.M = M;
        // Computes (R ^(M-1)) % Q using the Horner's method
        // This precomputed result is useful for implementing nextHash in O(1)
        RM = 1;
        for (int i = 1; i <= M - 1; i++) {
            RM = (RM * R) % Q;
        }
    }

    /**
     * Computes the hash(t) function on the substring
     * t[from,...,from+M-1] in O(1) defined as
     * hash[t] = (t[from] * R^(M-1) + t[from+1] * R^(M-2) + ... + t[from+M-1]) % Q
     * Remark: ^ is the exponent operator, and % the modulo operator
     *
     * The formula given below can be computed in O(M) but
     * it is possible to compute it in O(1) by using the previousHash = hash(t-1)
     *
     * @param t the input array
     * @param previousHash = hash(t-1) that is the one on t[from-1,...from+M-2]
     * @param from the index of the substring window, must be on the inteveral [1...t.length-M]
     * @return hash[t] = (t[from] * R^(M-1) + t[from+1] * R^(M-2) + ... + t[from+M-1]) % Q
     *
     */
    public int nextHash(char[] t, int previousHash, int from) {
        // TODO
        //  Hint1: To compute hash[t]  in O(1) you should not iterate over M entries of t,
        //  by developing h[t], you should realize that is has a lot of common with h[t-1] = previousHash
        //  The RM values computed above might help you as well in the computation.
        //  Hint2: Modulo operator is distributive (A + B) % Q = (A % Q + B % Q) % Q (property exploited by Horners's method)
        //  Hint3: To compute B % Q if you have x = (A + B) % Q, you should do (x + Q - A % Q) % Q
        // STUDENT return 0;
        // BEGIN STRIP
        int previous = previousHash + Q - (t[from - 1] * RM) % Q;
        return ((previous * R) % Q + t[from + M - 1]) % Q;
        // END STRIP
    }
}