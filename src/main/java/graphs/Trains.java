package graphs;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Trains {

    public static class StationTime implements Comparable<StationTime> {

        public final String pos; //the station
        public final int time;  //the time
    
        public StationTime(String pos, int time) {
            this.pos = pos;
            this.time = time;
        }
    
        @Override
        public int hashCode() {
            return pos.hashCode() ^ Integer.hashCode(~time);
        }
    
        @Override
        public boolean equals(Object obj) {
            if(obj instanceof StationTime)
                return ((StationTime) obj).pos.equals(pos) && ((StationTime) obj).time == time;
            return false;
        }
    
        @Override
        public int compareTo(StationTime o) {
            int out = time - o.time;
            if(out == 0)
                return pos.compareTo(o.pos);
            return out;
        }
    }

    /**
     * Considering a list containing the relations between train stations (a train leaves the station `from` at `startTime` 
     * and arrives at station `to` at `endTime`) and the positions of those stations, a starting station and a starting time,
     * what is the earliest hour at which you can reach each an accessible station ?
     * 
     *You don't have to consider several points :
     * - passengers can leave a station at the exact moment where they reach this station
     * - all liaisons are direct
     * - timetable are not periodic, you don't have to repeat them everyday
     * - starTime < endTime and from != to are always true in all relations
     * - there is no duplicates entry (i.e. strictly equal relations)
     *
     * The question is graduated on 20 :
     * - 13 points if the algorithm works
     * - 4 points if the algorithm is fast (reasonable complexity)
     * - 3 points if the algorithm is really fast (optimal complexity)
     * 
     * We leave notion of optimal/reasonable complexity unclear on purpose. It is your job, based on your knowledge,
     * to identify, among the appropriate algorithm family, which one is optimal.
     * 
     * A clue : as you probably guessed it, it is clearly a graph problem. But it isn't a usual graph :
     * nodes are particular, because they doesn't represent only a point in the space, but also a point in the time
     * (for example (Bruxelles-midi, 8:48 am)).
     *
     * Don't forget that if I reach Bxl-midi at time i, I can take any train that leaves Bxl-midi at time j >= i.
     *
     * By the way, do you know the function TreeMap.subMap (https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html#subMap-K-boolean-K-boolean-) ?
     *
     * @param relations a list of relations that connect a pair (station, time) (the key) (for exemple, Bxl-midi, 8:48 am)
     *                  with a list of trains that leave the station at this time, represented by a list of
     *                  StationTime objects that gives at each station/time those trains arrives.
     *                  Stations are represented by Strings ("Bxl-midi") and time by positive integers.
     *
     * @param startPoint starting station/time
     * @return a dictionnary containing, for each reachable station (key) the earliest hour at which it can be reached.
     *         The dictionnary must contain the starting station
     */
    public static Map<String, Integer> reachableEarliest(HashMap<StationTime, LinkedList<StationTime>> relations, StationTime startPoint) {
        //STUDENT return null; 
        //BEGIN STRIP
        HashMap<String, Integer> solution = new HashMap<>();
        PriorityQueue<String> Q = new PriorityQueue<>((x,y) -> solution.get(x).compareTo(solution.get(y)));
        HashMap<String, Boolean> visited = new HashMap<>();

        Q.add(startPoint.pos);
        visited.put(startPoint.pos, false);
        solution.put(startPoint.pos, startPoint.time);

        while(!Q.isEmpty()) {
            String u = Q.poll();

            List<LinkedList<StationTime>> neighbors = relations.entrySet().stream().filter(x -> x.getKey().time >= solution.get(u) && x.getKey().pos == u).map(Map.Entry::getValue).collect(Collectors.toList());
            for(LinkedList<StationTime> neighborsList:neighbors) {
                for(StationTime neighbor: neighborsList){
                    if(visited.containsKey(neighbor.pos)){
                        if(!visited.get(neighbor.pos) && neighbor.time < solution.get(neighbor.pos)) {
                            solution.put(neighbor.pos, neighbor.time);
                        }
                    }
                    else{
                        visited.put(neighbor.pos, false);
                        solution.put(neighbor.pos, neighbor.time);
                        Q.add(neighbor.pos);
                    }
                }
            }
        }

        return solution;
        //END STRIP
    }
}
