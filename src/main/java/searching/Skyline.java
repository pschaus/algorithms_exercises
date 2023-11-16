package searching;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * In this exercise, you must compute the skyline defined by a set of buildings.
 * When viewed from far away, the buildings only appear as if they were on a two-dimensionnal line.
 * Hence, they can be defined by three integers: The start of the building (left side), the height
 * of the building and the end of the building (right side).
 * For example, a building defined by (2, 5, 4) would look like
 *
 *   xxx
 *   xxx
 *   xxx
 *   xxx
 *   xxx
 * ________
 *
 * Obviously in practice buildings are not on a line, so they can overlap. If we add a new, smaller,
 * building in front of the previous one, defined by (3, 3, 6), then the view looks like:
 *
 *   xxx
 *   xxx
 *   xyyyy
 *   xyyyy
 *   xyyyy
 * ________
 *
 * The skyline is then define as the line that follows the highest building at any given points.
 * Visually, for the above example, it gives:
 *
 *   sss
 *      
 *      ss
 *        
 *        
 * ________
 *
 *
 * We ask you to compute, gien a set of building, their skyline.
 */
public class Skyline {

    // BEGIN STRIP
    public static class BuildingPoint implements Comparable<BuildingPoint> {
        int x;
        boolean isStart;
        int height;

        /**
         *
         * @param x the x coordinate of a building point
         * @param isStart whether the point is a start point or an end point
         * @param height the height of the building point
         */
        public BuildingPoint(int x, boolean isStart, int height) {
            this.x = x;
            this.isStart = isStart;
            this.height = height;
        }

        @Override
        public int compareTo(BuildingPoint other) {
            if (this.x != other.x) {
                return this.x - other.x;
            } else {
                // always put start points before end points
                // if both are start points, order by decreasing height
                // if both are end points, order by increasing height
                return (this.isStart ? -this.height : this.height) - (other.isStart ? -other.height : other.height);
            }
        }
    }
	// END STRIP

    /**
     *   The buildings are defined with triplets (left, height, right).
     *         int[][] buildings = {{1, 11, 5}, {2, 6, 7}, {3, 13, 9}, {12, 7, 16}, {14, 3, 25}, {19, 18, 22}, {23, 13, 29}, {24, 4, 28}};
     *
     *         [{1,11},{3,13},{9,0},{12,7},{16,3},{19,18},{22,3},{23,13},{29,0}]
     *
     * @param buildings
     * @return  the skyline in the form of a list of "key points [x, height]".
     *          A key point is the left endpoint of a horizontal line segment.
     *          The key points are sorted by their x-coordinate in the list.
     */
    public static List<int[]> getSkyline(int[][] buildings) {
		// STUDENT return null;
		// BEGIN STRIP
        // Create a list of points.
        BuildingPoint[] points = new BuildingPoint[buildings.length * 2];
        int index = 0;
        for (int[] building : buildings) {
            points[index] = new BuildingPoint(building[0], true, building[1]);
            points[index + 1] = new BuildingPoint(building[2], false, building[1]);
            index += 2;
        }

        Arrays.sort(points); // Sort the points.

        // Use a tree map to represent the active buildings.
        TreeMap<Integer, Integer> queue = new TreeMap<>();
        queue.put(0, 1); // Add a ground level (height 0).
        int prevMaxHeight = 0;

        List<int[]> result = new ArrayList<>();

        for (BuildingPoint point : points) {
            if (point.isStart) {
                // If it's a start point, add the height to the map, or increment the existing height's count.
                queue.compute(point.height, (key, value) -> {
                    if (value != null) return value + 1;
                    return 1;
                });
            } else {
                // If it's an end point, decrement or remove the height from the map.
                queue.compute(point.height, (key, value) -> {
                    if (value == 1) return null;
                    return value - 1;
                });
            }
            // Get current max height after the addition/removal above.
            int currentMaxHeight = queue.lastKey();

            // If the current max height is different from the previous one, we have a critical point.
            if (prevMaxHeight != currentMaxHeight) {
                result.add(new int[]{point.x, currentMaxHeight});
                prevMaxHeight = currentMaxHeight;
            }
        }

        return result;
		// END STRIP
    }

    // BEGIN STRIP
    public static void main(String[] args) {
        // The buildings are defined with triplets (left, height, right).
        int[][] buildings = {{1, 11, 5}, {2, 6, 7}, {3, 13, 9}, {12, 7, 16}, {14, 3, 25}, {19, 18, 22}, {23, 13, 29}, {24, 4, 28}};

        // Get the skyline silhouette represented by a list of x coordinates and height.
        List<int[]> skyline = getSkyline(buildings);

        String skylineString = skyline.stream()
                .map(pair -> String.format("{%d,%d}", pair[0], pair[1]))
                .collect(Collectors.joining(",", "[", "]"));
        System.out.println(skylineString);

        // Output the skyline.
        for (int[] rect : skyline) {
            System.out.println("X coordinate: " + rect[0] + ", Height: " + rect[1]);
        }
    }
    // END STRIP
}
