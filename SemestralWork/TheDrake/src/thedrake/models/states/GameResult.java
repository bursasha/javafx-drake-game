package thedrake.models.states;

import thedrake.models.interfaces.JSONSerializable;

import java.io.PrintWriter;

/**
 * The `GameResult` enum defines the possible outcomes of a game in The Drake.
 * It is used to represent the current status of the game or the final result when the game concludes.
 */
public enum GameResult implements JSONSerializable {
    /**
     * Represents a victory in the game.
     * This status indicates that one of the players has won the game, usually by capturing the opponent's leader.
     */
    VICTORY,

    /**
     * Represents a draw in the game.
     * This status is used when the game ends in a tie, where neither player is able to achieve victory.
     */
    DRAW,

    /**
     * Represents an ongoing game.
     * This status indicates that the game is still in progress, and neither victory nor draw has been achieved yet.
     */
    IN_PLAY;

    /**
     * Serializes the game result into JSON format.
     * This method converts the game result (VICTORY, DRAW, IN_PLAY) into a JSON string representation,
     * allowing for a standardized format for data exchange and storage.
     *
     * Example of JSON output for a game that's in progress:
     * "IN_PLAY"
     *
     * @param writer the PrintWriter used to write the JSON representation of the GameResult
     */
    @Override
    public void toJSON(PrintWriter writer) {
        writer.printf("\"%s\"", this.name());
    }
}
