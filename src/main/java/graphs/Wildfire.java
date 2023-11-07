package graphs;

import java.util.LinkedList;
import java.util.Queue;


/**
 * Let's consider a forest represented as a 2D grid.
 * Each cell of the grid can be in one of three states:
 *
 * 0 representing an empty spot.
 * 1 representing a tree.
 * 2 representing a burning tree (indicating a wildfire).
 *
 * The fire spreads from a burning tree to its four neighboring cells (up, down, left, and right) if there's a tree there.
 * Each minute, the trees in the neighboring cells of burning tree catch on fire.
 *
 * Your task is to calculate how many minutes it would take for the fire to spread throughout the forest i.e. to burn all the trees.
 *
 * If there are trees that cannot be reached by the fire (for example, isolated trees with no adjacent burning trees),
 * we consider that the fire will never reach them and -1 is returned.
 *
 * The time-complexity of your algorithm must be O(n) with n the number of cells in the forest.
 */
public class Wildfire {

    static final int EMPTY = 0;
    static final int TREE = 1;
    static final int BURNING = 2;

    // BEGIN STRIP
    class Cell {
        int x, y, time;

        Cell(int x, int y, int time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }
    }
    // END STRIP

    /**
     * This method calculates how many minutes it would take for the fire to spread throughout the forest.
     *
     * @param forest
     * @return the number of minutes it would take for the fire to spread throughout the forest,
     *         -1 if the forest cannot be completely burned.
     */
    public int burnForest(int [][] forest) {
        // TODO
        // STUDENT return 0;
        // BEGIN STRIP

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        int rows = forest.length, cols = forest[0].length;
        Queue<Cell> queue = new LinkedList<>();
        int timeToBurn = 0;

        // Enqueue all burning trees and mark them as visited
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (forest[i][j] == BURNING) {
                    queue.offer(new Cell(i, j, 0));
                }
            }
        }

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            timeToBurn = cell.time;

            for (int[] dir : directions) {
                int newX = cell.x + dir[0];
                int newY = cell.y + dir[1];

                // If new coordinates are valid and tree is present
                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && forest[newX][newY] == TREE) {
                    // burn the tree and enqueue it
                    forest[newX][newY] = BURNING;
                    queue.offer(new Cell(newX, newY, timeToBurn + 1));
                }
            }
        }

        // Check if there are any trees left that couldn't be reached by the fire
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (forest[i][j] == TREE) {
                    return -1; // fire couldn't reach all trees
                }
            }
        }

        return timeToBurn;
        // END STRIP
    }
}