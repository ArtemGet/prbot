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

import io.github.artemget.prbot.config.EntryException;
import io.github.artemget.prbot.domain.pr.ReqHkGitlab;
import io.github.artemget.prbot.domain.pr.RequestWebhook;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsEmpty;
import org.takes.rs.RsWithStatus;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

/**
 * Gitlab webhook take.
 *
 * @since 0.0.1
 */
public final class TkHookGitlab implements Take {
    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(TkHookGitlab.class);

    /**
     * Telegrambots long polling bot.
     */
    private final LongPollingBot bot;

    /**
     * Main Ctor.
     *
     * @param bot Telegrambots
     */
    public TkHookGitlab(final LongPollingBot bot) {
        this.bot = bot;
    }

    @Override
    public Response act(final Request request) {
        Response response;
        try {
            this.bot.onUpdateReceived(TkHookGitlab.updated(request));
            response = new RsWithStatus(new RsEmpty(), 200);
        } catch (final IOException | EntryException exception) {
            LOG.error(
                String.format(
                    "Error wrapping http request to telegram update. request: %s",
                    request.toString()
                ),
                exception
            );
            response = new RsWithStatus(new RsEmpty(), 500);
        }
        return response;
    }

    /**
     * Wraps http request to telegram update.
     *
     * @param request Http
     * @return Telegram update
     * @throws IOException At corrupted body
     */
    private static Update updated(final Request request) throws IOException, EntryException {
        final Update update = new Update();
        update.setMessage(TkHookGitlab.wrapped(request));
        return update;
    }

    /**
     * Wraps http request to telegram message.
     *
     * @param request Http
     * @return Telegram message
     * @throws IOException At corrupted body
     */
    private static Message wrapped(final Request request) throws IOException, EntryException {
        final User user = new User();
        user.setId(0L);
        user.setUserName("Technical account");
        final Message message = new Message();
        message.setFrom(user);
        try (InputStream input = request.body()) {
            message.setText(
                new RequestWebhook.RequestsStrict(
                    new ReqHkGitlab(new String(input.readAllBytes(), StandardCharsets.UTF_8))
                ).value()
            );
        }
        return message;
    }
}
