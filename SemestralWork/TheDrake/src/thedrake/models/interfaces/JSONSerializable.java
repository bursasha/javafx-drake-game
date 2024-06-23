package thedrake.models.interfaces;

import java.io.PrintWriter;

/**
 * The `JSONSerializable` interface provides a standard way for objects to be serialized into JSON format.
 * Classes implementing this interface can be converted into a JSON representation,
 * allowing them to be easily stored, transmitted, or used in APIs that require JSON formatting.
 */
public interface JSONSerializable {

    /**
     * Serializes the implementing object into JSON format. The method uses a provided PrintWriter
     * to output the JSON representation of the object. Implementing classes should define
     * their own JSON conversion logic within this method, ensuring the JSON format is correctly
     * structured and captures all relevant data of the object.
     *
     * Example usage:
     * A class implementing this interface would override this method to provide its custom
     * JSON serialization logic. The method is typically used to serialize the object's
     * state and attributes into a JSON structure.
     *
     * @param writer the PrintWriter used to write the JSON representation of the object
     */
    public void toJSON(PrintWriter writer);
}
