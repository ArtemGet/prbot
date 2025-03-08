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

import jakarta.json.JsonObject;

/**
 * Git project from json source.
 *
 * @since 0.0.1
 * @todo #20:90 {@link io.github.artemget.prbot.domain.pr.ProjJson}
 *  and {@link io.github.artemget.prbot.domain.pr.PrJsonStrict}
 *  is not covered by tests. Lets cover it's methods. I suggest using
 *  <a href="https://github.com/joshka/junit-json-params">joshka/junit-json-params</a>
 *  with it's @JsonSource annotation, test sources is placed under resources dir.
 *  Note that using of junit-json-params requires to switch javax.json to
 *  jakarta.json.
 */
public final class ProjJson implements Project {
    /**
     * Json containing project's name and link.
     * Suggested structure:
     * {
     *   "name": "123",
     *   "link": "https://example-branch-link.com/123",
     *   ...
     * }
     */
    private final Branch branch;

    /**
     * Ctor.
     *
     * @param json Project
     */
    public ProjJson(final JsonObject json) {
        this(new BrJson(json));
    }

    /**
     * Main Ctor.
     *
     * @param branch Reused
     */
    private ProjJson(final Branch branch) {
        this.branch = branch;
    }

    @Override
    public String name() throws EmptyArgumentException {
        return this.branch.name();
    }

    @Override
    public String link() throws EmptyArgumentException {
        return this.branch.link();
    }
}
