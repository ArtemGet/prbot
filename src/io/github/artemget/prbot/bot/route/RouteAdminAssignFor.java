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

import io.github.artemget.prbot.bot.command.CmdAdminAssignFor;
import io.github.artemget.prbot.bot.match.MatchExistsAdminIn;
import io.github.artemget.prbot.bot.match.Not;
import io.github.artemget.prbot.config.EProp;
import io.github.artemget.prbot.config.EmptyEntryException;
import io.github.artemget.prbot.domain.messenger.Messengers;
import io.github.artemget.teleroute.match.MatchAll;
import io.github.artemget.teleroute.match.MatchRegex;
import io.github.artemget.teleroute.route.Route;
import io.github.artemget.teleroute.route.RouteFork;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Route for first admin login.
 *
 * @since 0.0.1
 */
public class RouteAdminAssignFor extends RouteEnvelope {
    /**
     * Ctor.
     *
     * @param telegram Messenger
     * @throws EmptyEntryException At empty property
     */
    public RouteAdminAssignFor(final Messengers<Long> telegram) throws EmptyEntryException {
        this(
            new RouteFork<>(
                new MatchAll<>(
                    new Not(new MatchExistsAdminIn(telegram)),
                    new MatchRegex<>(new EProp("admin_token").value())
                ),
                new CmdAdminAssignFor(telegram)
            )
        );
    }

    /**
     * Main Ctor.
     *
     * @param route Route to wrap
     */
    public RouteAdminAssignFor(final Route<Update, AbsSender> route) {
        super(route);
    }
}
