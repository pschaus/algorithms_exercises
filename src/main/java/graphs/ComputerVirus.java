package graphs;

import java.util.*;

/**
 * Proposed at the exam of August 2025
 * A computer virus has infected a computer network.
 * Each node represents a server, and edges represent directed connections to other servers.
 * The virus starts at a given server (the “start server”) at time 0.
 * Each server takes a specific incubation time before it becomes infectious to adjacent servers.
 * Once a server becomes infectious, it immediately begins infecting its direct neighbors via outgoing edges.
 *
 * Your task is to compute and return the earliest time each server becomes infectious.
 *
 * The time complexity of your solution should be O(n + m log n)
 */
public class ComputerVirus {


    /**
     * Computes the earliest time each server in the network becomes infectious.
     *
     * @param network a map representing the directed connections between servers
     * @param incubation a map representing the incubation time for each server
     * @param start the server where the virus starts
     * @return a map with the earliest time each server becomes infectious,
     *         if a server is not reachable, it should not be included in the result
     */
    public static Map<String, Integer> computeInfectionTimes(
            Map<String, List<String>> network,
            Map<String, Integer> incubation,
            String start
    ) {
        // STUDENT return null;
        // BEGIN STRIP
        Map<String, Integer> infectionTime = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.time));
        queue.add(new Node(start, 0));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            // If already infected earlier, skip
            if (infectionTime.containsKey(current.name)) continue;

            int readyTime = current.time + incubation.get(current.name);
            infectionTime.put(current.name, readyTime);

            for (String neighbor : network.getOrDefault(current.name, Collections.emptyList())) {
                if (!infectionTime.containsKey(neighbor)) {
                    queue.add(new Node(neighbor, readyTime));
                }
            }
        }

        return infectionTime;
        // END STRIP
    }
    // BEGIN STRIP
    static class Node {
        String name;
        int time;

        Node(String name, int time) {
            this.name = name;
            this.time = time;
        }
    }
    // END STRIP
}
