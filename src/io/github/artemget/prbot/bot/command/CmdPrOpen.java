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

import io.github.artemget.teleroute.command.Cmd;
import io.github.artemget.teleroute.command.CmdException;
import io.github.artemget.teleroute.send.Send;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Command for pull request just opened.
 *
 * @since 0.0.1
 * @todo #6:120min update should be send to all selected chats.
 *  Update should include:
 *  1)Tag assigner and reviewers
 *  2)If ASAP trigger word is present in pull request message - notify
 *  assigner via personal message.
 *  3)Link pull request in a group if there are any other
 *  pull requests with the same issue number. In this
 *  case there should not be a new message sent but
 *  previous updated.
 *  See message template in readme.
 */
public class CmdPrOpen implements Cmd<Update, AbsSender> {

    @Override
    public Send<AbsSender> execute(Update update) throws CmdException {
        throw new UnsupportedOperationException("unimplemented");
    }
}
