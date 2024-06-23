package thedrake.models.troops;

import thedrake.models.boards.PlayingSide;
import thedrake.models.interfaces.JSONSerializable;
import thedrake.models.positions.boards.BoardPos;
import thedrake.models.positions.interfaces.TilePos;
import thedrake.models.tiles.troops.TroopTile;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * The `BoardTroops` class manages the troops of a single player on the game board in The Drake.
 * It keeps track of the player's troops, their positions, the leader's position, and the number of guards deployed.
 */
public class BoardTroops implements JSONSerializable {
    /**
     * The playing side (BLUE or ORANGE) of this set of troops.
     * It identifies which player the troops belong to.
     */
    private final PlayingSide playingSide;

    /**
     * A map storing the positions of troops on the board.
     * Each BoardPos key is associated with a TroopTile, which represents a troop at that position.
     */
    private final Map<BoardPos, TroopTile> troopMap;

    /**
     * The position of the leader on the board.
     * If the leader has not been placed yet, this will be TilePos.OFF_BOARD.
     */
    private final TilePos leaderPosition;

    /**
     * The number of guards that have been placed on the board.
     * This is used to track the progress of placing initial guards after the leader.
     */
    private final int guards;

    /**
     * Constructs a BoardTroops object for a specific playing side.
     * Initially, it has no troops on the board, no guards, and the leader is considered off-board.
     *
     * @param playingSide the playing side (BLUE or ORANGE) for which the troops are being managed
     */
    public BoardTroops(PlayingSide playingSide) {
        this.playingSide = playingSide;
        this.troopMap = Collections.emptyMap();
        this.leaderPosition = TilePos.OFF_BOARD;
        this.guards = 0;
    }

    /**
     * Constructs a BoardTroops object with specific parameters.
     * Used for creating a new state of the troops on the board.
     *
     * @param playingSide    the playing side (BLUE or ORANGE)
     * @param troopMap       a map associating board positions with troop tiles
     * @param leaderPosition the position of the leader on the board
     * @param guards         the number of guards deployed
     */
    public BoardTroops(PlayingSide playingSide, Map<BoardPos, TroopTile> troopMap, TilePos leaderPosition, int guards) {
        this.playingSide = playingSide;
        this.troopMap = new HashMap<>(troopMap);
        this.leaderPosition = leaderPosition;
        this.guards = guards;
    }

    /**
     * Converts the BoardTroops to a JSON object representation and writes it using the provided PrintWriter.
     * The JSON output includes the playing side, leader position, number of guards, and the map of troop positions.
     *
     * Example of JSON output:
     * {
     *   "side": "BLUE",
     *   "leaderPosition": "a1",
     *   "guards": 2,
     *   "troopMap": {
     *     "a1": {...},
     *     "b2": {...}
     *   }
     * }
     *
     * @param writer the PrintWriter used to output the JSON representation of the BoardTroops
     */
    @Override
    public void toJSON(PrintWriter writer) {
        writer.printf("{");

        // Serializing the side of the player (BLUE or ORANGE)
        writer.printf("\"side\":"); this.playingSide.toJSON(writer); writer.printf(",");

        // Serializing the position of the leader on the board
        writer.printf("\"leaderPosition\":"); this.leaderPosition.toJSON(writer); writer.printf(",");

        // Serializing the number of guards
        writer.printf("\"guards\":%d,", this.guards);

        // Serializing the troop map: sorting by troop positions and outputting each troop
        writer.print("\"troopMap\":{");
        Map<String, TroopTile> sortedTroopMap = new TreeMap<>();
        for (BoardPos pos : this.troopMap.keySet()) {
            StringWriter swriter = new StringWriter();
            PrintWriter pwriter = new PrintWriter(swriter);
            pos.toJSON(pwriter);
            sortedTroopMap.put(swriter.toString(), this.troopMap.get(pos));
        }
        boolean isFirst = true;
        for (Map.Entry<String, TroopTile> entry : sortedTroopMap.entrySet()) {
            if (!isFirst)
                writer.print(",");

            writer.print(entry.getKey() + ":");
            entry.getValue().toJSON(writer);
            isFirst = false;
        }
        writer.print("}");

        writer.printf("}");
    }

    /**
     * Returns the troop tile at a specified board position.
     *
     * @param pos the position on the board to check
     * @return    an Optional containing the TroopTile at the position,
     *            or an empty Optional if the position is unoccupied
     */
    public Optional<TroopTile> at(TilePos pos) {
        return Optional.ofNullable(this.troopMap.get(pos));
    }

    /**
     * Gets the playing side of the troops.
     *
     * @return the playing side (BLUE or ORANGE)
     */
    public PlayingSide playingSide() {
        return this.playingSide;
    }

    /**
     * Gets the current position of the leader on the board.
     *
     * @return the position of the leader, or TilePos.OFF_BOARD if the leader hasn't been placed yet
     */
    public TilePos leaderPosition() {
        return this.leaderPosition;
    }

    /**
     * Gets the number of guards that have been deployed.
     *
     * @return the number of guards
     */
    public int guards() {
        return this.guards;
    }

