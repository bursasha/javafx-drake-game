package thedrake.models.moves.stacks;

import thedrake.models.states.GameState;
import thedrake.models.moves.interfaces.Move;
import thedrake.models.positions.boards.BoardPos;

/**
 * The `PlaceFromStack` class represents a move in The Drake game where a player places
 * a troop from their stack onto the game board.
 * This move is crucial at the beginning of the game and whenever a player decides to add a new troop to the board.
 */
public class PlaceFromStack extends Move {
    /**
     * Constructs a new PlaceFromStack move for placing a troop on the target position on the board.
     * The target position is where the player intends to place the troop from the top of their stack.
     *
     * @param target the position on the board where the player wants to place the troop
     */
    public PlaceFromStack(BoardPos target) {
        super(target);
    }

    /**
     * Executes the action of placing a troop from the stack to the specified position on the game board.
     * This method updates the state of the game to reflect the new position of the troop.
     *
     * @param originState the current state of the game before the move is executed
     * @return            a new GameState reflecting the changes after the move is executed
     */
    @Override
    public GameState execute(GameState originState) {
        return originState.placeFromStack(this.target());
    }

}
