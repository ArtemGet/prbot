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

import io.github.artemget.teleroute.update.Wrap;
import java.util.function.Predicate;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Wrap for Match predicate.
 *
 * @since 0.0.1
 * @todo #1:15min move this class to
 *  <a href="https://github.com/ArtemGet/teleroute">teleroute</a>,
 *  thus it would be useful in other bots. Tests required.
 *  Note that this class is extendable.
 * @checkstyle DesignForExtensionCheck (100 lines)
 */
public class MatchEnvelope implements Predicate<Wrap<Update>> {
    /**
     * Wrapped predicate match.
     */
    private final Predicate<Wrap<Update>> origin;

    /**
     * Main ctor.
     *
     * @param origin Match predicate
     */
    public MatchEnvelope(final Predicate<Wrap<Update>> origin) {
        this.origin = origin;
    }

    @Override
    public boolean test(final Wrap<Update> updateWrap) {
        return this.origin.test(updateWrap);
    }
}
