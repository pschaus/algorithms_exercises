package strings;
import java.util.HashMap;

/**
 * Author: Alexis Englebert
 *
 * Context: Oh no, there’s a bomb in your kitchen!
 * You must defuse it before it explodes. To do so, you need to solve a puzzle.
 * You have a grid with n rows and m columns. Each cell contains a color.
 * Each row has two arrows, one pointing left and the other pointing right.
 * When you press the right arrow, all the colors in the row are shifted by one to the right.
 * When you press the left arrow, they are shifted by one to the left.
 *
 * The rows wrap around (are cyclic), so if a color moves off one side of a row,
 * it re-enters from the other side.
 *
 * Your goal is to find two rows that are identical after entering a sequence of arrows.
 * If you find two identical rows, you have defused the bomb.
 * If you can’t find two identical rows, the bomb explodes!
 *
 * You must return the indices of the two identical rows in increasing order.
 * If you don’t find two identical rows, return [-1, -1].
 *
 * Time complexity O(n · m)
 *
 * Exemple:
 * n = 4, m = 5
 * RGRBB
 * RBBGB
 * BBBRB
 * GRBBR
 *
 * The answer is the pair (1, 4) because after one shift to the right on the 4th row,
 * they are equals (GRBBR -> RGRBB)
 */

public class Bombe {
    static final long MOD = (1L << 60);
    static final long A = 91138;
    static long[] hashes;
    // store the base at the ith power to avoid recomputing it every time.
    static long[] baseExponent;
    //BEGIN STRIP
    static int _n, _m;
    //END STRIP

    /**
     * Rotate           A given hashed string to the right.
     * @param hash      The input hashed string
     * @param toRemove  The character to rotate
     * @return          The hash of the string rotated to the right.
     */
    public static long rotateHash(long hash, char toRemove) {
        //BEGIN STRIP
        hash -= ( toRemove * baseExponent[_m - 1]) % MOD;
        hash %= MOD;
        hash *= A;
        hash %= MOD;
        hash += toRemove;
        hash %= MOD;
        //END STRIP
        return hash;
    }
    /**
     * @param n     The number of columns in the grid
     * @param m     The number of rows in the grid
     * @param grid  The input grid
     * @return      A list of 2 elements representing the indices of the two rows, or [-1, -1]
     *              if no rows are the same.
     */
    public static int[] solve(int n, int m, char [][] grid) {
        //TODO
        baseExponent = new long[m];
        hashes = new long[n];
        baseExponent[0] = 1;

        //BEGIN STRIP
        _m = m;
        _n = n;
        HashMap<Long, Integer> match = new HashMap<>();
        //END STRIP
        for (int i = 1; i < m; i++) {
            baseExponent[i] = (baseExponent[i - 1] * A) % MOD;
            baseExponent[i] %= MOD;
        }
        for (int i = 0; i < n; i++) {
            long hash = 0;

            for (int j = 0; j < m; j++) {
                hash += grid[i][j] * baseExponent[j];
                hash %= MOD;
            }
            hashes[i] = hash;
        }
        // STUDENT return -1;
        // BEGIN STRIP
        for(int i = 0; i < hashes.length; i++) {
            match.put(hashes[i], i);
        }

        int left = Integer.MAX_VALUE;
        int right = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                hashes[i] = rotateHash(hashes[i], grid[i][m - j - 1]);
                if (!match.containsKey(hashes[i])) {
                    match.put(hashes[i], i);
                }

                if(match.get(hashes[i]) != i) {
                    int a = match.get(hashes[i])+1;
                    int b = i+1;
                    int mini = Math.min(a, b);
                    int maxi = Math.max(a, b);
                    if(mini <= left) {
                        left = mini;
                        right = Math.min(right, maxi);
                    }
                }
            }
        }
        if(left != Integer.MAX_VALUE && right != Integer.MAX_VALUE){
            return new int[]{left, right};
        }
        // END STRIP
        return new  int[]{-1, -1};

    }

}


