package graphs;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;



import java.util.*;
// BEGIN STRIP
import java.io.*;
import java.util.stream.Stream;
// END STRIP
@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
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

    // BEGIN STRIP
    private static int[][] getMaze(String filename, int row, int col, int positions[][]) {
        String line;
        int[][] maze = new int[row][col];
        try {

            BufferedReader br = new BufferedReader(new FileReader(filename));
            if (!br.ready()) {
                br.close();
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
    // END STRIP

    @Test
    @Grade(value = 1)
    @Order(1)
    public void testMaze1a() {
        Iterable<Integer> path = Maze.shortestPath(maze1, 0, 0, 6, 0);
        Integer[] pathArray = toArray(path);
        assertTrue(validPathSourceToDest(0, 0, 6, 0, maze1, path));
        assertEquals(15, pathArray.length);
    }

    @Test
    @Grade(value = 1)
    @Order(1)
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
    @Grade(value = 1)
    @Order(1)
    public void testMaze1c() {
        Iterable<Integer> path = Maze.shortestPath(maze1, 0, 0, 0, 0);
        Integer[] pathArray = toArray(path);
        assertTrue(validPathSourceToDest(0, 0, 0, 0, maze1, path));
        assertEquals(1, pathArray.length);
    }

    @Test
    @Grade(value = 1)
    @Order(1)
    public void testMaze2a() {
        Iterable<Integer> path = Maze.shortestPath(maze2, 0, 0, 1, 6);
        Integer[] pathArray = toArray(path);
        assertTrue(validPathSourceToDest(0, 0, 1, 6, maze2, path));
        assertEquals(10, pathArray.length);
    }

    // BEGIN STRIP
    static Stream<Instance> dataProvider() {
        return Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).flatMap(i -> {
            return Stream.of(
                new Instance("data/graphs.Maze/in_100_" + i),
                new Instance("data/graphs.Maze/in_200_" + i),
                new Instance("data/graphs.Maze/in_500_" + i));
        });
    }

    @ParameterizedTest
    @Grade(value = 1, cpuTimeout = 1000)
    @MethodSource("dataProvider")
    public void testParameterized(Instance instance) {
        int[][] maze = instance.maze;

        for (int i = 0; i < instance.nQueries; i++) {
            ArrayList<Integer> query = instance.queries.get(i);
            int x1 = query.get(0), y1 = query.get(1), x2 = query.get(2), y2 = query.get(3);
            Iterable<Integer> path = Maze.shortestPath(maze, x1, y1, x2, y2);
            Integer[] pathArray = toArray(path);
            assertEquals(pathArray.length, instance.solutions.get(i).toArray().length);
            if (pathArray.length > 0){
                assertTrue(validPathSourceToDest(x1, y1, x2, y2, maze, path));
            }
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout = 20)
    @Order(2)
    public void testComplexity() {
        int positions[][] = new int[2][2];
        int[][] maze = getMaze("data/graphs.Maze/in_0", 24, 110, positions);
        Maze.shortestPath(maze, positions[0][0], positions[0][1], positions[1][0], positions[1][1]);
    }
    // END STRIP

    public static boolean validPathSourceToDest(int x1, int y1, int x2, int y2, int[][] maze, Iterable<Integer> path) {
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

    // BEGIN STRIP
    static class Instance {

        int[][] maze;
        List<ArrayList<Integer>> queries;
        List<ArrayList<Integer>> solutions;

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

                queries = new ArrayList<ArrayList<Integer>>(nQueries);
                solutions = new ArrayList<ArrayList<Integer>>(nQueries);
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
            }
        }
    }
    // END STRIP
}
