package thedrake.models.positions.boards;

import thedrake.models.boards.PlayingSide;
import thedrake.models.positions.interfaces.TilePos;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * The `BoardPos` class represents a position on the game board in The Drake.
 * It implements the `TilePos` interface and provides methods for accessing and manipulating the position.
 */
public class BoardPos implements TilePos {
    /**
     * The dimension of the game board.
     */
    private final int dimension;

    /**
     * The vertical index of the position.
     */
    private final int i;

    /**
     * The horizontal index of the position.
     */
    private final int j;

    /**
     * Creates a new BoardPos with the specified dimension, vertical index, and horizontal index.
     *
     * @param dimension the dimension of the game board
     * @param i         the vertical index
     * @param j         the horizontal index
     */
    public BoardPos(int dimension, int i, int j) {
        this.dimension = dimension;
        this.i = i;
        this.j = j;
    }

    /**
     * Converts the board position to a JSON string and writes it using the provided PrintWriter.
     * The JSON output is a string representing the position in the format of 'column' + 'row' (e.g., "a1").
     *
     * Example of JSON output:
     * "a1"
     *
     * @param writer the PrintWriter used to output the JSON representation of the BoardPos
     */
    @Override
    public void toJSON(PrintWriter writer) {
        writer.printf("\"%s\"", this);
    }

    /**
     * Gets the vertical index of the position.
     *
     * @return the vertical index
     */
    @Override
    public int i() {
        return this.i;
    }

    /**
     * Gets the horizontal index of the position.
     *
     * @return the horizontal index
     */
    @Override
    public int j() {
        return this.j;
    }

    /**
     * Gets the column representation of the position.
     *
     * @return the column character
     */
    @Override
    public char column() {
        return (char) ('a' + this.i);
    }

    /**
     * Gets the row representation of the position.
     *
     * @return the row index
     */
    @Override
    public int row() {
        return this.j + 1;
    }

    /**
     * Moves the position by the specified column and row steps.
     *
     * @param columnStep the column step
     * @param rowStep    the row step
     * @return           a new BoardPos after the move, or TilePos.OFF_BOARD if the move is out of bounds
     */
    @Override
    public TilePos step(int columnStep, int rowStep) {
        int newi = i + columnStep;
        int newj = j + rowStep;

        if ((newi >= 0 && newi < this.dimension) && (newj >= 0 && newj < this.dimension)) {
            return new BoardPos(this.dimension, newi, newj);
        }

        return TilePos.OFF_BOARD;
    }

    /**
     * Moves the position by the specified offset.
     *
     * @param step the offset
     * @return     a new BoardPos after the move, or TilePos.OFF_BOARD if the move is out of bounds
     */
    @Override
    public TilePos step(Offset2D step) {
        return step(step.x, step.y);
    }

    /**
     * Gets a list of neighboring positions.
     *
     * @return a list of neighboring BoardPos
     */
    @Override
    public List<BoardPos> neighbours() {
        List<BoardPos> result = new ArrayList<>();

        TilePos pos = step(1, 0);
        if (!pos.equals(TilePos.OFF_BOARD))
            result.add((BoardPos) pos);

        pos = step(-1, 0);
        if (!pos.equals(TilePos.OFF_BOARD))
            result.add((BoardPos) pos);

        pos = step(0, 1);
        if (!pos.equals(TilePos.OFF_BOARD))
            result.add((BoardPos) pos);

        pos = step(0, -1);
        if (!pos.equals(TilePos.OFF_BOARD))
            result.add((BoardPos) pos);

        return result;
    }

    /**
     * Checks if the position is next to the specified position.
     *
     * @param pos the target position
     * @return    true if the positions are next to each other, false otherwise
     */
    @Override
    public boolean isNextTo(TilePos pos) {
        if (pos.equals(TilePos.OFF_BOARD))
            return false;

        if (this.i == pos.i() && Math.abs(this.j - pos.j()) == 1)
            return true;

        if (this.j == pos.j() && Math.abs(this.i - pos.i()) == 1)
            return true;

        return false;
    }

    /**
     * Moves the position in a direction based on the playing side.
     *
     * @param dir  the direction offset
     * @param side the playing side
     * @return     a new BoardPos after the move, or TilePos.OFF_BOARD if the move is out of bounds
     */
    @Override
    public TilePos stepByPlayingSide(Offset2D dir, PlayingSide side) {
        return side.equals(PlayingSide.BLUE) ? this.step(dir) : this.step(dir.yFlipped());
    }

    /**
     * Generates a hash code for the position.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + i;
        result = prime * result + j;
        return result;
    }

    /**
     * Checks if the position is equal to the specified coordinates.
     *
     * @param i the vertical index to compare with
     * @param j the horizontal index to compare with
     * @return  true if the position equals the specified coordinates, false otherwise
     */
    @Override
    public boolean equalsTo(int i, int j) {
        return this.i == i && this.j == j;
    }

    /**
     * Checks if the position is equal to another object.
     *
     * @param obj the object to compare with
     * @return    true if the positions are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        BoardPos other = (BoardPos) obj;
        if (this.i != other.i)
            return false;
        if (this.j != other.j)
            return false;

        return true;
    }

    /**
     * Gets a string representation of the position.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return String.format("%c%d", column(), row());
    }
}
