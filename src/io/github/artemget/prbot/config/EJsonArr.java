package io.github.artemget.prbot.config;

import javax.json.JsonArray;
import javax.json.JsonObject;
import org.cactoos.Scalar;

/**
 * Eject {@link JsonArray} from provided json. Null safe.
 *
 * @since 0.0.1
 */
public final class EJsonArr implements Entry<JsonArray> {
    /**
     * Scalar returning {@link JsonArray}.
     */
    private final Scalar<JsonArray> json;

    /**
     * Default ctor. Get {@link JsonArray} by it's attribute name.
     * Wraps provided json in {@link Scalar}.
     *
     * @param json Object
     * @param attr To lookup
     */
    public EJsonArr(final JsonObject json, final String attr) {
        this(() -> json, attr);
    }

    /**
     * Get {@link JsonArray} by it's attribute name
     * from json provided by {@link Scalar}.
     *
     * @param json Scalar source json
     * @param attr To lookup
     */
    public EJsonArr(final Scalar<JsonObject> json, final String attr) {
        this(
            () -> {
                try {
                    return json.value().getJsonArray(attr);
                } catch (final ClassCastException exception) {
                    throw new EntryException(
                        String.format("Attribute %s couldn't be mapped to JsonArray", attr),
                        exception
                    );
                }
            }
        );
    }

    /**
     * Main ctor.
     *
     * @param json Array
     */
    public EJsonArr(final Scalar<JsonArray> json) {
        this.json = () -> new ESafe<>(json).value();
    }

    @Override
    public JsonArray value() throws EntryException {
        try {
            return this.json.value();
        } catch (final Exception exception) {
            throw new EntryException(exception);
        }
    }
}
