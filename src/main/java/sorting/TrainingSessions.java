package sorting;
// BEGIN STRIP
import java.util.Arrays;
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

        if (sessions.length == 0) {
            return 0;
        }
        // Sorting the sessions by the starting time. If they are the same compare the end time O(nlogn)
        Arrays.sort(sessions, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

        // Priority queue with end times of the sessions
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.add(sessions[0][1]);

        // The idea is that for each new session, we check if we can use
        // an existing facility or if we need an additional one of a session that was completed
        // This is done lazily so at the end, the number of facilities in the queue is the final answer.
        // Tnhis is node in O(n.log(n))
        for (int i = 1; i < sessions.length; i++) {
            // check if we can use an existing session ?
            if (queue.peek() <= sessions[i][0]) {
                // using an existing facility means deleting it from the queue
                // we delete the one finishing first (min priority queue)
                queue.poll();
            }
            queue.add(sessions[i][1]); // then we use a new facility
        }
        return queue.size();
        // END STRIP
    }





}
