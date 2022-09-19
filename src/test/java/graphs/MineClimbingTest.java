package graphs;


import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.PriorityQueue;

class MineClimbingCorrect {
    /**
     * Returns the minimum distance between (startX, startY) and (endX, endY), knowing that
     * you can climb from one mine block (a,b) to the other (c,d) with a cost Math.abs(map[a][b] - map[c][d])
     *
     * Do not forget that the world is round: the position (map.length,i) is the same as (0, i), etc.
     *
     * map is a matrix of size n times m, with n,m > 0.
     *
     * 0 <= startX, endX < n
     * 0 <= startY, endY < m
     */
    public static int best_distance(int[][] map, int startX, int startY, int endX, int endY) {
        int[][] dist = new int[map.length][map[0].length];
        for(int i = 0; i < map.length; i++)
            Arrays.fill(dist[i], Integer.MAX_VALUE);

        PriorityQueue<PositionAndCost> pq = new PriorityQueue<>();

        pq.add(new PositionAndCost(startX, startY,0));
        dist[startX][startY] = 0;
        while (!pq.isEmpty()) {
            PositionAndCost next = pq.poll();
            if(next.x == endX && next.y == endY)
                break;
            if(dist[next.x][next.y] != next.cost)
                continue;

            final int[][] pos = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};

            for(int i = 0; i < 4; i++) {
                int x = pos[i][0];
                int y = pos[i][1];
                int neiX = (map.length + next.x + x) % map.length;
                int neiY = (map[0].length + next.y + y) % map[0].length;

                int add_cost = Math.abs(map[next.x][next.y] - map[neiX][neiY]);
                if (dist[neiX][neiY] > add_cost + next.cost) {
                    dist[neiX][neiY] = add_cost + next.cost;
                    pq.add(new PositionAndCost(neiX, neiY, dist[neiX][neiY]));
                }
            }
        }

        return dist[endX][endY];
    }

    // you may need to add additional things below.
    static class PositionAndCost implements Comparable<PositionAndCost>{
        public int x;
        public int y;
        public int cost;

        public PositionAndCost(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }

        @Override
        public int compareTo(PositionAndCost o) {
            return - o.cost + cost;
        }
    }
}


/**
 * This is just a limited number of tests provided for convenience
 * Don't hesitate to extend it with other tests
 */

public class MineClimbingTest {

    @Test(timeout = 3000)
    @Grade(value = 5.0)
    public void example() {
        int[][] map = new int[][] {
                {7, 2, 9, 6},
                {8, 7, 6, 0},
                {4, 6, 5, 8}
        };

        assertEquals(8, MineClimbing.best_distance(map, 0, 1, 2, 3));
    }

