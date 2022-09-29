package graphs;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

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


    public List<Point> shortestPath(Point p1, Point p2) {
        // TODO
        // expected time complexity O(n^2)
        // STUDENT return  return new ArrayList<Point>();

        // BEGIN STRIP
        // Extreme case when the path is just one point
        if(p1.equals(p2)){
            LinkedList<Point> path = new LinkedList<>();
            if(altitude[p1.getX()][p1.getY()] > waterLevel){
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
        for(Point i=p2; ! i.equals(p1); i=edgeTo[i.getX()][i.getY()]){
            path.add(i);
        }
        path.add(p1);
        Collections.reverse(path);
        return path;
        // END STRIP
    }

    /**
     * This class represent a point in a 2-dimension discrete plane. This is used, for instance, to
     * identified cells of a grid
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
