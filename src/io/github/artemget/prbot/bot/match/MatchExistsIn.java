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

package io.github.artemget.prbot.bot.match;

import io.github.artemget.prbot.domain.messenger.Messengers;
import io.github.artemget.teleroute.update.Wrap;
import java.util.function.Predicate;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Checks either user linked to messenger.
 *
 * @since 0.0.1
 */
public final class MatchExistsIn implements Predicate<Wrap<Update>> {
    /**
     * Telegram messenger source.
     */
    private final Messengers<Long> msgs;

    /**
     * Main Ctor.
     *
     * @param msgs Telegram
     */
    public MatchExistsIn(final Messengers<Long> msgs) {
        this.msgs = msgs;
    }

    @Override
    public boolean test(final Wrap<Update> update) {
        return this.msgs.messenger()
            .users()
            .exists(update.src().getMessage().getFrom().getId());
    }
}
