package utils;

/**
 * This class represent a point in a 2-dimension discrete plane. This is used, for instance, to
 * identified cells of a grid
 */
public class Point {
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
