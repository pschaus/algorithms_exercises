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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;




import java.util.*;
@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class DarkMazeTests {

    public static int[][] maze1 = new int[][] {
            {1, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0,-1,-1},
            {0, 0, 0, 0, 0, 0, 0, 2}
    };

    public static int[][] maze2 = new int[][]{
            {1, 0, 0, 0, 0, 1, 0},
            {0,-1,-1, 0, 0, 0, 0},
            {0,-1, 0, 0,-1,-1,-1},
            {0, 0, 1, 0, 0,-1, 0},
            {0, 0, 0,-1, 0, 0, 0},
            {0, 0, 0, 0, 0,-1, 2},
    };

    public static int[][] maze3 = new int[][]{
            {1, 0, 0, 0, 0, 1, 0},
            {0,-1,-1, 0, 0, 0, 0},
            {0,-1, 0, 0,-1,-1,-1},
            {-1,0, 1, 0, 0,-1, 0},
            {0, 0, 0,-1, 0, 0, 0},
            {0, 0, 0, 0, 0,-1, 2},
    };

    public static int[][]  maze4 = {
            {1, 0, 0, 0, 0, 0, 2},
    };

    public static int[][]  maze5 = {
            {1,-1, 2},
    };


    @Test
    @Grade(value = 1)
    @Order(1)
    public void testMaze1() {
        Iterable<Integer> path = DarkMaze.findPath(0, 0, 2, 7, 8, maze1);
        assertNotNull(path);
        Integer[] pathArray = toArray(path);
        assertEquals(14, pathArray.length);
        assertTrue(validPathSourceToDest(0, 0, 2, 7, maze1, path));
    }

    @Test
    @Grade(value = 1)
    @Order(1)
    public void testMaze2() {
        Iterable<Integer> path1 = DarkMaze.findPath(0, 0, 5, 6, 8, maze2);
        assertNotNull(path1);
        Integer[] pathArray = toArray(path1);
        assertTrue(validPathSourceToDest(0, 0, 5, 6, maze2, path1));
        assertEquals(12, pathArray.length);
    }

    @Test
    @Grade(value = 1)
    @Order(1)
    public void testMaze3() {
        Iterable<Integer> path1 = DarkMaze.findPath(0, 0, 5, 6, 8, maze3);
        assertNotNull(path1);
        Integer[] pathArray = toArray(path1);
        assertTrue(validPathSourceToDest(0, 0, 5, 6, maze3, path1));
        assertEquals(14, pathArray.length);



        Iterable<Integer> path2 = DarkMaze.findPath(0, 0, 5, 6, 6, maze3);
        assertNotNull(path2);
        Integer[] pathArray2 = toArray(path2);
        assertTrue(validPathSourceToDest(0, 0, 5, 6, maze3, path2));
        assertEquals(18, pathArray2.length);
    }



    @Test
    @Grade(value = 1)
    @Order(1)
    public void testMazeUnfeasible() {
        Iterable<Integer> path1 = DarkMaze.findPath(0, 0, 0, 6, 1, maze4);
        assertNull(path1);

        Iterable<Integer> path2 = DarkMaze.findPath(0, 0, 0, 2, 6, maze5);
        assertNull(path2);

        Iterable<Integer> path3 = DarkMaze.findPath(0, 1, 0, 2, 6, maze5);
        assertNull(path3);
    }





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
            if (maze[x][y] == -1) return false;
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