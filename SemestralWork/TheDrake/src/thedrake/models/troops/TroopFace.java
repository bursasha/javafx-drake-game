package thedrake.models.troops;

import thedrake.models.interfaces.JSONSerializable;

import java.io.PrintWriter;

/**
 * The `TroopFace` enum represents the two possible facing directions of a troop in The Drake game:
 * AVERS (front) and REVERS (back).
 */
public enum TroopFace implements JSONSerializable {
    /**
     * The front facing direction of a troop.
     */
    AVERS,

    /**
     * The back facing direction of a troop.
     */
    REVERS;

    /**
     * Serializes the TroopFace enum value into a JSON format using the provided PrintWriter.
     * It outputs the name of the TroopFace (AVERS or REVERS) as a string in JSON format.
     *
     * Example JSON output:
     * "AVERS" or "REVERS"
     *
     * @param writer the PrintWriter to write the JSON string representation of the TroopFace
     */
    @Override
    public void toJSON(PrintWriter writer) {
        writer.printf("\"%s\"", this.name());
    }
}
