package thedrake.models.actions.slides;

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
 * The `SlideAction` class represents a sliding action for a troop in The Drake game.
 * A troop can slide in a straight line until it hits an obstacle or the edge of the board.
 */
public class SlideAction extends TroopAction {
    /**
     * Constructs a new SlideAction with a given offset.
     * The offset determines the direction and distance of the slide.
     *
     * @param offset the Offset2D representing the direction and distance of the slide
     */
    public SlideAction(Offset2D offset) {
        super(offset);
    }

    /**
     * An alternative constructor for creating a SlideAction using x and y offsets.
     *
     * @param offsetX the horizontal offset for the slide
     * @param offsetY the vertical offset for the slide
     */
    public SlideAction(int offsetX, int offsetY) {
        super(offsetX, offsetY);
    }

    /**
     * Generates a list of possible moves from a given board position.
     * The troop can slide along the direction of the offset until it encounters an obstacle
     * or reaches the board's edge.
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

        for (TilePos current = target; state.canStep(origin, current);
                current = current.stepByPlayingSide(this.offset(), side))
            result.add(new StepOnly(origin, (BoardPos) current));

        if (state.canCapture(origin, target))
            result.add(new StepAndCapture(origin, (BoardPos) target));

        return result;
    }
}
