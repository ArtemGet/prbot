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

package io.github.artemget.prbot.bot;

import io.github.artemget.prbot.config.Entry;
import io.github.artemget.prbot.config.EntryException;
import io.github.artemget.prbot.config.EntryExceptionUnchecked;
import io.github.artemget.teleroute.command.Cmd;
import io.github.artemget.teleroute.command.CmdException;
import io.github.artemget.teleroute.route.Route;
import io.github.artemget.teleroute.route.RouteDfs;
import io.github.artemget.teleroute.send.Send;
import io.github.artemget.teleroute.send.SendException;
import io.github.artemget.teleroute.telegrambots.update.TgBotWrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Main bot class.
 *
 * @since 0.1.0
 */
public final class BotPr extends TelegramLongPollingBot {
    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(BotPr.class);

    /**
     * Bot name.
     */
    private final Entry<String> name;

    /**
     * Update route.
     */
    private final Route<Update, AbsSender> route;

    /**
     * Main ctor.
     * @param name Telegram bot name
     * @param token Telegram bot token
     * @param route Routes
     */
    @SafeVarargs
    public BotPr(
        final Entry<String> name,
        final Entry<String> token,
        final Route<Update, AbsSender>... route
    ) throws EntryException {
        super(token.value());
        this.name = name;
        this.route = new RouteDfs<>(route);
    }

    @Override
    public void onUpdateReceived(final Update update) {
        this.route.route(new TgBotWrap(update))
            .map(cmd -> BotPr.handleExecution(cmd, update))
            .ifPresent(send -> this.handleSend(send, update));
    }

    @Override
    public String getBotUsername() {
        try {
            return this.name.value();
        } catch (final EntryException exception) {
            throw new EntryExceptionUnchecked(exception);
        }
    }

    public void handleSend(final Send<AbsSender> send, final Update update) {
        try {
            send.send(this);
        } catch (final SendException exception) {
            LOG.error("Error occurred while sending response to request {}", update, exception);
        }
    }

    private static Send<AbsSender> handleExecution(
        final Cmd<Update, AbsSender> cmd,
        final Update update
    ) {
        Send<AbsSender> resp;
        try {
            resp = cmd.execute(update);
        } catch (final CmdException exception) {
            resp = new Send.Void<>();
            LOG.error("Error occurred while processing command to request {}", update, exception);
        }
        return resp;
    }
}
