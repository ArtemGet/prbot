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
import io.github.artemget.prbot.domain.messenger.Messengers;
import io.github.artemget.teleroute.command.Cmd;
import io.github.artemget.teleroute.send.Send;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Assign admin for telegram messenger.
 *
 * @since 0.0.1
 */
public final class CmdAdminAssignFor implements Cmd<Update, AbsSender> {
    /**
     * Telegram messenger source.
     */
    private final Messengers<Long> msgs;

    /**
     * Main Ctor.
     *
     * @param msgs Telegram
     */
    public CmdAdminAssignFor(final Messengers<Long> msgs) {
        this.msgs = msgs;
    }

    @Override
    public Send<AbsSender> execute(final Update update) {
        this.msgs.messenger()
            .admins()
            .add(
                update.getMessage().getFrom().getId(),
                update.getMessage().getFrom().getUserName()
            );
        return new SendMsg(
            update,
            String.format("Приветствую, %s!", update.getMessage().getFrom().getUserName())
        );
    }
}
