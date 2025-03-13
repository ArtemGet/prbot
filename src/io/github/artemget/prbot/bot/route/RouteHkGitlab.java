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

package io.github.artemget.prbot.bot.route;

import io.github.artemget.prbot.bot.command.CmdPrApprove;
import io.github.artemget.prbot.bot.command.CmdPrClose;
import io.github.artemget.prbot.bot.command.CmdPrMerge;
import io.github.artemget.prbot.bot.command.CmdPrOpen;
import io.github.artemget.prbot.bot.command.CmdPrReject;
import io.github.artemget.prbot.bot.match.MatchJsonVal;
import io.github.artemget.prbot.config.EProp;
import io.github.artemget.prbot.config.Entry;
import io.github.artemget.prbot.config.EntryException;
import io.github.artemget.teleroute.match.MatchAll;
import io.github.artemget.teleroute.route.Route;
import io.github.artemget.teleroute.route.RouteDfs;
import io.github.artemget.teleroute.route.RouteFork;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import wtf.g4s8.hamcrest.json.JsonHas;
import wtf.g4s8.hamcrest.json.StringIsJson;

/**
 * Routes for gitlab merge request webhook.
 *
 * @since 0.0.1
 * @todo #6:45min divide this class to separate classes:
 *  RouteHkAuth - routes if token matches.
 *  RouteHkGitlab - routes if gitp matches gitlab.
 *  Rename this calss to RouteHk
 */
public final class RouteHkGitlab extends RouteEnvelope {

    /**
     * Ctor.
     *
     * @throws EntryException If empty entry
     */
    public RouteHkGitlab() throws EntryException {
        this(new EProp("bot_token"));
    }

    /**
     * Ctor.
     *
     * @param token Entry
     * @throws EntryException If empty entry
     */
    public RouteHkGitlab(final Entry<String> token) throws EntryException {
        this(token.value());
    }

    /**
     * Ctor.
     *
     * @param token Value
     */
    public RouteHkGitlab(final String token) {
        this(
            new RouteFork<>(
                new MatchAll<>(
                    new MatchJsonVal(
                        new StringIsJson.Object(
                            new JsonHas(
                                "gitp",
                                "gitlab"
                            )
                        )
                    ),
                    new MatchJsonVal(
                        new StringIsJson.Object(
                            new JsonHas(
                                "token",
                                token
                            )
                        )
                    )
                ),
                new RouteDfs<>(
                    new RouteFork<>(
                        new MatchJsonVal("open"),
                        new CmdPrOpen()
                    ),
                    new RouteFork<>(
                        new MatchJsonVal("close"),
                        new CmdPrClose()
                    ),
                    new RouteFork<>(
                        new MatchJsonVal("approval"),
                        new CmdPrApprove()
                    ),
                    new RouteFork<>(
                        new MatchJsonVal("unapproval"),
                        new CmdPrReject()
                    ),
                    new RouteFork<>(
                        new MatchJsonVal("merge"),
                        new CmdPrMerge()
                    )
                )
            )
        );
    }

    /**
     * Main Ctor.
     *
     * @param origin Route to wrap
     */
    public RouteHkGitlab(final Route<Update, AbsSender> origin) {
        super(origin);
    }
}