    @Test(timeout = 3000)
    @Grade(value = 4.0)
    public void almostflat() {
        int[][] map = new int[][] {
                {0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };

        assertEquals(1, MineClimbing.best_distance(map, 1, 1, 4, 4));
    }

    @Test(timeout = 3000)
    @Grade(value = 4.0)
    public void border() {
        int[][] map = new int[][] {
                {9, 9, 1, 9, 2},
                {9, 1, 2, 9, 1},
                {1, 2, 9, 9, 2},
                {9, 9, 2, 1, 9},
                {2, 1, 9, 9, 1}
        };

        assertEquals(10, MineClimbing.best_distance(map, 0, 2, 4, 1));
    }

    @Test(timeout = 3000)
    @Grade(value = 4.0)
    public void big() {
        int[][] map = new int[][]{
                {6, 6, 8, 1, 2, 5, 1, 3, 7, 7, 1, 1, 3, 6, 6, 5, 2, 5, 4, 6, 7, 7, 8, 1, 7},
                {1, 8, 5, 1, 4, 3, 2, 3, 1, 8, 7, 3, 0, 5, 4, 3, 3, 0, 8, 3, 8, 3, 3, 7, 4},
                {8, 4, 4, 4, 8, 7, 2, 3, 4, 1, 5, 6, 5, 4, 7, 0, 8, 3, 6, 7, 7, 4, 2, 1, 2},
                {5, 1, 7, 0, 0, 7, 7, 0, 1, 7, 0, 4, 0, 1, 3, 7, 8, 3, 0, 7, 5, 8, 1, 7, 8},
                {5, 8, 5, 7, 8, 5, 7, 7, 4, 5, 1, 0, 7, 7, 7, 3, 2, 1, 6, 2, 3, 5, 0, 2, 4},
                {0, 1, 6, 5, 6, 3, 5, 1, 3, 4, 0, 1, 2, 8, 2, 7, 8, 2, 2, 6, 1, 0, 0, 8, 4},
                {3, 8, 6, 6, 8, 1, 0, 8, 7, 7, 7, 0, 8, 3, 8, 5, 6, 5, 5, 4, 1, 2, 6, 6, 3},
                {1, 4, 4, 0, 2, 8, 2, 0, 4, 6, 0, 6, 1, 8, 4, 1, 2, 1, 4, 3, 1, 4, 3, 0, 5},
                {4, 2, 7, 3, 8, 5, 1, 2, 0, 5, 4, 3, 8, 7, 3, 5, 2, 0, 7, 5, 6, 1, 6, 8, 5},
                {5, 2, 6, 4, 4, 3, 4, 0, 0, 6, 4, 8, 7, 3, 5, 7, 4, 5, 8, 8, 6, 7, 1, 4, 4},
                {7, 5, 0, 7, 8, 0, 8, 7, 3, 8, 8, 7, 7, 7, 3, 3, 5, 0, 3, 0, 6, 6, 2, 8, 3},
                {4, 1, 2, 6, 6, 2, 1, 8, 4, 3, 8, 6, 0, 7, 4, 7, 5, 7, 6, 8, 6, 1, 0, 6, 5},
                {2, 1, 3, 2, 0, 4, 1, 5, 2, 6, 1, 2, 8, 5, 3, 8, 5, 8, 6, 5, 7, 5, 4, 0, 2},
                {1, 5, 8, 4, 8, 0, 0, 1, 8, 5, 6, 4, 6, 4, 4, 1, 4, 3, 6, 5, 3, 2, 0, 3, 3},
                {2, 4, 1, 1, 3, 8, 8, 1, 1, 1, 5, 3, 0, 7, 8, 1, 3, 7, 0, 5, 1, 2, 5, 0, 3},
                {0, 3, 7, 1, 7, 6, 7, 1, 8, 8, 2, 2, 2, 2, 5, 6, 4, 7, 7, 8, 8, 4, 8, 2, 4},
                {3, 0, 4, 6, 5, 3, 8, 2, 8, 3, 0, 0, 0, 5, 8, 1, 7, 6, 7, 8, 0, 5, 5, 1, 1},
                {6, 4, 4, 6, 8, 7, 1, 1, 6, 6, 3, 3, 0, 6, 1, 0, 3, 8, 4, 4, 4, 2, 4, 3, 4},
                {8, 0, 5, 4, 1, 5, 7, 8, 5, 8, 0, 2, 6, 5, 5, 6, 6, 6, 3, 0, 1, 7, 1, 7, 5},
                {7, 7, 1, 3, 6, 3, 6, 2, 7, 0, 8, 3, 4, 0, 7, 3, 2, 6, 5, 2, 3, 3, 7, 4, 2},
                {3, 1, 5, 0, 4, 4, 6, 2, 7, 4, 1, 1, 8, 2, 7, 0, 2, 2, 8, 0, 3, 0, 7, 1, 4},
                {6, 0, 3, 0, 0, 8, 1, 5, 5, 1, 3, 5, 8, 2, 8, 3, 4, 5, 6, 7, 0, 0, 7, 5, 7},
                {8, 5, 2, 3, 8, 6, 1, 6, 3, 4, 2, 3, 2, 5, 1, 8, 8, 4, 3, 3, 5, 5, 6, 3, 3},
                {1, 8, 2, 5, 4, 4, 1, 2, 4, 8, 7, 1, 5, 7, 2, 4, 2, 3, 4, 6, 8, 5, 3, 2, 1},
                {0, 1, 7, 1, 4, 6, 3, 2, 7, 4, 1, 6, 7, 4, 1, 5, 5, 3, 8, 3, 8, 1, 7, 0, 6}
        };
        assertEquals(30, MineClimbing.best_distance(map, 5, 5, 18, 18));
    }

    @Test(timeout = 3000)
    @Grade(value = 4.0)
    public void steps() {
        int[][] map = new int[][] {
                {6, 5, 4, 3, 4, 5, 6},
                {5, 4, 3, 2, 3, 4, 5},
                {4, 3, 2, 1, 2, 3, 4},
                {3, 2, 1, 0, 1, 2, 3},
                {4, 3, 2, 1, 2, 3, 4},
                {5, 4, 3, 2, 3, 4, 5},
                {6, 5, 4, 3, 4, 5, 6},
        };

        assertEquals(6, MineClimbing.best_distance(map, 3, 3, 0, 0));
    }

    @Test(timeout = 3000)
    @Grade(value = 4.0)
    public void increasing() {
        int[][] map = new int[][] {
                { 46,-40, 30,-22, 31,-41, 47},
                {-39, 29,-14,  9,-15, 32,-42},
                { 28,-13,  5, -2,  6,-16, 33},
                {-21, 12, -5,  1, -3, 10,-23},
                { 27,-20,  8, -4,  7,-17, 34},
                {-38, 26,-19, 11,-18, 35,-43},
                { 45,-37, 25,-24, 36,-44, 48},
        };

        assertEquals(221, MineClimbing.best_distance(map, 3, 3, 0, 0));
    }

    @Test(timeout = 15000)
    @Grade(value = 20)
    public void correctness1() {
        int SIZE = 60;
        Random rand = new Random(56782);
        for(int t = 0; t < 20; t++) {
            int[][] map = new int[SIZE][SIZE];
            for(int j = 0; j < SIZE; j++)
                for(int k = 0; k < SIZE; k++)
                    map[j][k] = rand.nextInt(1000);
            //never cross borders
            for(int j = 0; j < SIZE; j++) {
                map[j][0] = 1000000;
                map[j][SIZE-1] = 1000000;
                map[0][j] = 1000000;
                map[SIZE-1][j] = 1000000;
            }

            int startX = rand.nextInt(SIZE-3) + 1;
            int startY = rand.nextInt(SIZE-3) + 1;
            int endX = rand.nextInt(SIZE-3) + 1;
            int endY = rand.nextInt(SIZE-3) + 1;


            assertEquals(MineClimbingCorrect.best_distance(map, startX, startY, endX, endY),
                    MineClimbing.best_distance(map, startX, startY, endX, endY));
        }
    }

    @Test(timeout = 15000)
    @Grade(value = 15)
    public void correctness2() {
        int SIZE = 60;
        Random rand = new Random(56782);
        for(int t = 0; t < 20; t++) {
            int[][] map = new int[SIZE][SIZE];
            for(int j = 0; j < SIZE; j++)
                for(int k = 0; k < SIZE; k++)
                    map[j][k] = rand.nextInt(1000);

            int startX = rand.nextInt(SIZE-3) + 1;
            int startY = rand.nextInt(SIZE-3) + 1;
            int endX = rand.nextInt(SIZE-3) + 1;
            int endY = rand.nextInt(SIZE-3) + 1;


            assertEquals(MineClimbingCorrect.best_distance(map, startX, startY, endX, endY),
                    MineClimbing.best_distance(map, startX, startY, endX, endY));
        }
    }

    @Test(timeout = 10000)
    @Grade(value = 10.0)
    public void complexity1() {
        Random rand = new Random(56782);
        for(int i = 0; i < 10; i++) {
            int[][] map = new int[1000][1000];
            for(int j = 0; j < 1000; j++)
                for(int k = 0; k < 1000; k++)
                    map[j][k] = 10 + rand.nextInt(100);

            int startX = rand.nextInt(800) + 100;
            int startY = rand.nextInt(800) + 100;
            for(int j = 0; j < 100; j++)
                for(int k = 0; k < 100; k++)
                    map[j+startX][k+startY] = rand.nextInt(10);

            assertEquals(MineClimbingCorrect.best_distance(map, startX+1, startY+1, startX+99, startY+99),
                    MineClimbing.best_distance(map, startX+1, startY+1, startX+99, startY+99));
        }
    }

    @Test(timeout = 30000)
    @Grade(value = 30.0)
    public void complexity2() {
        Random rand = new Random(56782);
        int SIZE = 600;
        for(int i = 0; i < 10; i++) {
            int[][] map = new int[SIZE][SIZE];
            for(int j = 0; j < SIZE; j++)
                for(int k = 0; k < SIZE; k++)
                    map[j][k] = rand.nextInt(10000);

            int startX = rand.nextInt(SIZE-1);
            int startY = rand.nextInt(SIZE-1);
            int endX = rand.nextInt(SIZE-1);
            int endY = rand.nextInt(SIZE-1);

            assertEquals(MineClimbingCorrect.best_distance(map, startX, startY, endX, endY),
                    MineClimbing.best_distance(map, startX, startY, endX, endY));
        }
    }
}
