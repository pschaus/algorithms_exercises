package sorting;
// BEGIN STRIP
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
// END STRIP

/**
 * The Olympic Games organizers need to allocate facilities for the athletes' training sessions.
 * Each team has a schedule of training sessions with a start and end time
 * 
 * To organize the athletes' training smoothly, the organizing committee must know
 * the minimum number of facilities they need so that the teams can train without overlap.
 * Each team has given the organizers their training slots,
 * represented by two integers timestamps: the start (included) and end time (not included!) of their session,
 * Given the training sessions' time, write the `minFacilitiesRequired` function,
 * which returns the minimum number of required facilities.
 *
 * Example Input with its visual representation:
 *
 * int[][] sessions = {
 *     {12, 20},//   --------
 *     {14, 25},//     -----------
 *     {19, 22},//          ---
 *     {25, 30},//                -----
 *     {26, 30},//                 ----
 * };
 *
 * In this example, the minimum number of facilities required is 3
 * as at time 19, there are 3 sessions (intervals) overlapping,
 * namely [12, 20), [14, 25), and [19, 22).
 *
 *
 * More formally, the goal is to minimize k such that for all time t,
 * the number of sessions that overlap at time t is at most k
 *
 * The computation must be performed in O(n.log(n)) time complexity
 * where n is the number of training sessions.
 *
 *
 */
public class TrainingSessions {

    /**
     * Determines the minimum number of facilities required to accommodate
     * all the training sessions without overlap.
     *
     * @param sessions a non-null array of int arrays where each int array represents
     *                 a session with start time and end time.
     * @return the minimum number of facilities required.
     */
    public int minFacilitiesRequired(int[][] sessions) {
        // TODO
        // STUDENT return -1;
        // BEGIN STRIP
        // sweep line algorithm
        List<int[]> events = new ArrayList<>();
        for (int[] session : sessions) {
            events.add(new int[]{session[0], 1});
            events.add(new int[]{session[1], -1});
        }
        events.sort((a, b) -> (a[0] == b[0]) ? a[1] - b[1] : a[0] - b[0]);
        int max = 0;
        int current = 0;
        for (int[] event : events
        ) {
            current += event[1];
            max = Math.max(max, current);
        }
        return max;
        // END STRIP
    }

}
