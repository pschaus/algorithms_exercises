package searching;

/**
 * Rudolph is organising a sleigh race for the reindeers this Christmas Eve, and he needs your help to keep track of
 * the results. Each reindeer enters the race one at a time, and Rudolph wants to know the ranking of each reindeer
 * as if the rankings were sorted at the moment of entry.
 *
 * A reindeer's ranking is determined by how many of the previous reindeers were faster than itself. A ranking of 0
 * indicates the fastest reindeer up to this point.
 *
 * Given a list of race time of each reindeer ordered by registration, help Rudolph calculate the ranking of each
 * reindeer just after they finished their run.
 *
 * Input:
 *     * An array of n positive integers representing the race time of the reindeers ordered by registration.
 *
 * Output:
 *     * A list of integers where the i-th element is the rank of the i-th reindeer when it finishes the race.
 *
 * For example:
 *     * Input: [5, 4, 6, 4, 2, 3, 7]
 *     * Output: [0, 0, 2, 0, 0, 1, 6]
 *
 *     * When the first reindeer finishes with a time of 5, there are no faster registered reindeer, so its ranking
 *       is 0.
 *     * When the second reindeer finishes with a time of 4, there are no faster registered reindeer, so its ranking
 *       is also 0.
 *     * When the third reindeer finishes with a time of 6, there are two faster registered reindeers (4 and 5), so
 *       its ranking is 2.
 *     * When the fourth reindeer finishes with a time of 4, there are no faster registered reindeer, so its ranking
 *       is 0.
 *     * When the fifth reindeer finishes with a time of 2, there are no faster registered reindeer, so its ranking
 *       is 0.
 *     * When the sixth reindeer finishes with a time of 3, there is one faster registered reindeer (2), so its
 *       ranking is 1.
 *     * When the seventh reindeer finishes with a time of 7, there are six faster registered reindeers
 *       (2, 3, 4, 4, 5, and 6), so its ranking is 6.
 *
 * Expected Time-Complexity: O(N * log(N)) (N being the size of the array)
 *
 * Hint:
 *     * Think about how you can efficiently maintain a view of the elements as you process the list. A data
 *       structure like a Binary Search Tree (BST) can help you insert elements while preserving their order and track
 *       their positions relative to already-inserted elements.
 */
public class SleighRanking {

    // BEGIN STRIP
    private static class BST {
        private Node root = null;

        private static class Node {
            private final int key;

            private Node left = null;
            private Node right = null;

            private int N_LEFT = 0;
            private int N = 1;

            public Node(int key) {
                this.key = key;
            }
        }

        private static class Rank {
            public int v = 0;
        }

        public int putAndRank(int key) {
            Rank rank = new Rank(); // Get rank by reference
            root = putAndRank(root, key, rank);
            return rank.v;
        }

        private Node putAndRank(Node x, int key, Rank rank) {
            if (x == null) {
                return new Node(key);
            }
            if (key < x.key) {
                x.left = putAndRank(x.left, key, rank);
                x.N_LEFT++;
            } else if (key > x.key) {
                rank.v += x.N_LEFT + x.N;
                x.right = putAndRank(x.right, key, rank);
            } else {
                rank.v += x.N_LEFT;
                x.N++;
            }
            return x;
        }
    }
    // END STRIP

    /**
     * Computes the ranking of each reindeer when it arrives at the end of the race, based on its race time.
     * The ranking is determined by the number of reindeer that have already finished the race with a race time that
     * is less than the race time of the current reindeer.
     *
     * @param array The array of race times of the reindeers.
     *
     * @return An array of integers where the i-th element is the rank of the i-th reindeer when it finishes the race.
     */
    public static int[] computeRankings(int[] array) {
        // TODO
        // BEGIN STRIP
        int[] answer = new int[array.length];
        BST bst = new BST();
        for (int i = 0; i < array.length; i++) {
            answer[i] = bst.putAndRank(array[i]);
        }
        return answer;
        // END STRIP
        // STUDENT return null;
    }
}
