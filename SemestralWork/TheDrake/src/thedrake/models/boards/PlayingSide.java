package thedrake.models.boards;

import thedrake.models.interfaces.JSONSerializable;

import java.io.PrintWriter;

/**
 * The `PlayingSide` enum represents the two sides of the game The Drake: ORANGE and BLUE.
 */
public enum PlayingSide implements JSONSerializable {

    /**
     * Represents the ORANGE side of the game.
     */
    ORANGE,

    /**
     * Represents the BLUE side of the game.
     */
    BLUE;

    /**
     * Serializes the PlayingSide value into a JSON string representation.
     * The method writes the name of the current enum value (ORANGE or BLUE) in a JSON-compatible format.
     * This serialization is useful for transmitting game states or preferences in a standard,
     * easily interpretable JSON format.
     *
     * Example of JSON output:
     * "ORANGE" or "BLUE"
     *
     * @param writer the PrintWriter used to write the JSON representation of the PlayingSide
     */
    @Override
    public void toJSON(PrintWriter writer) {
        writer.printf("\"%s\"", this.name());
    }
}
