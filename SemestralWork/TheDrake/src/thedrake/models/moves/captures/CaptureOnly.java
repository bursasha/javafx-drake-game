package thedrake.models.moves.captures;

import thedrake.models.states.GameState;
import thedrake.models.moves.interfaces.BoardMove;
import thedrake.models.positions.boards.BoardPos;

/**
 * The `CaptureOnly` class represents a move in The Drake game where a troop captures an opponent's troop without moving.
 * It extends `BoardMove` and specifies the action of capturing an enemy troop from its current position.
 */
public class CaptureOnly extends BoardMove {
    /**
     * Constructs a CaptureOnly move with the specified origin and target positions.
     *
     * @param origin the position of the capturing troop
     * @param target the position of the troop to be captured
     */
    public CaptureOnly(BoardPos origin, BoardPos target) {
        super(origin, target);
    }

    /**
     * Executes this capture-only move in the given game state.
     * This method uses the game state to perform the capturing action and returns the new state of the game.
     *
     * @param originState the current state of the game
     * @return            the updated game state after performing the capture
     */
    @Override
    public GameState execute(GameState originState) {
        return originState.captureOnly(this.origin(), this.target());
    }

}
