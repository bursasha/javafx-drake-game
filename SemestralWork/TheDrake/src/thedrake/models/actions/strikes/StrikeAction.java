package thedrake.models.actions.strikes;

import thedrake.models.states.GameState;
import thedrake.models.positions.boards.Offset2D;
import thedrake.models.boards.PlayingSide;
import thedrake.models.actions.interfaces.TroopAction;
import thedrake.models.moves.captures.CaptureOnly;
import thedrake.models.moves.interfaces.Move;
import thedrake.models.positions.boards.BoardPos;
import thedrake.models.positions.interfaces.TilePos;

import java.util.ArrayList;
import java.util.List;

/**
 * The `StrikeAction` class represents an attack action for a troop in The Drake game.
 * This action allows a troop to attack or capture an enemy troop positioned
 * at a certain offset from its current position.
 */
public class StrikeAction extends TroopAction {
    /**
     * Constructs a StrikeAction with a specified offset.
     *
     * @param offset the offset at which the action can be performed relative to the troop's position
     */
    public StrikeAction(Offset2D offset) {
        super(offset);
    }

    /**
     * Constructs a StrikeAction with specified offsets in the x and y direction.
     *
     * @param offsetX the offset in the x direction
     * @param offsetY the offset in the y direction
     */
    public StrikeAction(int offsetX, int offsetY) {
        super(offsetX, offsetY);
    }

    /**
     * Generates a list of capture moves that can be performed by the troop from its current position.
     * It calculates the target position based on the offset and the troop's side.
     * It then checks if capturing an enemy troop at that position is a valid move in the current game state.
     *
     * @param origin the starting position of the troop on the board
     * @param side   the side (BLUE or ORANGE) of the troop performing the action
     * @param state  the current state of the game
     * @return       a list of valid capture moves that can be performed
     */
    @Override
    public List<Move> movesFrom(BoardPos origin, PlayingSide side, GameState state) {
        List<Move> result = new ArrayList<>();
        TilePos target = origin.stepByPlayingSide(this.offset(), side);

        if (state.canCapture(origin, target))
            result.add(new CaptureOnly(origin, (BoardPos) target));

        return result;
    }
}
