package thedrake.models.tiles.interfaces;

import thedrake.models.positions.boards.BoardPos;
import thedrake.models.states.GameState;
import thedrake.models.moves.interfaces.Move;

import java.util.List;

/**
 * The `Tile` interface represents a tile on the game board in The Drake.
 * Tiles can either be empty or contain a troop, and they may allow or disallow movement by troops.
 */
public interface Tile {

    /**
     * Checks if a troop can step on this tile.
     *
     * @return true if a troop can step on this tile, false otherwise
     */
    public boolean canStepOn();

    /**
     * Checks if the tile contains a troop.
     *
     * @return true if the tile contains a troop, false otherwise
     */
    public boolean hasTroop();

    /**
     * Generates a list of all possible moves that can be performed from this tile, given the current game state.
     * This method is used to determine the potential actions a troop can take when positioned on this tile.
     *
     * @param pos   the position of the tile on the game board
     * @param state the current state of the game
     * @return      a list of Move objects representing all possible moves from this tile
     */
    public List<Move> movesFrom(BoardPos pos, GameState state);
}
