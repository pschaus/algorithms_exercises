package utils;

import java.util.List;

public abstract class GlobalWarming {

    public final int[][] altitude;
    public final int waterLevel;


    /**
     * In the following, we assume that the points are connected to
     * horizontal or vertical neighbors but not to the diagonal ones
     * @param altitude is a n x n matrix of int values representing altitudes (positive or negative)
     * @param waterLevel is the water level, every entry <= waterLevel is flooded
     */
    public GlobalWarming(int[][] altitude, int waterLevel) {
        this.altitude = altitude;
        this.waterLevel = waterLevel;
    }


    /**
     *
     * @param p1 a safe point with valid coordinates on altitude matrix
     * @param p2 a safe point (different from p1) with valid coordinates on altitude matrix
     * @return the shortest simple path (vertical/horizontal moves) if any between from p1 to p2 using only vertical/horizontal moves on safe points.
     *         an empty list if not path exists (i.e. p1 and p2 are not on the same island).
     */
    public abstract List<Point> shortestPath(Point p1, Point p2);


}
