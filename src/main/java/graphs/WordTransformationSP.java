package graphs;

import java.util.*;


/**
 * You are asked to implement the WordTransformationSP
 * class which allows to find the shortest path
 * from a string A to another string B
 * (with the certainty that there is a path from A to B).
 * To do this, we define a rotation(x, y) operation that
 * reverses the order of the letters between the x and y positions (not included).
 * For example, with A=``HAMBURGER``, if we do rotation(A, 4, 8), we get HAMBEGRUR.
 * So you can see that the URGE sub-string
 * has been inverted to EGRU and the rest of the string
 * has remained unchanged: HAMB + ECRU + R = HAMBEGRUR.
 * Let's say that a rotation(x, y) has a cost of y-x.
 * For example going from HAMBURGER to HAMBEGRUR costs 8-4 = 4.
 * The question is what is the minimum cost to reach a string B from A?
 * So you need to implement a public static int minimalCost(String A, String B)
 * function that returns the minimum cost to reach String B
 * from A using the rotation operation.
 */
public class WordTransformationSP {

    /**
     * Rotate the substring between start and end of a given string s
     * eg. s = HAMBURGER, rotation(s,4,8) = HAMBEGRUR i.e. HAMB + EGRU + R
     */
    /**
     * Rotate the substring between start and end of a given string s
     * eg. s = HAMBURGER, rotation(s,4,8) = HAMBEGRUR i.e. HAMB + EGRU + R
     *
     * @param s
     * @param start
     * @param end
     * @return rotated string
     */
    public static String rotation(String s, int start, int end) {
        return s.substring(0, start) + new StringBuilder(s.substring(start, end)).reverse().toString() + s.substring(end);
    }

    /**
     * Compute the minimal cost from string "from" to string "to" representing the shortest path
     *
     * @param from
     * @param to
     * @return
     */
    public static int minimalCost(String from, String to) {
        // TODO
        // STUDENT return 0;
        // BEGIN STRIP
        HashMap<String, Integer> distTo = new HashMap<>();
        PriorityQueue<Rotation> queue = new PriorityQueue<>();
        queue.add(new Rotation(from, 0));
        distTo.put(from, 0);
        while (true) {
            Rotation r = queue.poll();
            if (r.value.equals(to)) { // Found "to"
                return r.distance;
            }
            if (r.distance != distTo.get(r.value)) { // Check if r is outdated
                continue;
            }
            String val = r.value;
            for (int i = 0; i < val.length() - 1; i++) {
                for (int j = i + 2; j <= val.length(); j++) {
                    String out = rotation(val, i, j);
                    int cost = r.distance + (j - i);
                    Integer previous = distTo.get(out);
                    if (previous == null || previous > cost) {
                        // New distance is better than the previous one for this word
                        distTo.put(out, cost);
                        queue.add(new Rotation(out, cost));
                    }
                }
            }
        }
        // END STRIP
    }

    // BEGIN STRIP
    private static class Rotation implements Comparable<Rotation> {

        String value;
        int distance;

        Rotation(String value, int distance) {
            this.value = value;
            this.distance = distance;
        }

        @Override
        public int compareTo(Rotation o) {
            return this.distance - o.distance;
        }
    }
    // END STRIP

}
