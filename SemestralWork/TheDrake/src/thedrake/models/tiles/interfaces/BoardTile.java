package thedrake.models.tiles.interfaces;

import thedrake.models.positions.boards.BoardPos;
import thedrake.models.states.GameState;
import thedrake.models.moves.interfaces.Move;
import thedrake.models.interfaces.JSONSerializable;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

/**
 * The `BoardTile` interface represents different types of tiles on the game board in The Drake.
 * It extends the `Tile` interface and includes constants for common board tile types.
 */
public interface BoardTile extends Tile, JSONSerializable {

    /**
     * Represents an empty board tile where troops can step on.
     */
    BoardTile EMPTY = new BoardTile() {
        /**
         * Converts the empty board tile to a JSON string and writes it using the provided PrintWriter.
         * The JSON output for an empty tile is the string "empty".
         *
         * Example of JSON output:
         * "empty"
         *
         * @param writer the PrintWriter used to output the JSON representation of the BoardTile
         */
        public void toJSON(PrintWriter writer) {
            writer.printf("\"%s\"", this);
        }

        @Override
        public boolean canStepOn() {
            return true;
        }

        @Override
        public boolean hasTroop() {
            return false;
        }

        @Override
        public List<Move> movesFrom(BoardPos pos, GameState state) {
            return Collections.emptyList();
        }

        @Override
        public String toString() {
            return "empty";
        }
    };

    /**
     * Represents a mountain board tile where troops cannot step on.
     */
    BoardTile MOUNTAIN = new BoardTile() {
        /**
         * Converts the mountain board tile to a JSON string and writes it using the provided PrintWriter.
         * The JSON output for a mountain tile is the string "mountain".
         *
         * Example of JSON output:
         * "mountain"
         *
         * @param writer the PrintWriter used to output the JSON representation of the BoardTile
         */
        public void toJSON(PrintWriter writer) {
            writer.printf("\"%s\"", this);
        }

        @Override
        public boolean canStepOn() {
            return false;
        }

        @Override
        public boolean hasTroop() {
            return false;
        }

        @Override
        public List<Move> movesFrom(BoardPos pos, GameState state) {
            return Collections.emptyList();
        }

        @Override
        public String toString() {
            return "mountain";
        }
    };
}
