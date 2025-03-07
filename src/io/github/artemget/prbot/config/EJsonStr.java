/*
 * MIT License
 *
 * Copyright (c) 2024-2025. Artem Getmanskii
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.artemget.prbot.config;

import javax.json.JsonObject;
import org.cactoos.Scalar;

/**
 * Eject {@link String} value from provided json. Null safe.
 *
 * @since 0.0.1
 */
public final class EJsonStr implements Entry<String> {
    /**
     * Scalar returning {@link String}.
     */
    private final Scalar<String> json;

    /**
     * Default ctor. Get {@link String} by it's attribute name.
     * Wraps provided json in {@link Scalar}.
     *
     * @param json Object
     * @param attr To lookup
     */
    public EJsonStr(final JsonObject json, final String attr) {
        this(() -> json, attr);
    }

    /**
     * Get {@link String} by it's attribute name.
     *
     * @param json Scalar source json
     * @param attr To lookup
     */
    public EJsonStr(final Scalar<JsonObject> json, final String attr) {
        this(
            () -> {
                try {
                    return json.value().getString(attr);
                } catch (final ClassCastException exception) {
                    throw new EntryException(
                        String.format("Attribute %s couldn't be mapped to String", attr),
                        exception
                    );
                }
            }
        );
    }

    /**
     * Main Ctor. Implement by yourself.
     *
     * @param json Scalar
     */
    public EJsonStr(final Scalar<String> json) {
        this.json = () -> new ESafe<>(json).value();
    }

    //@checkstyle IllegalCatchCheck (7 lines)
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    @Override
    public String value() throws EntryException {
        try {
            return this.json.value();
        } catch (final Exception exception) {
            throw new EntryException(exception);
        }
    }
}
