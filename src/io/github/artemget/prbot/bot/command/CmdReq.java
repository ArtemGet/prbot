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

package io.github.artemget.prbot.bot.command;

import io.github.artemget.prbot.bot.send.SendMsg;
import io.github.artemget.prbot.domain.action.Feature;
import io.github.artemget.prbot.domain.messenger.Messenger;
import io.github.artemget.prbot.domain.messenger.Messengers;
import io.github.artemget.teleroute.command.Cmd;
import io.github.artemget.teleroute.send.Send;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Request feature access for user.
 *
 * @since 0.0.1
 */
public final class CmdReq implements Cmd<Update, AbsSender> {
    /**
     * Telegram messenger source.
     */
    private final Messengers<Long> msgs;

    /**
     * Feature to allow.
     */
    private final Feature feature;

    /**
     * Main Ctor.
     *
     * @param msgs Telegram
     * @param feature To allow
     */
    public CmdReq(final Messengers<Long> msgs, final Feature feature) {
        this.msgs = msgs;
        this.feature = feature;
    }

    @Override
    public Send<AbsSender> execute(final Update update) {
        final Messenger<Long> messenger = this.msgs.messenger();
        messenger.users()
            .user(update.getMessage().getFrom().getId())
            .request(
                messenger.admins().user(),
                this.feature
            );
        return new SendMsg(
            update,
            String.format(
                "Запрос на фичу: %s направлен администратору",
                this.feature
            )
        );
    }
}
