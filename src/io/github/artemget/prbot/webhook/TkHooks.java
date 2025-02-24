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

import io.github.artemget.prbot.bot.Register;
import io.github.artemget.prbot.config.EProp;
import io.github.artemget.prbot.config.Entry;
import io.github.artemget.prbot.config.EntryException;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.auth.TkAuth;
import org.takes.facets.auth.TkSecure;
import org.takes.facets.fallback.FbChain;
import org.takes.facets.fallback.FbStatus;
import org.takes.facets.fallback.TkFallback;
import org.takes.facets.fork.FkMethods;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.misc.Opt;
import org.takes.rs.RsText;
import org.takes.rs.RsWithStatus;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

/**
 * Take for source code webhooks.
 *
 * @since 0.0.1
 */
public final class TkHooks implements Take {
    /**
     * Wrapped take.
     */
    private final Take origin;

    /**
     * Ctor.
     *
     * @param bot To register
     * @throws Exception If Fails
     */
    public TkHooks(final Register<LongPollingBot> bot) throws Exception {
        this(bot.registered(), new EProp("gitlab_token"));
    }

    /**
     * Ctor.
     *
     * @param bot Telegram
     * @param config Entry
     * @throws EntryException If config is empty
     */
    public TkHooks(final LongPollingBot bot, final Entry<String> config) throws EntryException {
        this(new TkHookGitlab(bot), config.value());
    }

    /**
     * Ctor.
     *
     * @param gitlab Flow
     * @param token Auth
     */
    public TkHooks(final Take gitlab, final String token) {
        this(
            new TkFallback(
                new TkAuth(
                    new TkSecure(
                        new TkFork(
                            new FkRegex(
                                "/webhooks/gitlab",
                                new TkFork(new FkMethods("POST", gitlab))
                            )
                        )
                    ),
                    new PsGitlab(token)
                ),
                new FbChain(
                    new FbStatus(401, new RsWithStatus(new RsText("unauthenticated"), 401)),
                    new FbStatus(404, new RsWithStatus(new RsText("route not found"), 404)),
                    new FbStatus(405, new RsWithStatus(new RsText("no suitable method"), 405)),
                    req -> new Opt.Single<>(new RsWithStatus(new RsText("bad request"), 500))
                )
            )
        );
    }

    /**
     * Main Ctor.
     *
     * @param origin Take
     */
    public TkHooks(final Take origin) {
        this.origin = origin;
    }

    @Override
    public Response act(final Request req) throws Exception {
        return this.origin.act(req);
    }
}
