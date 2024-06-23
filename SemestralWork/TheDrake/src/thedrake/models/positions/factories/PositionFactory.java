package thedrake.models.positions.factories;

import thedrake.models.positions.boards.BoardPos;

/**
 * The `PositionFactory` class provides methods for creating instances of `BoardPos` and handling board positions.
 */
public class PositionFactory {

    /**
     * The dimension of the game board.
     */
    private final int dimension;

    /**
     * Creates a new `PositionFactory` with the specified dimension.
     *
     * @param dimension                 the dimension of the game board
     * @throws IllegalArgumentException if the dimension is not positive
     */
    public PositionFactory(int dimension) {
        if (dimension <= 0)
            throw new IllegalArgumentException("The dimension needs to be positive.");

        this.dimension = dimension;
    }

    /**
     * Gets the dimension of the game board.
     *
     * @return the dimension of the game board
     */
    public int dimension() {
        return this.dimension;
    }

    /**
     * Creates a new `BoardPos` instance with the specified vertical and horizontal indices.
     *
     * @param i the vertical index
     * @param j the horizontal index
     * @return  a new `BoardPos` instance
     */
    public BoardPos pos(int i, int j) {
        return new BoardPos(this.dimension, i, j);
    }

    /**
     * Creates a new `BoardPos` instance with the specified column and row indices.
     *
     * @param column the column character
     * @param row    the row index
     * @return       a new `BoardPos` instance
     */
    public BoardPos pos(char column, int row) {
        return this.pos(this.iFromColumn(column), this.jFromRow(row));
    }

    /**
     * Creates a new `BoardPos` instance with the specified position string.
     *
     * @param pos the position string (e.g., "a1")
     * @return    a new `BoardPos` instance
     */
    public BoardPos pos(String pos) {
        return this.pos(pos.charAt(0), Integer.parseInt(pos.substring(1)));
    }

    /**
     * Converts a column character to a vertical index.
     *
     * @param column the column character
     * @return       the vertical index
     */
    private int iFromColumn(char column) {
        return column - 'a';
    }

    /**
     * Converts a row index to a horizontal index.
     *
     * @param row the row index
     * @return    the horizontal index
     */
    private int jFromRow(int row) {
        return row - 1;
    }
}
