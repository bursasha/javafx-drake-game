package thedrake.models.moves.steps;

import thedrake.models.states.GameState;
import thedrake.models.moves.interfaces.BoardMove;
import thedrake.models.positions.boards.BoardPos;

/**
 * The `StepOnly` class represents a move in The Drake game where a troop moves from one tile to another
 * without capturing an enemy troop.
 * It extends from `BoardMove`, including the original position of the troop and the target position it moves to.
 */
public class StepOnly extends BoardMove {
    /**
     * Constructs a `StepOnly` move with the specified origin and target positions on the board.
     *
     * @param origin the initial position of the troop on the board
     * @param target the destination position of the troop on the board
     */
    public StepOnly(BoardPos origin, BoardPos target) {
        super(origin, target);
    }

    /**
     * Executes this move on the given game state. It moves a troop from its current position to a new position
     * on the game board, updating the game state accordingly.
     *
     * @param originState the current state of the game before the move is executed
     * @return            a new `GameState` reflecting the game after the move has been executed
     */
    @Override
    public GameState execute(GameState originState) {
        return originState.stepOnly(this.origin(), this.target());
    }

}
