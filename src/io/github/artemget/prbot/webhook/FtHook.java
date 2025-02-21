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

import io.github.artemget.prbot.bot.Register;
import io.github.artemget.prbot.config.EPropInt;
import io.github.artemget.prbot.config.EntryException;
import java.io.IOException;
import org.takes.facets.fork.FkMethods;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.Front;
import org.takes.http.FtBasic;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

/**
 * Front for source code webhooks.
 *
 * @since 0.0.1
 */
public final class FtHook implements Front {
    /**
     * Wrapped front.
     */
    private final Front origin;

    /**
     * Ctor.
     *
     * @param bot To register
     * @throws Exception If Fails
     */
    public FtHook(final Register<LongPollingBot> bot) throws Exception {
        this(bot.registered());
    }

    /**
     * Ctor.
     *
     * @param bot Telegram
     * @throws EntryException At fail to read entry
     * @throws IOException If fails
     */
    public FtHook(final LongPollingBot bot) throws EntryException, IOException {
        this(
            new FtBasic(
                new TkFork(
                    new FkRegex(
                        "/webhooks/gitlab",
                        new TkFork(new FkMethods("POST", new TkHookGitlab(bot)))
                    )
                ),
                new EPropInt("port").value()
            )
        );
    }

    /**
     * Main Ctor.
     *
     * @param origin Front
     */
    public FtHook(final Front origin) {
        this.origin = origin;
    }

    @Override
    public void start(final Exit exit) throws IOException {
        this.origin.start(exit);
    }
}
