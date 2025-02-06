package graphs;

// BEGIN STRIP
import java.util.*;
// END STRIP

/**
 * Given two integers start and end, your task is to determine the minimum number of operations required to transform
 * start into end. You may use the following operations:
 *     - Add 5 to the number
 *     - Subtract 7 to the number
 *     - Multiply the number by 2
 *     - Divide the number by 3 (integer division)
 */
public class IntegerTransformation {

    /**
     * Given two integers start and end, returns the minimum number of operations required to transform start into end.
     * @param start the initial integer.
     * @param target the target integer.
     * @return the minimum number of operations required to transform start into end.
     */
    public static int countSteps(int start, int target) {
        // TODO
        // STUDENT return -1;
        // BEGIN STRIP
        HashMap<Integer, Integer> distTo = new HashMap<>();
        distTo.put(start, 0);

        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int dist1 = distTo.getOrDefault(o1, Integer.MAX_VALUE);
                int dist2 = distTo.getOrDefault(o2, Integer.MAX_VALUE);
                return Integer.compare(dist1, dist2);
            }
        });

        pq.add(start);
        while (!pq.isEmpty()) {
            int curr = pq.poll();

            if (curr == target) {
                break;
            }

            for (int neighbor : getNeighbors(curr)) {
                if (distTo.getOrDefault(neighbor, Integer.MAX_VALUE) > distTo.get(curr) + 1) {
                    distTo.put(neighbor, distTo.get(curr) + 1);
                    pq.remove(neighbor);
                    pq.add(neighbor);
                }
            }
        }

        return distTo.get(target);
        // END STRIP
    }

    // BEGIN STRIP
    private static Iterable<Integer> getNeighbors(int curr) {
        Set<Integer> neighbors = new HashSet<>();
        neighbors.add(curr + 5);
        neighbors.add(curr - 7);
        neighbors.add(curr * 2);
        neighbors.add(curr / 3);
        return neighbors;
    }
    // END STRIP
}
