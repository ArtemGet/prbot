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

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Registerable telegram bot.
 *
 * @since 0.0.1
 */
public final class BotReg implements Register<LongPollingBot> {
    /**
     * Telegram bot.
     */
    private final LongPollingBot bot;

    /**
     * Telegram bot api.
     */
    private final TelegramBotsApi api;

    /**
     * Ctor.
     *
     * @param bot Telegram
     * @throws TelegramApiException If fails to create session
     */
    public BotReg(final LongPollingBot bot) throws TelegramApiException {
        this(bot, new TelegramBotsApi(DefaultBotSession.class));
    }

    /**
     * Main Ctor.
     *
     * @param bot Telegram
     * @param api Registers
     */
    public BotReg(final LongPollingBot bot, final TelegramBotsApi api) {
        this.bot = bot;
        this.api = api;
    }

    public LongPollingBot registered() throws TelegramApiException {
        this.api.registerBot(this.bot);
        return this.bot;
    }
}
