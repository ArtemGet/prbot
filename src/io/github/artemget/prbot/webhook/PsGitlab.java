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

package io.github.artemget.prbot.webhook;

import org.takes.Request;
import org.takes.Response;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.Pass;
import org.takes.misc.Opt;

/**
 * Authenticate gitlab technical user.
 *
 * @since 0.0.1
 */
public final class PsGitlab implements Pass {
    /**
     * Gitlab technical user.
     */
    private final Identity technical;

    /**
     * Ctor.
     */
    public PsGitlab() {
        this("technical_account_gitlab");
    }

    /**
     * Ctor.
     *
     * @param name Of user
     */
    public PsGitlab(final String name) {
        this(new Identity.Simple(name));
    }

    /**
     * Main ctor.
     *
     * @param technical User
     */
    public PsGitlab(final Identity technical) {
        this.technical = technical;
    }

    @Override
    public Opt<Identity> enter(final Request request) {
        return new Opt.Single<>(this.technical);
    }

    @Override
    public Response exit(final Response response, final Identity identity) {
        return response;
    }
}
