package graphs;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

/**
 * Author Pierre Schaus
 *
 * Assume the following 5x5 matrix that represent a grid surface:
 * int [][] tab = new int[][] {{1,3,3,1,3},
 *                             {4,2,2,4,5},
 *                             {4,4,5,4,2},
 *                             {1,4,2,3,6},
 *                             {1,1,1,6,3}};
 *
 * Given a global water level, all positions in the matrix
 * with a value <= the water level are flooded and therefore unsafe.
 * So, assuming the water level is 3,
 * all safe points are highlighted between parenthesis:
 *
 *   1 , 3 , 3 , 1 , 3
 *  (4), 2 , 2 ,(4),(5)
 *  (4),(4),(5),(4), 2
 *   1 ,(4), 2 , 3 ,(6)
 *   1 , 1 , 1 ,(6), 3}
 *
 * The method you need to implement is
 * a method that find a safe-path between
 * two positions (row,column) on the matrix.
 * The path assume you only make horizontal or vertical moves
 * but not diagonal moves.
 *
 * For a water level of 4, the shortest path
 * between (1,0) and (1,3) is
 * (1,0) -> (2,0) -> (2,1) -> (2,2) -> (2,3) -> (1,3)
 *
 *
 * Complete the code below so that the {@code  shortestPath} method
 * works as expected
 */
public class GlobalWarmingPaths {

    // BEGIN STRIP
    int rows, cols;
    boolean[][] marked;
    Point[][] edgeTo;
    // END STRIP
    int waterLevel;
    int [][] altitude;

    public GlobalWarmingPaths(int[][] altitude, int waterLevel) {
        this.waterLevel = waterLevel;
        this.altitude = altitude;
        // TODO
        // BEGIN STRIP
        rows = altitude.length;
        cols = altitude[0].length;
        // END STRIP
    }


    /**
     * Computes the shortest path between point p1 and p2
     * @param p1 the starting point
     * @param p2 the ending point
     * @return the list of the points starting
     *         from p1 and ending in p2 that corresponds
     *         the shortest path.
     *         If no such path, an empty list.
     */
    public List<Point> shortestPath(Point p1, Point p2) {
        // TODO
        // expected time complexity O(n^2)
        // STUDENT return null;

        // BEGIN STRIP
        // Extreme case when the path is just one point
        if (p1.equals(p2)) {
            LinkedList<Point> path = new LinkedList<>();
            if (altitude[p1.getX()][p1.getY()] > waterLevel) {
                path.add(p1);
                return path;
            }
            return null;
        }

        marked = new boolean[rows][cols];
        edgeTo = new Point[rows][cols];

        final int[][] pos = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};

        LinkedList<Point> queue = new LinkedList<>();
        queue.add(p1);
        while (!queue.isEmpty()) {
            Point current = queue.remove();
            if (current.getX() == p2.getX() && current.getY() == p2.getY()){
                break;
            }

            for (int i = 0; i < 4; i++) {
                int x = pos[i][0];
                int y = pos[i][1];
                int neiX = current.getX() + x;
                int neiY = current.getY() + y;

                if ((0 <= neiX && neiX < rows) && (0 <= neiY && neiY < cols) && (altitude[neiX][neiY] > waterLevel)) {
                    if(!marked[neiX][neiY]){
                        marked[neiX][neiY] = true;
                        edgeTo[neiX][neiY] = current;
                        queue.add(new Point(neiX, neiY));
                    }
                }
            }

        }

        LinkedList<Point> path = new LinkedList<>();
        if (!marked[p2.getX()][p2.getY()]) return path;
        for (Point i = p2; !i.equals(p1); i = edgeTo[i.getX()][i.getY()]) {
            path.add(i);
        }
        path.add(p1);
        Collections.reverse(path);
        return path;
        // END STRIP
    }

    /**
     * This class represent a point in a 2-dimension discrete plane.
     * This is used to identify the cells of a grid
     * with X = row, Y = column
     */
    static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point) {
                Point p = (Point) o;
                return p.x == this.x && p.y == this.y;
            }
            return false;
        }
    }
}
