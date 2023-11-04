package pt.upskill.projeto1.rogue.utils;

/**
 * 2D integer position.
 */
public class Position {

    private int x;
    private int y;

    /**
     * @param x horizontal coordinate
     * @param y vertical coordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return horizontal coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @return vertical coordinate
     */
    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    public double calculateEuclideanDistance(Position calculateDistance) {
        return Math.sqrt((calculateDistance.getY() - this.getY()) * (calculateDistance.getY() - this.getY()) + (calculateDistance.getX() - this.getX()) * (calculateDistance.getX() - this.getX()));
    }

    public Position plus(Vector2D vector2d) {
        int newX = getX() + vector2d.getX();
        int newY = getY() + vector2d.getY();
        return new Position(newX, newY);
    }

    public Position minus(Vector2D vector2d) {
        int newX = getX() - vector2d.getX();
        int newY = getY() - vector2d.getY();
        return new Position(newX, newY);
    }


    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}