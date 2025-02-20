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

package io.github.artemget.prbot.domain.messenger;

import io.github.artemget.prbot.domain.users.Admins;
import io.github.artemget.prbot.domain.users.AdminsTg;
import io.github.artemget.prbot.domain.users.User;
import io.github.artemget.prbot.domain.users.Users;
import io.github.artemget.prbot.domain.users.UsersTg;
import org.jdbi.v3.core.Handle;

/**
 * Telegram messenger.
 *
 * @since 0.0.1
 */
public final class MessengerTg implements Messenger<Long> {
    /**
     * Telegram Users.
     */
    private final Users<Long, User> usrs;

    /**
     * Telegram admins.
     */
    private final Admins<Long> adms;

    /**
     * Ctor.
     *
     * @param src DB
     */
    public MessengerTg(final Handle src) {
        this(new UsersTg(src), new AdminsTg(src));
    }

    /**
     * Main Ctor.
     *
     * @param usrs From telegram
     * @param adms From telegram
     */
    public MessengerTg(final Users<Long, User> usrs, final Admins<Long> adms) {
        this.usrs = usrs;
        this.adms = adms;
    }

    @Override
    public Users<Long, User> users() {
        return this.usrs;
    }

    @Override
    public Admins<Long> admins() {
        return this.adms;
    }
}
