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

import io.github.artemget.prbot.config.EJsonObj;
import io.github.artemget.prbot.config.EntryException;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * Gitlab webhook request.
 *
 * @since 0.0.1
 * @todo #6:60 {@link io.github.artemget.prbot.domain.pr.ReqHkGitlab}
 *  and {@link io.github.artemget.prbot.domain.pr.ReqHkStrict}
 *  should be implemented. You could find test request samples
 *  under resources dir.
 */
@SuppressWarnings({
    "PMD.AvoidDuplicateLiterals",
    "PMD.OnlyOneConstructorShouldDoInitialization",
    "PMD.SingularField",
    "AnnotationUseStyleCheck"
})
public final class ReqHkGitlab implements RequestWebhook {
    /**
     * Json containing gitlab webhook request.
     * See resources dir.
     */
    private final JsonObject json;

    /**
     * Wraps json string in to {@link JsonObject}.
     *
     * @param json String
     * @throws EntryException If fails
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public ReqHkGitlab(final String json) throws EntryException {
        this(
            new EJsonObj(
                () -> {
                    try (JsonReader read = Json.createReader(new StringReader(json))) {
                        return read.readObject();
                    }
                }
            ).value()
        );
    }

    /**
     * Main ctor.
     *
     * @param json Object
     */
    public ReqHkGitlab(final JsonObject json) {
        this.json = json;
    }

    @Override
    public String token() throws EmptyArgumentException {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public String action() throws EmptyArgumentException {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public String platform() throws EmptyArgumentException {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public Project project() throws EmptyArgumentException {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public PullRequest pullRequest() {
        throw new UnsupportedOperationException("unimplemented");
    }
}
