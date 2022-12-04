package graphs;

import java.util.*;

/**
 * Author: Pierre Schaus
 *
 * The first pictures from the James-Webb telescope revealed hyperspace by-passes
 * connecting certain galaxies (much like in the book
 * “The Hitchhiker's Guide to the Galaxy” by Douglas Adams).
 * The telescope is also able to detect certain galaxies that
 * contain habitable planets (called abusively habitable galaxies).
 * NASA asks you to plan an escape route to reach a habitable
 * galaxy if ever the earth were to no longer be.
 * However, each by-pass observed between two galaxies is
 * functional only for a specifi period of time (expressed in years)
 * and it is no longer usable once this period has passed.
 * In order to save humanity, we ask you to design an optimal escape plan,
 * to a habitable galaxy using only functional by-passes.
 * As you will have understood, this problem can be modeled as graph a graph problem,
 * where each node is a galaxy and the directed links are the hyperspatial by-passes.
 * Given a starting galaxy (source) and a set of habitable galaxies,
 * is it possible to reach a viable galaxy (any of them) and
 * how long would it take at least knowing that each by-pass takes 1 year to be crossed ?
 */
public class GalaxyPath {
    /**
     * Hint: Read the first small unit test with 5 galaxies for
     *       a small example and its expected solution.
     *       By reading this example, you will make sure
     *       you understand the problem to be solved.
     *
     * @param graph is an n x n matrix,
     *              each entry i,j represent the expiration date (in years)
     *              of the by-passe from galaxy i to j.
     *              an entry = 0 means that the by-pass does not exist (no link since the beginning).
     *              an entry = i > 0 means that the by-pass can be used but before time i,
     *              because it will be closed at time i
     *              Time passes by a year each time you use by-pass to travel.
     *
     * @param source a galaxy id on [0..n-1]
     * @param destinations a set of habitable galaxies (ids taken on on [0..n-1])
     * @return the shortest (number of years) feasible path in years from the source
     *         to a habitable destination galaxy.
     *         More formally if P = (n0, n1, ...nk) is a valid path of duration k
     *            iff n1 = source, nk = destination and
     *                for all (ni, ni+1) in P, graph[ni][ni+1] >= i+1
     *         -1 if no path exists
     *
     */
    public static int findPath(int [][] graph, int source, Set<Integer> destinations) {
        // TODO Your algorithm should execute in O(n^2) where n is the number of galaxies (nodes)
        // STUDENT return -2;
        // BEGIN STRIP
        int n = graph.length;;
        boolean [] visited = new boolean[n];
        int [] dist = new int[n];
        int current = source;
        Queue<Integer> open = new ArrayDeque<>();
        open.add(source);
        visited[source] = true;

        while (open.size() > 0) {
            int i = open.poll();
            if (destinations.contains(i)) {
                return  dist[i];
            }
            for (int j = 0; j < n; j++) {
                if (!visited[j] && dist[i] < graph[i][j]) {
                    open.add(j);
                    visited[j] = true;
                    dist[j] = dist[i]+1;
                }
            }
        }
        return -1;
        // END STRIP
    }
}


