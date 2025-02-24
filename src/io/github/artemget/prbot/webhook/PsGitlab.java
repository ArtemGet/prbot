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

import java.io.IOException;
import java.net.HttpURLConnection;
import org.takes.HttpException;
import org.takes.Request;
import org.takes.Response;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.Pass;
import org.takes.misc.Opt;
import org.takes.rq.RqHeaders;

/**
 * Authenticate gitlab technical user.
 *
 * @since 0.0.1
 */
public final class PsGitlab implements Pass {
    private final String header;
    private final String check;

    /**
     * Gitlab technical user.
     */
    private final Identity technical;

    /**
     * Ctor.
     *
     * @param token Expected
     */
    public PsGitlab(String token) {
        this("technical_account_gitlab", "X-Gitlab-Token", token);
    }

    /**
     * Main ctor.
     *
     * @param name Of user
     * @param header Auth
     * @param token Expected
     */
    public PsGitlab(
        final String name,
        final String header,
        final String token
    ) {
        this.technical = new Identity.Simple(name);
        this.header = header;
        this.check = token;
    }

    @Override
    public Opt<Identity> enter(final Request request) throws IOException {
        final boolean authenticated;
        try {
            authenticated = this.check.equals(
                new RqHeaders.Smart(request).single(this.header)
            );
        } catch (final IOException exception) {
            throw new HttpException(HttpURLConnection.HTTP_UNAUTHORIZED, exception);
        }
        final Opt<Identity> res;
        if (authenticated) {
            res = new Opt.Single<>(this.technical);
        } else {
            res = new Opt.Empty<>();
        }
        return res;
    }

    @Override
    public Response exit(final Response response, final Identity identity) {
        return response;
    }
}
