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

package io.github.artemget.prbot.domain.pr;

import javax.json.JsonObject;

/**
 * User's account from json source.
 *
 * @since 0.0.1
 */
public final class AccJson implements Account {
    /**
     * Json containing account's id and name.
     * Suggested structure:
     * {
     *   "id": "123",
     *   "username": "username",
     *   ...
     * }
     */
    private final JsonObject json;

    /**
     * Main ctor.
     *
     * @param json Account
     */
    public AccJson(final JsonObject json) {
        this.json = json;
    }

    @Override
    public String identity() throws EmptyArgumentException {
        if (!this.json.containsKey("id")) {
            throw new EmptyArgumentException("Empty account identity");
        }
        try {
            return this.json.getString("id");
        } catch (final ClassCastException exception) {
            throw new EmptyArgumentException(
                String.format(
                    "Wrong account identity format: %s",
                    this.json.get("id").getValueType()
                ),
                exception
            );
        }
    }

    @Override
    public String username() throws EmptyArgumentException {
        if (!this.json.containsKey("username")) {
            throw new EmptyArgumentException("Empty account name");
        }
        try {
            return this.json.getString("username");
        } catch (final ClassCastException exception) {
            throw new EmptyArgumentException(
                String.format(
                    "Wrong account name format: %s",
                    this.json.get("username").getValueType()
                ),
                exception
            );
        }
    }
}
