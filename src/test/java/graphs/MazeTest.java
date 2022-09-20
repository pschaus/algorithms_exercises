package graphs;


import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.Assert.*;

import java.util.*;

public class MazeTest{

    public int [][] maze1 = new int[][] {
            {0,0,0,0,0,0,0},
            {1,1,0,0,0,0,0},
            {0,0,0,0,0,1,0},
            {0,1,1,1,1,1,1},
            {0,0,0,0,1,0,0},
            {1,1,0,0,1,0,0},
            {0,0,0,0,1,0,0}
    };

    public int [][] maze2 = new int[][] {
            {0,0,0,1,0,0,0},
            {1,1,0,0,0,1,0}
    };

    @Test
    @Grade(value=10)
    public void testMaze1a() {
        Iterable<Integer> path = Maze.shortestPath(maze1,0,0,6,0);
        Integer [] pathArray = toArray(path);
        assertTrue(validPathSourceToDest(0,0,6,0,maze1,path));
        assertTrue(pathArray.length == 15);
    }

    @Test
    @Grade(value=10)
    public void testMaze1b() {
        // should not have a path
        // unreachable destination
        assertTrue(!Maze.shortestPath(maze1,0,0,6,6).iterator().hasNext());
        // unreachable destination
        assertTrue(!Maze.shortestPath(maze1,6,6,0,0).iterator().hasNext());
        // start position is a wall
        assertTrue(!Maze.shortestPath(maze1,1,0,6,0).iterator().hasNext());
        // end position is a wall
        assertTrue(!Maze.shortestPath(maze1,6,0,1,0).iterator().hasNext());
    }

    @Test
    @Grade(value=20)
    public void testMaze1c() {
        Iterable<Integer> path = Maze.shortestPath(maze1,0,0,0,0);
        Integer [] pathArray = toArray(path);
        assertTrue(validPathSourceToDest(0,0,0,0,maze1,path));
        assertTrue(pathArray.length == 1);
    }

    @Test
    @Grade(value=20)
    public void testMaze2a() {
        Iterable<Integer> path = Maze.shortestPath(maze2,0,0,1,6);
        Integer [] pathArray = toArray(path);
        assertTrue(validPathSourceToDest(0,0,1,6,maze2,path));
        assertTrue(pathArray.length == 10);
    }

    @Test (timeout = 20)
    @Grade(value=40)
    public void testComplexity() {
        int positions[][] = new int[2][2];
        int[][] maze = getMaze("data/graphs.Maze/in_0",24,110, positions);

        long t0 = System.currentTimeMillis();
        Iterable<Integer> path = Maze.shortestPath(maze, positions[0][0], positions[0][1], positions[1][0], positions[1][1]);
        long t1 = System.currentTimeMillis();

        int count = 0;
        for (Integer it: path) { count++; }
        //System.out.println(count);

        assertEquals(count, 125);

        //System.out.println("time constructor bis=:"+(t1-t0));
    }

    private int[][] getMaze(String filename, int row, int col, int positions[][]) {
        String line;
        int[][] maze = new int[row][col];
        try {

            BufferedReader br = new BufferedReader(new FileReader(filename));
            if (!br.ready()) {
                throw new IOException();
            }
            int j = 0;
            int pos = 0;
            while ((line = br.readLine()) != null) {
                for(int i = 0; i < line.length(); i++) {
                    try {
                        maze[j][i] = Integer.parseInt(line.charAt(i) + "");
                    } catch (NumberFormatException r) {
                        positions[pos][0] = j;
                        positions[pos][1] = i;
                        pos++;
                        ///System.out.println(j+" "+i);
                        maze[j][i] = 0;
                    }
                }
                j++;
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return maze;
    }


    public Integer[] toArray(Iterable<Integer> path) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        path.forEach(list::add);
        return list.toArray(new Integer[0]);
    }


    public static int row(int pos, int mCols) { return pos / mCols; }

    public static int col(int pos, int mCols) { return pos % mCols; }

    public boolean validPathSourceToDest(int x1, int y1, int x2, int y2, int [][] maze, Iterable<Integer> path) {
        int n = maze.length;
        int m = maze[0].length;
        Iterator<Integer> ite = path.iterator();
        if (!ite.hasNext()) return false;
        int p = ite.next();
        int x = row(p,m);
        int y = col(p,m);
        if (x != x1 || y != y1) return false;
        while (ite.hasNext()) {
            p = ite.next();
            int x_ = row(p,m);
            int y_ = col(p,m);
            if (maze[x][y] == 1) return false;
            if (Math.abs(x_-x)+Math.abs(y_-y) != 1) return false;
            x = x_; y = y_;
        }
        if (x != x2 || y != y2) return false;
        return true;
    }

}
