package thedrake.models.moves.interfaces;

import thedrake.models.states.GameState;
import thedrake.models.positions.boards.BoardPos;

/**
 * The abstract class `Move` represents a single move action in The Drake game.
 * It serves as the base class for specific types of moves that a troop can make on the board.
 */
public abstract class Move {
    /**
     * The target position on the board where the move ends.
     */
    protected final BoardPos target;

    /**
     * Constructs a Move with a specified target position.
     *
     * @param target the target position on the board
     */
    protected Move(BoardPos target) {
        this.target = target;
    }

    /**
     * Gets the target position of this move.
     *
     * @return the target position on the board
     */
    public BoardPos target() {
        return this.target;
    }

    /**
     * Executes this move in the given game state and returns the resulting new game state.
     * It is an abstract method and should be implemented by subclasses to define specific move behaviors.
     *
     * @param originState the current state of the game before the move
     * @return            the new state of the game after the move is executed
     */
    public abstract GameState execute(GameState originState);

    /**
     * Generates a hash code for this move.
     * The hash code is based on the target position of the move.
     *
     * @return the hash code for this move
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.target == null) ? 0 : this.target.hashCode());
        return result;
    }

    /**
     * Compares this move to another object for equality.
     * Two moves are considered equal if they have the same target position.
     *
     * @param obj the object to compare with
     * @return    true if this move is equal to the other object, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;

        Move other = (Move) obj;
        if (this.target == null) {
            if (other.target != null)
                return false;
        }
        else if (!this.target.equals(other.target))
            return false;

        return true;
    }
}
