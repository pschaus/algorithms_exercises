package graphs;


import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.*;

import static org.junit.Assert.*;

import java.util.*;

@RunWith(Enclosed.class)
public class MazeTest {

    public static int[][] maze1 = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 1, 0, 0},
            {1, 1, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 0}
    };

    public static int[][] maze2 = new int[][]{
            {0, 0, 0, 1, 0, 0, 0},
            {1, 1, 0, 0, 0, 1, 0}
    };


    private static int[][] getMaze(String filename, int row, int col, int positions[][]) {
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
                for (int i = 0; i < line.length(); i++) {
                    try {
                        maze[j][i] = Integer.parseInt(line.charAt(i) + "");
                    } catch (NumberFormatException r) {
                        positions[pos][0] = j;
                        positions[pos][1] = i;
                        pos++;
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


    public static class TestNotParameterized {

        @Test
        @Grade(value = 10)
        public void testMaze1a() {
            Iterable<Integer> path = Maze.shortestPath(maze1, 0, 0, 6, 0);
            Integer[] pathArray = toArray(path);
            assertTrue(validPathSourceToDest(0, 0, 6, 0, maze1, path));
            assertEquals(15, pathArray.length);
        }

        @Test
        @Grade(value = 10)
        public void testMaze1b() {
            // should not have a path
            // unreachable destination
            assertFalse(Maze.shortestPath(maze1, 0, 0, 6, 6).iterator().hasNext());
            // unreachable destination
            assertFalse(Maze.shortestPath(maze1, 6, 6, 0, 0).iterator().hasNext());
            // start position is a wall
            assertFalse(Maze.shortestPath(maze1, 1, 0, 6, 0).iterator().hasNext());
            // end position is a wall
            assertFalse(Maze.shortestPath(maze1, 6, 0, 1, 0).iterator().hasNext());
        }

        @Test
        @Grade(value = 20)
        public void testMaze1c() {
            Iterable<Integer> path = Maze.shortestPath(maze1, 0, 0, 0, 0);
            Integer[] pathArray = toArray(path);
            assertTrue(validPathSourceToDest(0, 0, 0, 0, maze1, path));
            assertEquals(1, pathArray.length);
        }

        @Test
        @Grade(value = 20)
        public void testMaze2a() {
            Iterable<Integer> path = Maze.shortestPath(maze2, 0, 0, 1, 6);
            Integer[] pathArray = toArray(path);
            assertTrue(validPathSourceToDest(0, 0, 1, 6, maze2, path));
            assertEquals(10, pathArray.length);
        }

        @Test(timeout = 20)
        @Grade(value = 40)
        public void testComplexity() {
            int positions[][] = new int[2][2];
            int[][] maze = getMaze("data/graphs.Maze/in_0", 24, 110, positions);
            Iterable<Integer> path = Maze.shortestPath(maze, positions[0][0], positions[0][1], positions[1][0], positions[1][1]);
            int count = 0;
            for (Integer ignored : path) {
                count++;
            }
            assertEquals(count, 125);

        }
    }

    @RunWith(Parameterized.class)
    public static class TestParameterized {
        @Parameterized.Parameters(name = "{0}")
        public static Collection data() {
            LinkedList<Object[]> coll = new LinkedList<>();
            for (int i = 0; i < 10; i++) {
                for (int grid_size : new int[]{100, 200, 500}) {
                    String name = "data/graphs.Maze/in_" + grid_size + "_" + i;
                    coll.add(new Object[]{name, new Instance(name)});
                }

            }
            return coll;
        }


        public TestParameterized(String name, Instance instance) {
            this.instance = instance;
        }

        static class Instance {

            int[][] maze;
            List<ArrayList> queries;
            List<ArrayList> solutions;

            int nQueries;


            public Instance(String file) {

                try {
                    Scanner dis = new Scanner(new FileInputStream(file));
                    int size = dis.nextInt();
                    maze = new int[size][size];
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            maze[i][j] = dis.nextInt();
                        }
                    }
                    nQueries = dis.nextInt();
                    dis.nextLine();

                    queries = new ArrayList<ArrayList>(nQueries);
                    solutions = new ArrayList<ArrayList>(nQueries);
                    for (int i = 0; i < nQueries; i++) {
                        String q = dis.nextLine();
                        String[] qu = q.split(" ");
                        ArrayList<Integer> query = new ArrayList<>();

                        for (String v : qu) {
                            query.add(Integer.parseInt(v));
                        }
                        queries.add(query);

                        int sol_size = dis.nextInt();
                        if (sol_size == 0) {
                            solutions.add(new ArrayList<>());
                        } else {
                            ArrayList<Integer> path = new ArrayList<>();
                            for (int j = 0; j < sol_size; j++) {
                                path.add(dis.nextInt());
                            }
                            solutions.add(path);
                        }
                        dis.nextLine();

                    }


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        @Test
        @Grade(value = 1, cpuTimeout = 1000)
        public void test() throws Exception {
            int[][] maze = instance.maze;

            for (int i = 0; i < instance.nQueries; i++) {
                ArrayList<Integer> query = instance.queries.get(i);
                Iterable<Integer> path = Maze.shortestPath(maze, query.get(0), query.get(1), query.get(2), query.get(3));
                Integer[] pathArray = toArray(path);
                assertArrayEquals(pathArray, instance.solutions.get(i).toArray());
            }
        }
        final Instance instance;
    }

    public static boolean validPathSourceToDest(int x1, int y1, int x2, int y2, int[][] maze, Iterable<Integer> path) {
        int n = maze.length;
        int m = maze[0].length;
        Iterator<Integer> ite = path.iterator();
        if (!ite.hasNext()) return false;
        int p = ite.next();
        int x = row(p, m);
        int y = col(p, m);
        if (x != x1 || y != y1) return false;
        while (ite.hasNext()) {
            p = ite.next();
            int x_ = row(p, m);
            int y_ = col(p, m);
            if (maze[x][y] == 1) return false;
            if (Math.abs(x_ - x) + Math.abs(y_ - y) != 1) return false;
            x = x_;
            y = y_;
        }
        if (x != x2 || y != y2) return false;
        return true;
    }

    public static Integer[] toArray(Iterable<Integer> path) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        path.forEach(list::add);
        return list.toArray(new Integer[0]);
    }

    public static int row(int pos, int mCols) {
        return pos / mCols;
    }
    public static int col(int pos, int mCols) {
        return pos % mCols;
    }
}
