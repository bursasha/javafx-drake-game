package thedrake.models.moves.steps;

import thedrake.models.states.GameState;
import thedrake.models.moves.interfaces.BoardMove;
import thedrake.models.positions.boards.BoardPos;

/**
 * The `StepAndCapture` class represents a move in The Drake game where a troop moves from one tile to
 * another and captures an enemy troop.
 * It is a specialized type of `BoardMove` that involves both movement and capturing an opponent's troop.
 */
public class StepAndCapture extends BoardMove {
    /**
     * Constructs a `StepAndCapture` move with the specified origin and target positions on the board.
     *
     * @param origin the initial position of the troop on the board
     * @param target the destination position of the troop on the board where the enemy troop is captured
     */
    public StepAndCapture(BoardPos origin, BoardPos target) {
        super(origin, target);
    }

    /**
     * Executes this move on the given game state. It moves a troop from its current position to a new position
     * on the game board and captures the enemy troop located at the target position,
     * updating the game state accordingly.
     *
     * @param originState the current state of the game before the move is executed
     * @return            a new `GameState` reflecting the game after the move has been executed,
     *                    including the capture of the enemy troop
     */
    @Override
    public GameState execute(GameState originState) {
        return originState.stepAndCapture(this.origin(), this.target());
    }

}
