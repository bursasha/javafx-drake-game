package thedrake.models.positions.interfaces;

import thedrake.models.positions.boards.Offset2D;
import thedrake.models.boards.PlayingSide;
import thedrake.models.interfaces.JSONSerializable;

import java.io.PrintWriter;
import java.util.List;

/**
 * The `TilePos` interface represents a position on the game board in The Drake.
 * It provides methods for accessing and manipulating the position, as well as determining neighbors.
 */
public interface TilePos extends JSONSerializable {

    /**
     * Represents a position that is off the game board.
     */
    public static final TilePos OFF_BOARD = new TilePos() {
        /**
         * Serializes the OFF_BOARD position to a JSON string representation.
         * The method outputs the string "off-board", indicating that the position is not on the game board.
         *
         * Example of JSON output:
         * "off-board"
         *
         * @param writer the PrintWriter used to write the JSON representation of the OFF_BOARD position
         */
        public void toJSON(PrintWriter writer) {
            writer.printf("\"%s\"", this);
        }

        @Override
        public int i() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int j() {
            throw new UnsupportedOperationException();
        }

        @Override
        public char column() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int row() {
            throw new UnsupportedOperationException();
        }

        @Override
        public TilePos step(int columnStep, int rowStep) {
            throw new UnsupportedOperationException();
        }

        @Override
        public TilePos step(Offset2D step) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<TilePos> neighbours() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isNextTo(TilePos pos) {
            throw new UnsupportedOperationException();
        }

        @Override
        public TilePos stepByPlayingSide(Offset2D dir, PlayingSide side) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equalsTo(int i, int j) {
            return false;
        }

        @Override
        public String toString() {
            return "off-board";
        }
    };

    /**
     * Serializes the TilePos to a JSON object representation and writes it using the provided PrintWriter.
     * The JSON output depends on the specific implementation of the TilePos interface.
     * For example, a board position might be serialized to its string representation like "a1" or "d4".
     *
     * Example of JSON output for a typical board position:
     * "a1" or "d4"
     *
     * @param writer the PrintWriter used to output the JSON representation of the TilePos
     */
    public void toJSON(PrintWriter writer);

    /**
     * Gets the vertical index of the position.
     *
     * @return the vertical index
     */
    public int i();

    /**
     * Gets the horizontal index of the position.
     *
     * @return the horizontal index
     */
    public int j();

    /**
     * Gets the column representation of the position.
     *
     * @return the column character
     */
    public char column();

    /**
     * Gets the row representation of the position.
     *
     * @return the row index
     */
    public int row();

    /**
     * Moves the position by the specified column and row steps.
     *
     * @param columnStep the column step
     * @param rowStep    the row step
     * @return           a new TilePos after the move
     */
    public TilePos step(int columnStep, int rowStep);

    /**
     * Moves the position by the specified offset.
     *
     * @param step the offset
     * @return     a new TilePos after the move
     */
    public TilePos step(Offset2D step);

    /**
     * Gets a list of neighboring positions.
     *
     * @return a list of neighboring TilePos
     */
    public List<? extends TilePos> neighbours();

    /**
     * Checks if the position is next to the specified position.
     *
     * @param pos the target position
     * @return    true if the positions are next to each other, false otherwise
     */
    public boolean isNextTo(TilePos pos);

    /**
     * Moves the position in a direction based on the playing side.
     *
     * @param dir  the direction offset
     * @param side the playing side
     * @return     a new TilePos after the move
     */
    public TilePos stepByPlayingSide(Offset2D dir, PlayingSide side);

    /**
     * Checks whether the position is equal to the specified coordinates.
     *
     * @param i the vertical index to compare with
     * @param j the horizontal index to compare with
     * @return  true if the position equals the specified coordinates, false otherwise
     */
    public boolean equalsTo(int i, int j);
}