    /**
     * Checks whether the leader has been placed on the board.
     *
     * @return true if the leader is placed, otherwise false
     */
    public boolean isLeaderPlaced() {
        return !this.leaderPosition.equals(TilePos.OFF_BOARD);
    }

    /**
     * Checks whether guards are currently being placed on the board.
     * This happens after the leader is placed and before any other troops are placed.
     *
     * @return true if guards are being placed, otherwise false
     */
    public boolean isPlacingGuards() {
        return this.isLeaderPlaced() && this.guards < 2;
    }

    /**
     * Returns a set of all board positions currently occupied by troops.
     *
     * @return a set of BoardPos representing the positions of all troops
     */
    public Set<BoardPos> troopPositions() {
        return this.troopMap.keySet();
    }

    /**
     * Places a troop on the specified target position on the board.
     * If the target position is already occupied, it throws an IllegalArgumentException.
     * This method also updates the leader position and the number of guards if necessary.
     *
     * @param troop  the troop to be placed on the board
     * @param target the target position for the troop
     * @return       a new BoardTroops instance with the updated state
     */
    public BoardTroops placeTroop(Troop troop, BoardPos target) {
        if (this.at(target).isPresent())
            throw new IllegalArgumentException("Target position is already occupied.");

        Map<BoardPos, TroopTile> newTroops = new HashMap<>(this.troopMap);
        newTroops.put(target, new TroopTile(troop, this.playingSide, TroopFace.AVERS));

        TilePos newLeaderPosition = this.isLeaderPlaced() ? this.leaderPosition : target;
        int newGuards = this.isPlacingGuards() ? this.guards + 1 : this.guards;

        return new BoardTroops(this.playingSide, newTroops, newLeaderPosition, newGuards);
    }

    /**
     * Moves a troop from the origin position to the target position.
     * If moving is not allowed due to game rules (like placing the leader or guards),
     * it throws an IllegalStateException.
     * If the origin is empty or the target is occupied, it throws an IllegalArgumentException.
     * The moved troop is also flipped to its other face.
     *
     * @param origin the original position of the troop to move
     * @param target the target position to move the troop to
     * @return       a new BoardTroops instance with the updated state
     */
    public BoardTroops troopStep(BoardPos origin, BoardPos target) {
        if (!this.isLeaderPlaced())
            throw new IllegalStateException("Cannot move troops before the leader is placed.");

        if (this.isPlacingGuards())
            throw new IllegalStateException("Cannot move troops before guards are placed.");

        if (this.at(origin).isEmpty())
            throw new IllegalArgumentException("No troop at origin position.");

        if (this.at(target).isPresent())
            throw new IllegalArgumentException("Target position is already occupied.");

        Map<BoardPos, TroopTile> newTroops = new HashMap<>(this.troopMap);
        TroopTile tile = newTroops.remove(origin);
        newTroops.put(target, tile.flipped());

        TilePos newLeaderPosition = origin.equals(this.leaderPosition) ? target : this.leaderPosition;

        return new BoardTroops(this.playingSide, newTroops, newLeaderPosition, this.guards);
    }

    /**
     * Flips the troop at the given position to its other face.
     * If flipping is not allowed due to game rules, it throws an IllegalStateException.
     * If the origin position is empty, it throws an IllegalArgumentException.
     *
     * @param origin the position of the troop to flip
     * @return       a new BoardTroops instance with the updated state
     */
    public BoardTroops troopFlip(BoardPos origin) {
        if (!this.isLeaderPlaced())
            throw new IllegalStateException("Cannot move troops before the leader is placed.");

        if (this.isPlacingGuards())
            throw new IllegalStateException("Cannot move troops before guards are placed.");

        if (this.at(origin).isEmpty())
            throw new IllegalArgumentException("No troop at origin position.");

        Map<BoardPos, TroopTile> newTroops = new HashMap<>(this.troopMap);
        TroopTile tile = newTroops.remove(origin);
        newTroops.put(origin, tile.flipped());

        return new BoardTroops(this.playingSide, newTroops, this.leaderPosition, this.guards);
    }

    /**
     * Removes a troop from the target position on the board.
     * If removal is not allowed due to game rules, it throws an IllegalStateException.
     * If the target position is empty, it throws an IllegalArgumentException.
     * If the removed troop is the leader, the leader's position is updated.
     *
     * @param target the position of the troop to be removed
     * @return       a new BoardTroops instance with the updated state
     */
    public BoardTroops removeTroop(BoardPos target) {
        if (!this.isLeaderPlaced())
            throw new IllegalStateException("Cannot move troops before the leader is placed.");

        if (this.isPlacingGuards())
            throw new IllegalStateException("Cannot move troops before guards are placed.");

        if (this.at(target).isEmpty())
            throw new IllegalArgumentException("No troop at target position.");

        Map<BoardPos, TroopTile> newTroops = new HashMap<>(this.troopMap);
        newTroops.remove(target);

        TilePos newLeaderPosition = target.equals(this.leaderPosition) ? TilePos.OFF_BOARD : this.leaderPosition;

        return new BoardTroops(this.playingSide, newTroops, newLeaderPosition, this.guards);
    }
}
