package thedrake.models.states;

import thedrake.models.boards.Army;
import thedrake.models.boards.Board;
import thedrake.models.boards.PlayingSide;
import thedrake.models.interfaces.JSONSerializable;
import thedrake.models.positions.boards.BoardPos;
import thedrake.models.positions.interfaces.TilePos;
import thedrake.models.tiles.interfaces.Tile;
import thedrake.models.tiles.troops.TroopTile;
import thedrake.models.troops.BoardTroops;
import thedrake.models.troops.Troop;

import java.io.PrintWriter;
import java.util.Optional;

/**
 * The `GameState` class encapsulates the entire state of a game in The Drake.
 * It includes the game board, the armies of both players, the side currently on turn, and the game result.
 */
public class GameState implements JSONSerializable {
    /**
     * The game board representing the current layout of the game.
     * It contains the configuration of the tiles and any troops placed on them.
     */
    private final Board board;

    /**
     * The side that is currently taking their turn in the game.
     * It can be either BLUE or ORANGE, representing the two players.
     */
    private final PlayingSide sideOnTurn;

    /**
     * The Army representing the blue player's troops.
     * It includes troops on the game board, captured enemy troops, and the stack of troops to be played.
     */
    private final Army blueArmy;

    /**
     * The Army representing the orange player's troops.
     * Similar to blueArmy, it includes the orange player's troops on the board, captured enemy troops, and the stack.
     */
    private final Army orangeArmy;

    /**
     * The current result of the game.
     * This can be IN_PLAY (game is ongoing), VICTORY (one player has won), or DRAW (the game has ended in a tie).
     */
    private final GameResult result;

    /**
     * Constructs a GameState with the initial configuration.
     *
     * @param board      the game board
     * @param blueArmy   the blue player's army
     * @param orangeArmy the orange player's army
     */
    public GameState(Board board, Army blueArmy, Army orangeArmy) {
        this(board, blueArmy, orangeArmy, PlayingSide.BLUE, GameResult.IN_PLAY);
    }

    /**
     * Constructs a GameState with custom configuration.
     *
     * @param board      the game board
     * @param blueArmy   the blue player's army
     * @param orangeArmy the orange player's army
     * @param sideOnTurn the side currently taking their turn
     * @param result     the current result of the game
     */
    public GameState(Board board, Army blueArmy, Army orangeArmy, PlayingSide sideOnTurn, GameResult result) {
        this.board = board;
        this.sideOnTurn = sideOnTurn;
        this.blueArmy = blueArmy;
        this.orangeArmy = orangeArmy;
        this.result = result;
    }

    /**
     * Serializes the GameState into JSON format. This method provides a JSON representation
     * of the entire state of the game, including the board, both armies, the side currently on turn,
     * and the game result.
     *
     * The JSON output is structured in a way that captures all aspects
     * of the current game state, making it suitable for saving the game or sharing the game state.
     *
     * Example of JSON output:
     * {
     *   "result": "IN_PLAY",
     *   "board": { ... },
     *   "blueArmy": { ... },
     *   "orangeArmy": { ... }
     * }
     *
     * @param writer the PrintWriter used to write the JSON representation of the GameState
     */
    @Override
    public void toJSON(PrintWriter writer) {
        writer.print("{");

        // Serialize game result
        writer.print("\"result\":"); this.result.toJSON(writer); writer.print(",");

        // Serialize the game board
        writer.print("\"board\":"); this.board.toJSON(writer); writer.print(",");

        // Serialize the blue player's army
        writer.print("\"blueArmy\":"); this.blueArmy.toJSON(writer); writer.print(",");

        // Serialize the orange player's army
        writer.print("\"orangeArmy\":"); this.orangeArmy.toJSON(writer);

        writer.print("}");
    }

    /**
     * Gets the game board.
     *
     * @return the game board
     */
    public Board board() {
        return this.board;
    }

    /**
     * Gets the side that is currently taking their turn.
     *
     * @return the side on turn
     */
    public PlayingSide sideOnTurn() {
        return this.sideOnTurn;
    }

    /**
     * Gets the current result of the game.
     *
     * @return the game result
     */
    public GameResult result() {
        return this.result;
    }

    /**
     * Gets the army for the specified side.
     *
     * @param side the side of the army to retrieve
     * @return     the army of the specified side
     */
    public Army army(PlayingSide side) {
        return side.equals(PlayingSide.BLUE) ? this.blueArmy : this.orangeArmy;
    }

