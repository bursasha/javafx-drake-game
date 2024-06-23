package thedrake.models.tiles.troops;

import thedrake.models.actions.interfaces.TroopAction;
import thedrake.models.boards.PlayingSide;
import thedrake.models.interfaces.JSONSerializable;
import thedrake.models.moves.interfaces.Move;
import thedrake.models.positions.boards.BoardPos;
import thedrake.models.states.GameState;
import thedrake.models.tiles.interfaces.Tile;
import thedrake.models.troops.Troop;
import thedrake.models.troops.TroopFace;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * The `TroopTile` class represents a tile on the game board in The Drake that contains a troop.
 * It includes information about the troop, the side it belongs to, and its facing direction.
 */
public class TroopTile implements Tile, JSONSerializable {
    /**
     * The troop placed on the tile.
     */
    private final Troop troop;

    /**
     * The playing side to which the troop belongs (ORANGE or BLUE).
     */
    private final PlayingSide side;

    /**
     * The facing direction of the troop on the tile (AVERS or REVERS).
     */
    private final TroopFace face;

    /**
     * Creates a new TroopTile with the specified troop, side, and facing direction.
     *
     * @param troop the troop placed on the tile
     * @param side  the playing side to which the troop belongs (ORANGE or BLUE)
     * @param face  the facing direction of the troop on the tile (AVERS or REVERS)
     */
    public TroopTile(Troop troop, PlayingSide side, TroopFace face) {
        this.troop = troop;
        this.side = side;
        this.face = face;
    }

    /**
     * Serializes the TroopTile object into a JSON representation using the provided PrintWriter.
     * The JSON output includes the troop, playing side, and the facing direction of the troop on the tile.
     *
     * Example of JSON output:
     * {
     *   "troop": { ... },
     *   "side": "BLUE",
     *   "face": "AVERS"
     * }
     *
     * @param writer the PrintWriter to write the JSON string representation of the TroopTile
     */
    @Override
    public void toJSON(PrintWriter writer) {
        writer.printf("{");

        writer.printf("\"troop\":"); this.troop.toJSON(writer); writer.printf(",");
        writer.printf("\"side\":"); this.side.toJSON(writer); writer.printf(",");
        writer.printf("\"face\":"); this.face.toJSON(writer);

        writer.printf("}");
    }

    /**
     * Gets the troop placed on the tile.
     *
     * @return the troop on the tile
     */
    public Troop troop() {
        return this.troop;
    }

    /**
     * Gets the playing side to which the troop belongs.
     *
     * @return the playing side of the troop (ORANGE or BLUE)
     */
    public PlayingSide side() {
        return this.side;
    }

    /**
     * Gets the facing direction of the troop on the tile.
     *
     * @return the facing direction of the troop (AVERS or REVERS)
     */
    public TroopFace face() {
        return this.face;
    }

    /**
     * Creates a new TroopTile with the troop facing in the opposite direction.
     *
     * @return a new TroopTile with the troop facing in the opposite direction
     */
    public TroopTile flipped() {
        return new TroopTile(this.troop, this.side,
                this.face.equals(TroopFace.AVERS) ? TroopFace.REVERS : TroopFace.AVERS);
    }

    /**
     * Checks if a troop can step on this tile.
     *
     * @return false, as a troop cannot step on a tile with another troop
     */
    @Override
    public boolean canStepOn() {
        return false;
    }

    /**
     * Checks if the tile contains a troop.
     *
     * @return true, as this tile always contains a troop
     */
    @Override
    public boolean hasTroop() {
        return true;
    }

    /**
     * Generates a list of all possible moves from this tile based on the current game state
     * and the actions of the troop.
     * This method accounts for the troop's current face (AVERS or REVERS) and the specific actions available to it.
     *
     * @param pos   the current position of the troop on the game board
     * @param state the current state of the game, used to determine the validity of potential moves
     * @return      a list of Move objects representing all legal moves the troop can make from its current position
     */
    @Override
    public List<Move> movesFrom(BoardPos pos, GameState state) {
        List<Move> moves = new ArrayList<>();
        List<TroopAction> actions = this.troop.actions(this.face);

        for (TroopAction action : actions)
            moves.addAll(action.movesFrom(pos, this.side, state));

        return moves;
    }
}
