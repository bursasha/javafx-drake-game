package thedrake.models.troops;

import thedrake.models.positions.boards.Offset2D;
import thedrake.models.actions.interfaces.TroopAction;
import thedrake.models.interfaces.JSONSerializable;

import java.io.PrintWriter;
import java.util.List;

/**
 * The `Troop` class represents the basic units in the game The Drake.
 * Each troop has a unique name, and pivot points for both its front (avers) and back (revers) faces.
 */
public class Troop implements JSONSerializable {
    /**
     *  Represents the name of the troop.
     */
    private final String name;

    /**
     * The pivot point on the troop's avers (front) face. The pivot determines movement and placement possibilities.
     */
    private final Offset2D aversPivot;

    /**
     * The pivot point on the troop's revers (back) face. The pivot determines movement and placement possibilities.
     */
    private final Offset2D reversPivot;

    /**
     * A list of actions available to the troop when its avers (front) face is up.
     * These actions define the movement and combat capabilities of the troop in its avers state.
     */
    private final List<TroopAction> aversActions;

    /**
     * A list of actions available to the troop when its revers (back) face is up.
     * These actions define the movement and combat capabilities of the troop in its revers state.
     */
    private final List<TroopAction> reversActions;

    /**
     * Creates a new Troop with specified attributes, pivot points, and actions for both faces.
     * This constructor is used when the troop has different pivot points and actions for its avers and revers faces.
     *
     * @param name          the name of the troop
     * @param aversPivot    the pivot point for the avers (front) face
     * @param reversPivot   the pivot point for the revers (back) face
     * @param aversActions  the list of actions available on the avers (front) face of the troop
     * @param reversActions the list of actions available on the revers (back) face of the troop
     */
    public Troop(String name, Offset2D aversPivot, Offset2D reversPivot,
                 List<TroopAction> aversActions, List<TroopAction> reversActions) {
        this.name = name;
        this.aversPivot = aversPivot;
        this.reversPivot = reversPivot;
        this.aversActions = aversActions;
        this.reversActions = reversActions;
    }

    /**
     * Creates a new Troop with the same pivot and actions for both faces.
     * This constructor is used when the troop's avers and revers faces share the same pivot point and actions.
     *
     * @param name          the name of the troop
     * @param pivot         the pivot point for both the avers and revers faces
     * @param aversActions  the list of actions available on the avers (front) face of the troop
     * @param reversActions the list of actions available on the revers (back) face of the troop
     */
    public Troop(String name, Offset2D pivot, List<TroopAction> aversActions, List<TroopAction> reversActions) {
        this(name, pivot, pivot, aversActions, reversActions);
    }

    /**
     * Creates a new Troop with the name and default pivot of (1, 1) for both faces.
     * This constructor is used when the troop has no specific pivot points defined,
     * and it defaults to (1, 1) for both faces.
     *
     * @param name          the name of the troop
     * @param aversActions  the list of actions available on the avers (front) face of the troop
     * @param reversActions the list of actions available on the revers (back) face of the troop
     */
    public Troop(String name, List<TroopAction> aversActions, List<TroopAction> reversActions) {
        this(name, new Offset2D(1, 1), aversActions, reversActions);
    }

    /**
     * Converts the Troop to a JSON string representation and writes it using the provided PrintWriter.
     * The JSON output includes only the name of the troop.
     * This method provides a simple representation of a Troop object suitable for JSON serialization,
     * focusing on identifying the troop by its name.
     *
     * Example of JSON output:
     * "Clubman"
     *
     * @param writer the PrintWriter used to write the JSON representation of the Troop
     */
    @Override
    public void toJSON(PrintWriter writer) {
        writer.printf("\"%s\"", this.name);
    }

    /**
     * Returns the name of the troop.
     *
     * @return the name of the troop
     */
    public String name() {
        return this.name;
    }

    /**
     *  Returns the pivot point based on the provided face.
     *
     * @param face the face of the Troop (AVERS or REVERS)
     * @return     the pivot Offset2D for that face
     */
    public Offset2D pivot(TroopFace face) {
        return face.equals(TroopFace.AVERS) ? this.aversPivot : this.reversPivot;
    }

    /**
     * Returns the list of actions available to the troop based on its current face.
     * This method allows the troop to utilize its actions depending on whether its avers or revers side is up.
     *
     * @param face the face of the Troop (AVERS or REVERS) for which actions are requested
     * @return     a list of TroopActions for the specified face
     */
    public List<TroopAction> actions(TroopFace face) {
        return face.equals(TroopFace.AVERS) ? this.aversActions : this.reversActions;
    }
}
