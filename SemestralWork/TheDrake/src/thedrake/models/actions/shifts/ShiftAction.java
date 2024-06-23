package thedrake.models.actions.shifts;

import thedrake.models.states.GameState;
import thedrake.models.positions.boards.Offset2D;
import thedrake.models.boards.PlayingSide;
import thedrake.models.actions.interfaces.TroopAction;
import thedrake.models.moves.interfaces.Move;
import thedrake.models.moves.steps.StepAndCapture;
import thedrake.models.moves.steps.StepOnly;
import thedrake.models.positions.boards.BoardPos;
import thedrake.models.positions.interfaces.TilePos;

import java.util.ArrayList;
import java.util.List;

/**
 * The `ShiftAction` class represents a basic movement action for a troop in The Drake game.
 * A shift is a simple move from one tile to an adjacent tile in a specific direction.
 */
public class ShiftAction extends TroopAction {
    /**
     * Constructs a new ShiftAction with a given offset.
     * The offset determines the direction and distance of the shift.
     *
     * @param offset the Offset2D representing the direction and distance of the shift
     */
    public ShiftAction(Offset2D offset) {
        super(offset);
    }

    /**
     * An alternative constructor for creating a ShiftAction using x and y offsets.
     *
     * @param offsetX the horizontal offset for the shift
     * @param offsetY the vertical offset for the shift
     */
    public ShiftAction(int offsetX, int offsetY) {
        super(offsetX, offsetY);
    }

    /**
     * Generates a list of possible moves from a given board position.
     * The troop can move to an adjacent tile in the direction specified by the offset.
     *
     * @param origin the starting position of the troop on the board
     * @param side   the playing side of the troop
     * @param state  the current state of the game
     * @return       a list of potential moves that can be performed from the origin
     */
    @Override
    public List<Move> movesFrom(BoardPos origin, PlayingSide side, GameState state) {
        List<Move> result = new ArrayList<>();
        TilePos target = origin.stepByPlayingSide(this.offset(), side);

        if (state.canStep(origin, target))
            result.add(new StepOnly(origin, (BoardPos) target));
        else if (state.canCapture(origin, target))
            result.add(new StepAndCapture(origin, (BoardPos) target));

        return result;
    }
}
