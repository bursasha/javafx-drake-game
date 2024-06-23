package thedrake.models.positions.boards;

/**
 * The `Offset2D` class represents a two-dimensional offset or displacement on a plane.
 * It is used to denote the positions of pieces and changes in positions in the game The Drake.
 */
public class Offset2D {
    /**
     * The offset coordinate on the X-axis.
     */
    public final int x;

    /**
     * The offset coordinate on the Y-axis.
     */
    public final int y;

    /**
     * Creates a new offset with the given coordinates.
     *
     * @param x the offset coordinate on the X-axis
     * @param y the offset coordinate on the Y-axis
     */
    public Offset2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Checks whether the current offset is equal to another one given by coordinates.
     *
     * @param x the X-coordinate of the offset to compare with
     * @param y the Y-coordinate of the offset to compare with
     * @return  true if the offsets are identical, false otherwise
     */
    public boolean equalsTo(int x, int y) {
        return this.x == x && this.y == y;
    }

    /**
     * Returns a new offset with the Y-coordinate flipped (mirrored).
     *
     * @return a new offset where the Y-coordinate is negated
     */
    public Offset2D yFlipped() {
        return new Offset2D(this.x, -this.y);
    }
}
