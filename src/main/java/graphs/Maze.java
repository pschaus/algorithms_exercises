package graphs;

// BEGIN STRIP

import java.util.Collections;
import java.util.LinkedList;
// END STRIP

/**
 * We are interested in solving a maze represented
 * by a matrix of integers 0-1 of size nxm.
 * This matrix is a two-dimensional array.
 * An entry equal to '1' means that there
 * is a wall and therefore this position is not accessible,
 * while '0' means that the position is free.
 * We ask you to write a Java code to discover
 * the shortest path between two coordinates
 * on this matrix from (x1, y1) to (x2, y2).
 * The moves can only be vertical (up/down) or horizontal (left/right)
 * (not diagonal), one step at a time.
 * The result of the path is an Iterable of
 * coordinates from the origin to the destination.
 * These coordinates are represented by integers
 * between 0 and n * m-1, where an integer 'a'
 * represents the position x =a/m and y=a%m.
 * If the start or end position is a wall
 * or if there is no path, an empty Iterable must be returned.
 * The same applies if there is no path
 * between the origin and the destination.
 */
public class Maze {
    public static Iterable<Integer> shortestPath(int[][] maze, int x1, int y1, int x2, int y2) {
        // TODO
        // STUDENT return null;
        // BEGIN STRIP
        if (maze[x1][y1] == 1 || maze[x2][y2] == 1) return new LinkedList<>();

        LinkedList<Integer> queue = new LinkedList<>();

        int lgy = maze[0].length;
        int lgx = maze.length;

        int[] edgeTo = new int[lgx * lgy];
        boolean[] marked = new boolean[lgx * lgy];

        marked[ind(x1, y1, lgy)] = true;
        queue.add(ind(x1, y1, lgy));

        final int[][] pos = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};

        boolean found = false;
        while (!queue.isEmpty() && !found) {
            int current = queue.remove();
            int currX = row(current, lgy);
            int currY = col(current, lgy);

            // for each neighbors of the current position (up/right/down/left)
            for (int i = 0; i < 4; i++) {
                int x = pos[i][0];
                int y = pos[i][1];
                int neiX = currX + x;
                int neiY = currY + y;
                // check we are not going out of the maze or against a wall
                if ((0 <= neiX && neiX < lgx) && (0 <= neiY && neiY < lgy) && (maze[neiX][neiY] != 1)) {
                    int index = ind(neiX, neiY, lgy);
                    if (!marked[index]) {
                        marked[index] = true;
                        queue.add(index);
                        edgeTo[index] = current;
                        // check if destination found (optimisation)
                        if (neiX == x2 && neiY == y2) {
                            found = true;
                        }
                    }
                }
            }
        }

        int source = ind(x1, y1, lgy);
        int dest = ind(x2, y2, lgy);

        LinkedList<Integer> list = new LinkedList<>();
        if (!marked[dest]) return list;
        while (dest != source) {
            list.add(dest);
            dest = edgeTo[dest];
        }
        list.add(dest);
        Collections.reverse(list);
        return list;
        // END STRIP
    }

    public static int ind(int x, int y, int lg) {
        return x * lg + y;
    }

    public static int row(int pos, int mCols) {
        return pos / mCols;
    }

    public static int col(int pos, int mCols) {
        return pos % mCols;
    }

}