    /**
     * Gets the army of the side currently taking their turn.
     *
     * @return the army on turn
     */
    public Army armyOnTurn() {
        return this.army(this.sideOnTurn);
    }

    /**
     * Gets the army of the side not currently taking their turn.
     *
     * @return the army not on turn
     */
    public Army armyNotOnTurn() {
        return this.sideOnTurn.equals(PlayingSide.BLUE) ? this.orangeArmy : this.blueArmy;
    }

    /**
     * Returns the tile at a specified position on the board.
     * It can be a troop tile from either the blue or orange army, or a board tile if no troop is present.
     *
     * @param pos the position on the board to check
     * @return    the tile at the specified position
     */
    public Tile tileAt(TilePos pos) {
        Optional<TroopTile> blueTroop = this.army(PlayingSide.BLUE).boardTroops().at(pos);
        if (blueTroop.isPresent())
            return blueTroop.get();

        Optional<TroopTile> orangeTroop = this.army(PlayingSide.ORANGE).boardTroops().at(pos);
        if (orangeTroop.isPresent())
            return orangeTroop.get();

        return this.board().at(pos);
    }

    /**
     * Checks if a step can be initiated from the given position.
     * Returns false if the position is off-board, the game is not in play, or the position doesn't have a troop
     * of the army currently taking their turn.
     *
     * @param origin the starting position for the step
     * @return       true if a step can be initiated, false otherwise
     */
    private boolean canStepFrom(TilePos origin) {
        if (origin.equals(TilePos.OFF_BOARD))
            return false;

        if (!this.result.equals(GameResult.IN_PLAY) || !this.tileAt(origin).hasTroop() ||
                this.armyNotOnTurn().boardTroops().at(origin).isPresent())
            return false;

        BoardTroops armyOnTurnTroops = this.armyOnTurn().boardTroops();

        return armyOnTurnTroops.at(origin).isPresent() && armyOnTurnTroops.isLeaderPlaced() &&
                !armyOnTurnTroops.isPlacingGuards();
    }

    /**
     * Checks if a step can be completed at the given position.
     * Returns false if the position is off-board, the game is not in play, or the position cannot be stepped on.
     *
     * @param target the target position for the step
     * @return       true if a step can be completed, false otherwise
     */
    private boolean canStepTo(TilePos target) {
        if (target.equals(TilePos.OFF_BOARD) || !this.result.equals(GameResult.IN_PLAY))
            return false;

        return this.tileAt(target).canStepOn();
    }

    /**
     * Checks if a troop at the given position can capture an enemy troop.
     * Returns false if the position is off-board, the game is not in play, or no enemy troop is present.
     *
     * @param target the target position for capturing
     * @return       true if an enemy troop can be captured, false otherwise
     */
    private boolean canCaptureOn(TilePos target) {
        if (target.equals(TilePos.OFF_BOARD) || !this.result.equals(GameResult.IN_PLAY))
            return false;

        return this.armyNotOnTurn().boardTroops().at(target).isPresent();
    }

    /**
     * Checks if a step move is valid from the origin to the target position.
     *
     * @param origin the starting position for the move
     * @param target the target position for the move
     * @return       true if the move is valid, false otherwise
     */
    public boolean canStep(TilePos origin, TilePos target) {
        return this.canStepFrom(origin) && this.canStepTo(target);
    }

    /**
     * Checks if a capture move is valid from the origin to the target position.
     *
     * @param origin the starting position for the move
     * @param target the target position for the move
     * @return       true if the move is valid, false otherwise
     */
    public boolean canCapture(TilePos origin, TilePos target) {
        return this.canStepFrom(origin) && this.canCaptureOn(target);
    }

    /**
     * Checks if a troop can be placed from the stack to the target position.
     *
     * @param target the target position to place a troop from the stack
     * @return       true if a troop can be placed, false otherwise
     */
    public boolean canPlaceFromStack(TilePos target) {
        if (target.equals(TilePos.OFF_BOARD))
            return false;

        if (!this.result.equals(GameResult.IN_PLAY) || this.armyOnTurn().stack().isEmpty() || !this.canStepTo(target))
            return false;

        BoardTroops armyOnTurnTroops = this.armyOnTurn().boardTroops();

        if (!armyOnTurnTroops.isLeaderPlaced() &&
                ((this.sideOnTurn().equals(PlayingSide.BLUE) && target.row() == 1) ||
                        (this.sideOnTurn().equals(PlayingSide.ORANGE) && target.row() == this.board.dimension())))
            return true;

        if (armyOnTurnTroops.isPlacingGuards() && target.isNextTo(armyOnTurnTroops.leaderPosition()))
            return true;

        if (armyOnTurnTroops.isLeaderPlaced() && !armyOnTurnTroops.isPlacingGuards())
            for (TilePos pos : armyOnTurnTroops.troopPositions())
                if (target.isNextTo(pos))
                    return true;

        return false;
    }

