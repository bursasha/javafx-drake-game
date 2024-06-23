package thedrake.models.boards;

import thedrake.models.interfaces.JSONSerializable;
import thedrake.models.positions.boards.BoardPos;
import thedrake.models.positions.interfaces.TilePos;
import thedrake.models.troops.BoardTroops;
import thedrake.models.troops.Troop;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The `Army` class represents the entire army of a single player in The Drake game.
 * It includes the troops on the game board (managed by BoardTroops), captured troops, and troops in the stack.
 */
public class Army implements JSONSerializable {
    /**
     * The `BoardTroops` object representing the troops currently on the game board for this army.
     * It handles all operations related to troop movements, placement, and the state of the board for this player's side.
     */
    private final BoardTroops boardTroops;

    /**
     * A list of `Troop` objects representing the player's remaining troops that have not yet been placed on the board.
     * These troops are available to be played (placed on the board) in subsequent turns.
     */
    private final List<Troop> stack;

    /**
     * A list of `Troop` objects representing enemy troops that have been captured by this army.
     * Captured troops are no longer in play but can impact game strategy and scoring.
     */
    private final List<Troop> captured;

    /**
     * Constructs an Army with a specific playing side and a stack of troops.
     * Initially, there are no troops on the board or captured troops.
     *
     * @param playingSide the side of the player (BLUE or ORANGE)
     * @param stack       the stack of troops to be placed on the board
     */
    public Army(PlayingSide playingSide, List<Troop> stack) {
        this(new BoardTroops(playingSide), stack, Collections.emptyList());
    }

    /**
     * Constructs an Army with specific board troops, a stack of troops, and captured troops.
     *
     * @param boardTroops the troops already placed on the board
     * @param stack       the stack of troops to be placed on the board
     * @param captured    the list of captured enemy troops
     */
    public Army(BoardTroops boardTroops, List<Troop> stack, List<Troop> captured) {
        this.boardTroops = boardTroops;
        this.stack = stack;
        this.captured = captured;
    }

    /**
     * Converts the current state of the Army into a JSON format and writes it using the provided PrintWriter.
     * The JSON representation includes the board troops, the stack of troops, and the captured troops.
     *
     * Example of JSON output:
     * {
     *   "boardTroops": {...},
     *   "stack": ["TroopName1", "TroopName2", ...],
     *   "captured": ["CapturedTroop1", "CapturedTroop2", ...]
     * }
     *
     * @param writer the PrintWriter used to output the JSON representation of the Army
     */
    @Override
    public void toJSON(PrintWriter writer) {
        writer.printf("{");

        // Serialize the troops on the board
        writer.printf("\"boardTroops\":"); this.boardTroops.toJSON(writer); writer.printf(",");

        // Serialize the stack of troops
        writer.printf("\"stack\":[");
        for (int i = 0; i < this.stack.size(); i++) {
            this.stack.get(i).toJSON(writer);

            if (i < this.stack.size() - 1)
                writer.printf(",");
        }
        writer.printf("],");

        // Serialize the captured troops
        writer.printf("\"captured\":[");
        for (int i = 0; i < this.captured.size(); i++) {
            this.captured.get(i).toJSON(writer);

            if (i < this.captured.size() - 1)
                writer.printf(",");
        }
        writer.printf("]");

        writer.printf("}");
    }

    /**
     * Returns the playing side of this army.
     *
     * @return the playing side (BLUE or ORANGE)
     */
    public PlayingSide side() {
        return this.boardTroops.playingSide();
    }

    /**
     * Returns the BoardTroops object representing the troops on the game board.
     *
     * @return the BoardTroops object
     */
    public BoardTroops boardTroops() {
        return this.boardTroops;
    }

    /**
     * Returns the stack of troops to be placed on the board.
     *
     * @return the list of troops in the stack
     */
    public List<Troop> stack() {
        return this.stack;
    }

    /**
     * Returns the list of captured enemy troops.
     *
     * @return the list of captured troops
     */
    public List<Troop> captured() {
        return this.captured;
    }

    /**
     * Places a troop from the top of the stack to the specified target position on the board.
     * If the target position is invalid or the stack is empty, it throws an exception.
     *
     * @param target the position where the top troop from the stack is to be placed
     * @return       a new Army instance with the updated state
     */
    public Army placeFromStack(BoardPos target) {
        if (target.equals(TilePos.OFF_BOARD))
            throw new IllegalArgumentException();

        if (this.stack.isEmpty())
            throw new IllegalStateException();

        if (this.boardTroops.at(target).isPresent())
            throw new IllegalStateException();

        List<Troop> newStack = new ArrayList<>(this.stack.subList(1, this.stack.size()));

        return new Army(this.boardTroops.placeTroop(this.stack.get(0), target), newStack, this.captured);
    }

    /**
     * Moves a troop from one position to another on the board.
     * Delegates the movement to BoardTroops and returns a new Army instance with updated state.
     *
     * @param origin the original position of the troop
     * @param target the target position for the troop
     * @return       a new Army instance with the updated state
     */
    public Army troopStep(BoardPos origin, BoardPos target) {
        return new Army(this.boardTroops.troopStep(origin, target), this.stack, this.captured);
    }

    /**
     * Flips the troop at the given position to its other face.
     * Delegates the flipping to BoardTroops and returns a new Army instance with updated state.
     *
     * @param origin the position of the troop to flip
     * @return       a new Army instance with the updated state
     */
    public Army troopFlip(BoardPos origin) {
        return new Army(this.boardTroops.troopFlip(origin), this.stack, this.captured);
    }

    /**
     * Removes a troop from a specified position on the board.
     * Delegates the removal to BoardTroops and returns a new Army instance with updated state.
     *
     * @param target the position of the troop to be removed
     * @return       a new Army instance with the updated state
     */
    public Army removeTroop(BoardPos target) {
        return new Army(this.boardTroops.removeTroop(target), this.stack, this.captured);
    }

    /**
     * Captures an enemy troop and adds it to the list of captured troops.
     * Returns a new Army instance with the updated state.
     *
     * @param troop the enemy troop to be captured
     * @return      a new Army instance with the updated state
     */
    public Army capture(Troop troop) {
        List<Troop> newCaptured = new ArrayList<>(this.captured);
        newCaptured.add(troop);

        return new Army(this.boardTroops, this.stack, newCaptured);
    }
}
