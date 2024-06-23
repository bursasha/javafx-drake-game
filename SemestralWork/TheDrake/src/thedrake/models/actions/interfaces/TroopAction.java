package thedrake.models.actions.interfaces;

import thedrake.models.states.GameState;
import thedrake.models.positions.boards.Offset2D;
import thedrake.models.boards.PlayingSide;
import thedrake.models.moves.interfaces.Move;
import thedrake.models.positions.boards.BoardPos;

import java.util.List;

/**
 * The `TroopAction` abstract class represents an action that a troop can perform in the game The Drake.
 * Each action has an offset, indicating its relative position to the troop's pivot point.
 */
public abstract class TroopAction {
    /**
     * The offset relative to the troop's position, defining where the action can be performed.
     */
    private final Offset2D offset;

    /**
     * Constructs a TroopAction with a given offset specified by X and Y coordinates.
     *
     * @param offsetX the horizontal offset from the troop's position
     * @param offsetY the vertical offset from the troop's position
     */
    protected TroopAction(int offsetX, int offsetY) {
        this(new Offset2D(offsetX, offsetY));
    }

    /**
     * Constructs a TroopAction with a given Offset2D object.
     *
     * @param offset the offset from the troop's position
     */
    public TroopAction(Offset2D offset) {
        this.offset = offset;
    }

    /**
     * Gets the offset associated with the action.
     *
     * @return the Offset2D representing the action's offset
     */
    public Offset2D offset() {
        return this.offset;
    }

    /**
     * Determines the possible moves for a troop from a given position, taking into account the game's current state.
     * This abstract method should be implemented to define specific action behaviors.
     *
     * @param origin the starting position of the troop on the board
     * @param side   the playing side of the troop (BLUE or ORANGE)
     * @param state  the current state of the game
     * @return       a list of moves that can be performed as a result of this action
     */
    public abstract List<Move> movesFrom(BoardPos origin, PlayingSide side, GameState state);
}
