package thedrake.models.boards;

import thedrake.models.positions.factories.PositionFactory;
import thedrake.models.interfaces.JSONSerializable;
import thedrake.models.positions.boards.BoardPos;
import thedrake.models.positions.interfaces.TilePos;
import thedrake.models.tiles.interfaces.BoardTile;

import java.io.PrintWriter;
import java.util.Arrays;

/**
 * The `Board` class represents the game board in The Drake.
 * It includes methods for accessing board information and creating new board instances with updated tiles.
 */
public class Board implements JSONSerializable {

    /**
     * The dimension of the game board.
     */
    private final int dimension;

    /**
     * The 2D array of `BoardTile` representing the tiles on the board.
     */
    private final BoardTile[][] boardTiles;

    /**
     * Creates a new `Board` with the specified dimension.
     *
     * @param dimension the dimension of the game board
     */
    public Board(int dimension) {
        this.dimension = dimension;
        this.boardTiles = new BoardTile[dimension][dimension];

        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                this.boardTiles[i][j] = BoardTile.EMPTY;
    }

    /**
     * Converts the current state of the Board into a JSON format and writes it using the provided PrintWriter.
     * The JSON representation includes the dimension of the board and an array of its tiles.
     *
     * Example of JSON output:
     * {
     *   "dimension": 4,
     *   "tiles": ["EMPTY", "MOUNTAIN", "TROOPTILE", ...]
     * }
     *
     * @param writer the PrintWriter used to output the JSON representation of the Board
     */
    @Override
    public void toJSON(PrintWriter writer) {
        writer.printf("{");

        // Serialize the board dimension
        writer.printf("\"dimension\":%d,", this.dimension);

        // Serialize the board tiles
        writer.printf("\"tiles\":[");
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++) {
                this.boardTiles[j][i].toJSON(writer);

                if (!(i == this.dimension - 1 && j == this.dimension - 1))
                    writer.printf(",");
            }
        writer.printf("]");

        writer.printf("}");
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
     * Gets the `BoardTile` at the specified position.
     *
     * @param  pos the position to query
     * @return the `BoardTile` at the specified position
     */
    public BoardTile at(TilePos pos) {
        return this.boardTiles[pos.i()][pos.j()];
    }

    /**
     * Creates a new `Board` instance with updated tiles.
     *
     * @param  ats an array of `TileAt` objects representing the positions and tiles to be updated
     * @return a new `Board` instance with updated tiles
     */
    public Board withTiles(TileAt... ats) {
        Board newBoard = new Board(this.dimension);

        for (int i = 0; i < this.dimension; i++)
            newBoard.boardTiles[i] = this.boardTiles[i].clone();

        Arrays.stream(ats).forEach(at -> newBoard.boardTiles[at.pos.i()][at.pos.j()] = at.tile);

        return newBoard;
    }

    /**
     * Creates a new `PositionFactory` instance for the current board.
     *
     * @return a new `PositionFactory` instance
     */
    public PositionFactory positionFactory() {
        return new PositionFactory(this.dimension);
    }

    /**
     * The `TileAt` class represents a position and the corresponding `BoardTile` on the game board.
     */
    public static class TileAt {

        /**
         * The position on the board.
         */
        public final BoardPos pos;

        /**
         * The `BoardTile` at the specified position.
         */
        public final BoardTile tile;

        /**
         * Creates a new `TileAt` with the specified position and tile.
         *
         * @param pos the position on the board
         * @param tile the `BoardTile` at the specified position
         */
        public TileAt(BoardPos pos, BoardTile tile) {
            this.pos = pos;
            this.tile = tile;
        }
    }
}
