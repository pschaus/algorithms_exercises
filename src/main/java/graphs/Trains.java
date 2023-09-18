package graphs;

import java.util.*;

/**
 * Considering a list containing the relations between train stations
 * (a train leaves the station `from` at `startTime`
 * and arrives at station `to` at `endTime`) and the positions
 * of those stations, a starting station and a starting time,
 * what is the earliest hour at which you can reach each an accessible station ?
 * <p>
 * You don't have to consider several points :
 * - passengers can leave a station at the exact moment where they reach this station
 * - all liaisons are direct
 * - timetable are not periodic, you don't have to repeat them evryday
 * - startTime < endTime and from != to are always true in all relations
 * - there is no duplicate entry (i.e. strictly equal relations)
 * <p>
 * As an example, let's consider that your list of relations between train stations is :
 * {(Bxl-midi, 9:00 am) : [(Namur, 9:20 am), (Charleroi, 9:30 am), (Ottiginies, 9:20 am)],
 * (Ottignies, 9:30 am) : [(LLN, 9:40 am), (Charleroi, 9:50) am],
 * (Charleroi, 9:30 am) : [(Namur, 9:45 am)],
 * (Charleroi, 9:50 am) : [(Ottignies, 10:00 am)],
 * (Namur, 10:00 am) : [(Charleroi, 10:30 am)],
 * (Ottignies, 10:00 am) : [(LLN, 10:20 am)]
 * }
 * <p>
 * In the above dictionary, the keys are the departure stations and times,
 * and the values are a list of stations that you can reach
 * (if you take a train starting from the key) and the time
 * at which you would reach them.
 * <p>
 * The list of reacheable stations and the earliest hour you can reach them is :
 * {Bxl-midi : 9:00 am,
 * Namur : 9:20 am,
 * Charleroi, 9:30 am,
 * Ottignies : 9:20 am,
 * LLN : 9:40 am
 * }
 * <p>
 * We leave notion of optimal/reasonable complexity unclear on purpose.
 * It is your job, based on your knowledge,
 * to identify, among the appropriate algorithm family, which one is optimal.
 * <p>
 * Some clues :
 * - As you probably guessed it, it is clearly a graph problem.
 *   But it isn't a usual graph since nodes are particular.
 *   Nodes don't represent only a point in the space, but also
 *   a point in the time (for example (Bruxelles-midi, 8:48 am)).
 * - Don't forget that if I reach Bxl-midi at time i,
 *   I can take any train that leaves Bxl-midi at time j >= i.
 * - Consider the function TreeMap.subMap that might be useful
 *   (https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html#subMap-K-boolean-K-boolean-) ?
 */
public class Trains {

    /** 
     * Considering given starting station and time, compute the earliest hour at which
     * any accessible station can be reached.
     * @param relations a list of relations that connect a pair (station, time) (the key)
     *                  (for example, Bxl-midi, 8:48 am)
     *                  with a list of trains that leave the station at this time,
     *                  represented by a list of StationTime objects that gives for each,
     *                  the destination-station + time those trains arrives.
     *                  Stations are represented by Strings ("Bxl-midi") and
     *                  (absolute) time by positive integers.
     *
     * @param startPoint starting station/time
     * @return a map containing, for each reachable station (key)
     *         the earliest hour at which it can be reached.
     *         The map must contain the starting station
     */
    public static Map<String, Integer> reachableEarliest(HashMap<StationTime, LinkedList<StationTime>> relations, StationTime startPoint) {
        // STUDENT return null; 
        // BEGIN STRIP
        HashMap<String, Integer> solution = new HashMap<>();
        PriorityQueue<StationTime> Q = new PriorityQueue<>((x, y) -> x.compareTo(y));

        for (StationTime key : relations.keySet()) {
            Q.add(key);
        }

        solution.put(startPoint.station, startPoint.time);

        while (!Q.isEmpty()) {
            StationTime u = Q.poll();
            if (solution.containsKey(u.station) && u.time >= solution.get(u.station)) {
                for (StationTime neighbor : relations.get(u)) {
                    if (solution.containsKey(neighbor.station)) {
                        if (neighbor.time < solution.get(neighbor.station))
                            solution.put(neighbor.station, neighbor.time);
                    } else
                        solution.put(neighbor.station, neighbor.time);
                }
            }
        }

        return solution;
        // END STRIP
    }

    public static class StationTime implements Comparable<StationTime> {

        public final String station; 
        public final int time;  
    
        public StationTime(String station, int time) {
            this.station = station;
            this.time = time;
        }
    
        @Override
        public int hashCode() {
            return station.hashCode() ^ Integer.hashCode(~time);
        }
    
        @Override
        public boolean equals(Object obj) {
            if(obj instanceof StationTime)
                return ((StationTime) obj).station.equals(station) && ((StationTime) obj).time == time;
            return false;
        }
    
        @Override
        public int compareTo(StationTime o) {
            int out = time - o.time;
            if(out == 0)
                return station.compareTo(o.station);
            return out;
        }
    }
}
