package searching;


/**
 * Given a set of n players, EACH WITH A DIFFERENT score at an arcade game,
 * you want to know what would be the rank of a given score among those.
 *
 * Your first task is to implement correctly the compareTo(Player o)
 * so that the sorting algorithm in the constructor
 * sorts the givens players in decreasing order of their score.
 *
 * Your second task is to implement the getRank(int score) method
 * to find the rank of a given score among the players.
 *
 * !!!! You cannot call the get(int i) more than 2 x log(n) times !!!
 * So that the time complexity is O(log(n)).
 *
 * The highest score is rank 0 and the lowest score is rank n-1.
 *
 * If the queried score is equal to a score that is stored in the players
 * they should have the same rank.
 *
 */
public class ArcadeRanking {



    /**
     *  Stores the players in decreasing order of their score
     *  and allow to retrieve them in their sorted order with a get method.
     *  You don't need to modify this interface nor to implement it.
     *  All you know is that if you have correctly
     *  implemented the compareTo method in Player,
     *  the players are sorted in decreasing order of their score.
     *  It means that get(0) returns the player with the highest score.
     *  and get(size()-1) returns the player with the lowest score.
     */
    interface Players {
        int size();
        Player get(int i);
    }


    /**
     * Return the rank among the given players for the given score.
     *
     * In your algorithm, you cannot call the get(int i) method of players
     * more than 2 x log(n) times so that the time complexity is in O(log(n)).
     *
     * The highest score is rank 0 and the lowest score is rank n-1.
     * If the queried score is equal to a score that is stored in the players
     * they should have the same rank.
     * @param players the set of players, sorted in decreasing order of their score
     *                so that get(0) returns the player with the highest score.
     * @param score the score to find the rank of
     */
    public static int getRank(Players players, int score) {
        // TODO
        // BEGIN STRIP
        int high = players.size() - 1;
        int low = 0;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int scoreMid = players.get(mid).score;
            if (scoreMid > score) low = mid + 1;
            else if (scoreMid < score) high = mid - 1;
            else return mid;
        }
        return low;
        // END STRIP
        // STUDENT return -1;
    }


}

class Player implements Comparable<Player> {
    public final int score;
    public final String pseudo;

    public Player(int score, String pseudo) {
        this.score = score;
        this.pseudo = pseudo;
    }

    /**
     * Compare two Players objects, so that they can
     * be sorted in decreasing order of their score
     * @param o the other Player to compare to
     */
    @Override
    public int compareTo(Player o) {
        // TODO
        // STUDENT return -1;
        // BEGIN STRIP
        return -Integer.compare(this.score, o.score);
        // END STRIP
    }

    @Override
    public boolean equals(Object obj) {
        return pseudo.equals(((Player) obj).pseudo);
    }

    @Override
    public int hashCode() {
        return pseudo.hashCode();
    }

    @Override
    public String toString() {
        return pseudo + ": " + score;
    }
}
