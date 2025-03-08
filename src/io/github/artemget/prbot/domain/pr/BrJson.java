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

import io.github.artemget.prbot.config.EJsonStr;
import io.github.artemget.prbot.config.EntryException;
import javax.json.JsonObject;

/**
 * Git branch from json source.
 *
 * @since 0.0.1
 */
public final class BrJson implements Branch {
    /**
     * Json containing branch's name and link.
     * Suggested structure:
     * {
     *   "name": "123",
     *   "link": "https://example-branch-link.com/123",
     *   ...
     * }
     */
    private final JsonObject json;

    /**
     * Main ctor.
     *
     * @param json Branch
     */
    public BrJson(final JsonObject json) {
        this.json = json;
    }

    @Override
    public String name() throws EmptyArgumentException {
        try {
            return new EJsonStr(this.json, "name").value();
        } catch (final EntryException exception) {
            throw new EmptyArgumentException(
                String.format(
                    "Failed to get branch name. Source json: %s",
                    this.json.toString()
                ),
                exception
            );
        }
    }

    @Override
    public String link() throws EmptyArgumentException {
        try {
            return new EJsonStr(this.json, "link").value();
        } catch (final EntryException exception) {
            throw new EmptyArgumentException(
                String.format(
                    "Failed to get branch link. Source json: %s",
                    this.json.toString()
                ),
                exception
            );
        }
    }
}