    /**
     * Performs a step move only, updating the game state accordingly.
     *
     * @param origin the starting position for the move
     * @param target the target position for the move
     * @return       a new GameState reflecting the move
     */
    public GameState stepOnly(BoardPos origin, BoardPos target) {
        if (this.canStep(origin, target))
            return this.createNewGameState(this.armyNotOnTurn(),
                    this.armyOnTurn().troopStep(origin, target), GameResult.IN_PLAY);

        throw new IllegalArgumentException();
    }

    /**
     * Performs a step move and captures an enemy troop, updating the game state accordingly.
     *
     * @param origin the starting position for the move
     * @param target the target position for the move
     * @return       a new GameState reflecting the move
     */
    public GameState stepAndCapture(BoardPos origin, BoardPos target) {
        if (this.canCapture(origin, target)) {
            Troop captured = this.armyNotOnTurn().boardTroops().at(target).get().troop();
            GameResult newResult = GameResult.IN_PLAY;

            if (this.armyNotOnTurn().boardTroops().leaderPosition().equals(target))
                newResult = GameResult.VICTORY;

            return this.createNewGameState(this.armyNotOnTurn().removeTroop(target),
                    this.armyOnTurn().troopStep(origin, target).capture(captured), newResult);
        }

        throw new IllegalArgumentException();
    }

    /**
     * Captures an enemy troop without moving, updating the game state accordingly.
     *
     * @param origin the position of the capturing troop
     * @param target the position of the enemy troop
     * @return       a new GameState reflecting the capture
     */
    public GameState captureOnly(BoardPos origin, BoardPos target) {
        if (this.canCapture(origin, target)) {
            Troop captured = this.armyNotOnTurn().boardTroops().at(target).get().troop();
            GameResult newResult = GameResult.IN_PLAY;

            if (this.armyNotOnTurn().boardTroops().leaderPosition().equals(target))
                newResult = GameResult.VICTORY;

            return this.createNewGameState(this.armyNotOnTurn().removeTroop(target),
                    this.armyOnTurn().troopFlip(origin).capture(captured), newResult);
        }

        throw new IllegalArgumentException();
    }

    /**
     * Places a troop from the stack to the target position, updating the game state accordingly.
     *
     * @param target the target position for placing a troop from the stack
     * @return       a new GameState reflecting the placement
     */
    public GameState placeFromStack(BoardPos target) {
        if (this.canPlaceFromStack(target))
            return this.createNewGameState(this.armyNotOnTurn(), this.armyOnTurn().placeFromStack(target),
                    GameResult.IN_PLAY);

        throw new IllegalArgumentException();
    }

    /**
     * Marks the game as resigned by the current player, updating the game state accordingly.
     *
     * @return a new GameState with the result set to victory for the opposing player
     */
    public GameState resign() {
        return this.createNewGameState(this.armyNotOnTurn(), this.armyOnTurn(), GameResult.VICTORY);
    }

    /**
     * Marks the game as a draw, updating the game state accordingly.
     * This method creates a new game state with the result set to DRAW.
     *
     * @return a new GameState representing a draw
     */
    public GameState draw() {
        return this.createNewGameState(this.armyOnTurn(), this.armyNotOnTurn(), GameResult.DRAW);
    }

    /**
     * Creates a new GameState with updated information about the armies and the result.
     * It is a utility method used internally to construct a new GameState after a move or game decision.
     *
     * @param armyOnTurn    the army of the player who will take the next turn
     * @param armyNotOnTurn the army of the player who just completed their turn
     * @param result        the new result of the game, which can be IN_PLAY, VICTORY, or DRAW
     * @return              a new GameState reflecting the updated state of the game
     */
    private GameState createNewGameState(Army armyOnTurn, Army armyNotOnTurn, GameResult result) {
        if (armyOnTurn.side().equals(PlayingSide.BLUE))
            return new GameState(this.board, armyOnTurn, armyNotOnTurn, PlayingSide.BLUE, result);

        return new GameState(this.board, armyNotOnTurn, armyOnTurn, PlayingSide.ORANGE, result);
    }
}
