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

package io.github.artemget.prbot;

import io.github.artemget.prbot.bot.BotPr;
import io.github.artemget.prbot.bot.BotReg;
import io.github.artemget.prbot.bot.match.MatchAdminOf;
import io.github.artemget.prbot.bot.route.RouteAdminAssignFor;
import io.github.artemget.prbot.bot.route.RouteAdminFor;
import io.github.artemget.prbot.bot.route.RouteUserFor;
import io.github.artemget.prbot.config.EProp;
import io.github.artemget.prbot.config.EPropInt;
import io.github.artemget.prbot.domain.messenger.MessengersTg;
import io.github.artemget.prbot.webhook.TkHooks;
import io.github.artemget.teleroute.route.RouteFork;
import org.jdbi.v3.core.Jdbi;
import org.takes.http.Exit;
import org.takes.http.FtBasic;

/**
 * Entrypoint. Application starts here.
 *
 * @since 0.0.1
 * @checkstyle HideUtilityClassConstructorCheck (2 lines)
 */
@SuppressWarnings(
    {
        "AnnotationUseStyleCheck",
        "PMD.HideUtilityClassConstructorCheck",
        "PMD.ProhibitPublicStaticMethods",
        "PMD.UseUtilityClass"
    }
)
public final class Entrypoint {
    public static void main(final String[] args) throws Exception {
        final MessengersTg telegram = new MessengersTg(
            Jdbi.create(new EProp("pg_connection_url").value())
        );
        new FtBasic(
            new TkHooks(
                new BotReg(
                    new BotPr(
                        new EProp("bot_name"),
                        new EProp("bot_token"),
                        new RouteAdminAssignFor(telegram),
                        new RouteFork<>(
                            new MatchAdminOf(telegram),
                            new RouteAdminFor()
                        ),
                        new RouteUserFor()
                    )
                )
            ),
            new EPropInt("port").value()
        ).start(Exit.NEVER);
    }
}
