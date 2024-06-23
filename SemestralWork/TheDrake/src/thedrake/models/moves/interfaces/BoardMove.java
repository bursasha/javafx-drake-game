package thedrake.models.moves.interfaces;

import thedrake.models.positions.boards.BoardPos;

/**
 * The abstract class `BoardMove` extends `Move` and represents a move action on the board in The Drake game.
 * This class focuses on moves that involve moving from one board position to another.
 */
public abstract class BoardMove extends Move {
    /**
     * The starting position on the board from where the move begins.
     */
    private final BoardPos origin;

    /**
     * Constructs a BoardMove with specified origin and target positions.
     *
     * @param origin the starting position of the move on the board
     * @param target the target position of the move on the board
     */
    protected BoardMove(BoardPos origin, BoardPos target) {
        super(target);
        this.origin = origin;
    }

    /**
     * Gets the origin position of this move.
     *
     * @return the starting position of the move on the board
     */
    public BoardPos origin() {
        return this.origin;
    }

    /**
     * Generates a hash code for this board move.
     * The hash code is based on both the origin and target positions of the move.
     *
     * @return the hash code for this board move
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.origin == null) ? 0 : this.origin.hashCode());
        return result;
    }

    /**
     * Compares this board move to another object for equality.
     * Two board moves are considered equal if they have the same origin and target positions.
     *
     * @param obj the object to compare with
     * @return    true if this board move is equal to the other object, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;

        BoardMove other = (BoardMove) obj;
        if (this.origin == null) {
            if (other.origin != null)
                return false;
        }
        else if (!this.origin.equals(other.origin))
            return false;

        return true;
    }

    /**
     * Returns a string representation of this board move.
     *
     * @return a string that includes the class name, origin, and target positions of the move
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + this.origin + "->" + this.target + '}';
    }
}
