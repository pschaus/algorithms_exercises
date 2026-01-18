package graphs;
import java.util.*;

/**
 *
 * You are lost in a dark maze, and you want to find the exit.
 * Fortunately you have got a flashlight to light the way.
 * Your flashlight have a maximal power capacity.
 * At every move you do you lose one level of your power capacity.
 * When the power capacity falls to 0 you are definitively stuck in the maze.
 * In the maze you can find battery that recharge your flashlight to the maximum capacity.
 * You have to find the shortest path that leads you to the exit.
  * The maze is represented by a matrix of integers (-1)-0-1-2 of size nxm.
 * This matrix is a two-dimensional array.
 * An entry equal to '-1' means that there
 * is a wall and therefore this position is not accessible,
 * while '0' means that the position is free.
 * An entry equal to '1' means that there is battery.
 * An entry equal to '2' means that there is an EXIT.
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
public class DarkMaze {


    static final int WALL = -1;
    static final int BATTERY = 1;
    static final int EXIT = 2;

    static final int[][] pos = new int[][] {{1,0}, {-1,0}, {0,1}, {0,-1}};

    static HashMap<Integer, List<Integer>> pathsToKeyPoint;


    public static List<Integer> findPath(int startX, int startY, int endX, int endY, int maxPower, int[][] maze){
        // BEGIN STRIP
        if (maze[startX][startY] == WALL) return null;

        pathsToKeyPoint = new HashMap<>();
        int nRow = maze.length;
        int nCol = maze[0].length;
        int[] distTo = new int[nRow*nCol];
        int[] edgeTo = new int[nRow*nCol];
        int[] powerTrace = new int[nRow*nCol];
        int startIndex = ind(startX, startY, nCol);

        PriorityQueue<Point> PQ = new PriorityQueue<>();

        for (int i = 0; i < nRow*nCol; i++){
            distTo[i] = Integer.MAX_VALUE;
            powerTrace[i] = 0;
        }
        distTo[startIndex] = 0;
        powerTrace[startIndex] = maxPower;


        PQ.add(new Point(startX, startY, maxPower, 0));
        while (!PQ.isEmpty()){

            Point current = PQ.poll();
            int x = current.x;
            int y = current.y;
            int indCurrent = ind(x, y, nCol);

            if (x == endX && y == endY) break;

            HashMap<Point, List<Integer>> neighbours = getNeighbours(current, maxPower, maze, powerTrace);
            for ( Point neighbour : neighbours.keySet()) {
                int neiX = neighbour.x;
                int neiY = neighbour.y;
                int indNeighbour = ind(neiX, neiY, nCol);

                if (distTo[indNeighbour] > distTo[indCurrent] + neighbour.cost){
                    distTo[indNeighbour] = distTo[indCurrent] + neighbour.cost;
                    PQ.add(new Point(neiX, neiY, neighbour.power, distTo[indNeighbour]));
                    edgeTo[indNeighbour] = indCurrent;
                    pathsToKeyPoint.put(ind(neighbour.x, neighbour.y, nCol),neighbours.get(neighbour));
                }
            }
        }
        int currentIndex = ind(endX, endY, nCol);

        if (pathsToKeyPoint.get(currentIndex)==null) return null;

        List<Integer> path = new ArrayList<>();
        path.add(ind(endX, endY, nCol));

        while (currentIndex != startIndex){
            List<Integer> subPath = pathsToKeyPoint.get(currentIndex);
            for (int i = 1; i < pathsToKeyPoint.get(currentIndex).size(); i++) {
                path.add(subPath.get(i));
            }
            currentIndex = edgeTo[currentIndex];
        }

        Collections.reverse(path);

        return path;
        // END STRIP
        // STUDENT return null;
    }

    // BEGIN STRIP
    /**
     *
     * @param start : point where we start
     * @param maxPower :
     * @param maze : matrix (m x n)
     * @param powerTrace :
     * @return
     */
    private static HashMap<Point, List<Integer>> getNeighbours(Point start, int maxPower, int[][] maze, int[] powerTrace){

        int nRow = maze.length;
        int nCol = maze[0].length;
        int[] distTo = new int[nRow*nCol];
        int[] edgeTo = new int[nRow*nCol];
        HashMap<Point, List<Integer>> keys = new HashMap<>();
        PriorityQueue<Point> PQ = new PriorityQueue<>();

        for (int i = 0; i < nRow*nCol; i++) distTo[i] = Integer.MAX_VALUE;
        distTo[ind(start.x, start.y, nCol)] = 0;
        PQ.add(start);

        while (!PQ.isEmpty()){

            Point current = PQ.poll();
            int x = current.x;
            int y = current.y;
            int indCurrent = ind(x, y, nCol);

            if (maze[x][y] == BATTERY && (start.x != x || start.y != y)){
                keys.put(new Point(x, y, current.power, current.cost), getPathTo(ind(start.x, start.y, nCol), indCurrent, edgeTo));
                continue;
            } else if (maze[x][y] == EXIT){
                keys.put(new Point(x, y, current.power, current.cost), getPathTo(ind(start.x, start.y, nCol), indCurrent, edgeTo));
                return keys;
            }

            for (int w = 0; w < 4; w++) {
                int neiX = (x + pos[w][0]);
                int neiY = (y + pos[w][1]);

                if (neiX >= 0 && neiX < nRow && neiY >= 0 && neiY < nCol ) {
                    int indW = ind(neiX,neiY, nCol);

                    if ( maze[neiX][neiY] != WALL && (distTo[indW] > distTo[indCurrent] + 1) && (current.power > 1 || maze[neiX][neiY] == BATTERY || maze[neiX][neiY] == EXIT) && (powerTrace[indW] <= current.power-1)){

                        int newCost = current.cost+1;
                        int newPower = current.power-1;
                        powerTrace[indW] = newPower;

                        if (maze[neiX][neiY] == BATTERY){
                            newCost = current.cost + (maxPower-current.power);
                            newPower = maxPower;
                        }

                        PQ.offer(new Point(neiX, neiY, newPower, newCost));
                        distTo[indW] = newCost;
                        edgeTo[indW] = indCurrent;
                    }
                }
            }
        }
        return keys;
    }


    private static List<Integer> getPathTo(int src, int well, int[] edgeTo){

        List<Integer> path = new ArrayList<>();

        int current = well;
        while (current != src){
            path.add(current);
            current = edgeTo[current];
        }
        path.add(current);

        return path;
    }
    // END STRIP













    public static int ind(int x, int y, int lg) {
        return x * lg + y;
    }

    public static int row(int pos, int mCols) {
        return pos / mCols;
    }

    public static int col(int pos, int mCols) {
        return pos % mCols;
    }



    // BEGIN STRIP
    static class Point implements Comparable<Point>{

        int x;
        int y;
        int power;
        int cost;

        public Point(int x, int y, int power, int cost){
            this.x = x;
            this.y = y;
            this.power = power;
            this.cost = cost;
        }

        @Override
        public int compareTo(Point o) {
            return this.cost - o.cost;
        }
    }
    // END STRIP


    public static void main(String[] args) {


        int[][] maze = {
                {1, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0,-1,-1},
                {0, 0, 0, 0, 0, 0, 0, 2}
        };

        List<Integer> path = DarkMaze.findPath(0, 0, 2, 7, 8, maze);
        System.out.println(path);
        System.out.println(path.size());


        int[][] maze1 = {
                {1, 0, 0, 0, 0, 1, 0},
                {0,-1,-1, 0, 0, 0, 0},
                {0,-1, 0, 0,-1,-1,-1},
                {0, 0, 1, 0, 0,-1, 0},
                {0, 0, 0,-1, 0, 0, 0},
                {0, 0, 0, 0, 0,-1, 2},
        };

        List<Integer> path2 = DarkMaze.findPath(0, 0, 5, 6, 8, maze1);

        System.out.println(path2);
        System.out.println(path2.size());



        int[][] maze3 = {
                {1, 0, 0, 0, 0, 1, 0},
                {0,-1,-1, 0, 0, 0, 0},
                {0,-1, 0, 0,-1,-1,-1},
                {-1,0, 1, 0, 0,-1, 0},
                {0, 0, 0,-1, 0, 0, 0},
                {0, 0, 0, 0, 0,-1, 2},
        };
        List<Integer> path3 = DarkMaze.findPath(0, 0, 5, 6, 8, maze3);
        System.out.println(path3);
        System.out.println(path3.size());



        int[][] maze4 = {
                {1, 0, 0, 0, 0, 1, 0},
                {0,-1,-1, 0, 0, 0, 0},
                {0,-1, 0, 0,-1,-1,-1},
                {-1,0, 1, 0, 0,-1, 0},
                {0, 0, 0,-1, 0, 0, 0},
                {0, 0, 0, 0, 0,-1, 2},
        };
        List<Integer> path4 = DarkMaze.findPath(0, 0, 5, 6, 6, maze3);
        System.out.println(path4);
        System.out.println(path4.size());


    }
}
